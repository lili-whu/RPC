package org.rpc.Service.provider;

import org.rpc.server.HttpServer.HttpServer;
import org.rpc.server.HttpServer.VertXHttpServer;
import org.rpc.server.RPCApplication;
import org.rpc.server.config.RPCConfig;
import org.rpc.server.config.RegistryConfig;
import org.rpc.server.model.ServiceMetaInfo;
import org.rpc.server.registry.LocalRegistry;
import org.rpc.server.registry.Registry;
import org.rpc.server.registry.RegistryFactory;
import org.rpc.server.tcp.VertxTcpServer;
import org.rpc.service.UserService;

import java.util.concurrent.ExecutionException;

public class ProviderToRegistry{
    public static void main(String[] args){
        RPCApplication.init();
        String serviceName = UserService.class.getName();

        // 注册本地服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);


        //注册服务到注册中心
        RPCConfig rpcConfig = RPCApplication.getConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.start(serviceMetaInfo.getServicePort());
//        HttpServer httpServer = new VertXHttpServer();
//        httpServer.start(serviceMetaInfo.getServicePort());

    }
}
