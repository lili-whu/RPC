package org.rpc.server.HttpServer;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.rpc.server.model.RPCRequest;
import org.rpc.server.model.RPCResponse;
import org.rpc.server.registry.LocalRegistry;
import org.rpc.server.serializer.JDKSerializer;
import org.rpc.server.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 请求处理器
 */

public class VertxHttpHandler implements Handler<HttpServerRequest>{

    @Override
    public void handle(HttpServerRequest httpServerRequest){
        Serializer serializer = new JDKSerializer();

        System.out.println("Received Request: " + httpServerRequest.method() + " " + httpServerRequest.uri());

        // 处理Http请求
        httpServerRequest.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RPCRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RPCRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 返回响应结果
            RPCResponse rpcResponse = new RPCResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("null request");
                doResponse(httpServerRequest, rpcResponse, serializer);
                return;
            }
            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterType());
                Object result = method.invoke(implClass.getDeclaredConstructor().newInstance(), rpcRequest.getArgs());
                // 封装结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("success");

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
            doResponse(httpServerRequest, rpcResponse, serializer);
        });

    }

    private void doResponse(HttpServerRequest request, RPCResponse rpcResponse, Serializer serializer){
        // 返回响应
        HttpServerResponse response = request.response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化
            byte[] serial = serializer.serialize(rpcResponse);
            response.end(Buffer.buffer(serial));
        } catch (IOException e) {
            response.end(Buffer.buffer());
        }

    }
}
