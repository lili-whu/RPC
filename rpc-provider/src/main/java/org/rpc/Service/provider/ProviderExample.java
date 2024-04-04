package org.rpc.Service.provider;

import org.rpc.server.HttpServer.HttpServer;
import org.rpc.server.HttpServer.VertXHttpServer;
import org.rpc.server.RPCApplication;
import org.rpc.server.registry.LocalRegistry;
import org.rpc.service.UserService;

/**
 * 服务提供者事例
 */
public class ProviderExample{
    public static void main(String[] args){
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动web服务
        HttpServer httpServer = new VertXHttpServer();

        httpServer.start(RPCApplication.getConfig().getServerPort());
    }
}
