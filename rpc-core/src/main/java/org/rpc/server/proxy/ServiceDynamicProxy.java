package org.rpc.server.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.rpc.model.User;
import org.rpc.server.RPCApplication;
import org.rpc.server.config.RPCConfig;
import org.rpc.server.model.RPCRequest;
import org.rpc.server.model.RPCResponse;
import org.rpc.server.serializer.JDKSerializer;
import org.rpc.server.serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理
 */
public class ServiceDynamicProxy implements InvocationHandler{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        Serializer serializer = new JDKSerializer();

        // rpc请求
        RPCRequest rpcRequest = RPCRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterType(method.getParameterTypes())
                .args(args)
                .build();
        try{
            byte[] rpcRequestBytes = serializer.serialize(rpcRequest);
            byte[] result;
            // 发送rpc请求
            RPCConfig config = RPCApplication.getConfig();
            try(HttpResponse response = HttpRequest.post("http://"+ config.getServerHost() + ":" + config.getServerPort())
                    .body(rpcRequestBytes).execute()){
                result = response.bodyBytes();
            }
            //得到rpc响应
            RPCResponse rpcResponse = serializer.deserialize(result, RPCResponse.class);
            return rpcResponse.getData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
