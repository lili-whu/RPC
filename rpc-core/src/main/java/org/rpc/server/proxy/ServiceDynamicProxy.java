package org.rpc.server.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.rpc.model.User;
import org.rpc.server.RPCApplication;
import org.rpc.server.config.RPCConfig;
import org.rpc.server.fault.retry.RetryStrategy;
import org.rpc.server.fault.retry.RetryStrategyFactory;
import org.rpc.server.loadBalancer.LoadBalancer;
import org.rpc.server.loadBalancer.LoadBalancerFactory;
import org.rpc.server.model.RPCRequest;
import org.rpc.server.model.RPCResponse;
import org.rpc.server.model.ServiceMetaInfo;
import org.rpc.server.registry.Registry;
import org.rpc.server.registry.RegistryFactory;
import org.rpc.server.serializer.JDKSerializer;
import org.rpc.server.serializer.Serializer;
import org.rpc.server.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * JDK动态代理
 */
public class ServiceDynamicProxy implements InvocationHandler{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        RPCConfig rpcConfig = RPCApplication.getConfig();
        Serializer serializer = SerializerFactory.getInstance(rpcConfig.getSerializer());
        System.out.println("使用序列化器: " + serializer.getClass().getName());
        // rpc请求
        RPCRequest rpcRequest = RPCRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterType(method.getParameterTypes())
                .serviceVersion("1.0")
                .args(args)
                .build();
        try{
            byte[] rpcRequestBytes = serializer.serialize(rpcRequest);
            byte[] result;
            // 找到服务地址
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(rpcRequest.getServiceName());
            serviceMetaInfo.setServiceVersion(rpcRequest.getServiceVersion());
            // 通过注册中心发现可用节点
            List<ServiceMetaInfo> serviceMetaInfos = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 构造请求, 方便hash
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            // 得到负载均衡的结果
            ServiceMetaInfo usedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfos);

            // 重试机制 发送rpc请求
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            RPCResponse rpcResponse = null;

            //重试得到结果
//            rpcResponse =  retryStrategy.doRetry(new Callable<RPCResponse>(){
//                @Override
//                public RPCResponse call() throws Exception{

//                    return null;
//                }
//            })

            HttpResponse response = HttpRequest.post(usedServiceMetaInfo.getServiceAddress())
                    .body(rpcRequestBytes).execute();
            result = response.bodyBytes();

            //得到rpc响应, 反序列化
            rpcResponse = serializer.deserialize(result, RPCResponse.class);

            return rpcResponse.getData();
        }catch (Exception e){
            System.err.println("rpc代理请求错误: " + e.getMessage());
        }
        return null;
    }
}
