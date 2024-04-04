package org.rpc.server.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.rpc.model.User;
import org.rpc.server.model.RPCRequest;
import org.rpc.server.model.RPCResponse;
import org.rpc.server.serializer.JDKSerializer;
import org.rpc.server.serializer.Serializer;
import org.rpc.service.UserService;

/**
 * 静态代理实现UserService服务代理
 */
public class UserServiceStaticProxy implements UserService{

    @Override
    public User getUser(User user){
        Serializer serializer = new JDKSerializer();

        // 发请求
        RPCRequest rpcRequest = RPCRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterType(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] rpcRequestBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse response = HttpRequest.post("http://localhost:8080")
                    .body(rpcRequestBytes).execute()) {
                result = response.bodyBytes();
            }
            RPCResponse rpcResponse = serializer.deserialize(result, RPCResponse.class);
            return (User) rpcResponse.getData();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
