package org.rpc.server;

import org.rpc.server.config.RPCConfig;
import org.rpc.server.config.RegistryConfig;
import org.rpc.server.constant.RPCConstant;
import org.rpc.server.registry.Registry;
import org.rpc.server.registry.RegistryFactory;
import org.rpc.server.utils.ConfigUtils;

public class RPCApplication{
    private static volatile RPCConfig rpcConfig;

    public static void init(RPCConfig newRPCconfig){
        rpcConfig = newRPCconfig;
        // 初始化注册中心
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);

        // 注册JVM的 Shutdown Hook, 退出时执行
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    public static void init(){
        rpcConfig = ConfigUtils.loadConfig(RPCConfig.class, RPCConstant.DEFAULT_PREFIX);
        init(rpcConfig);
    }
    public static RPCConfig getConfig(){
        if(rpcConfig == null){
            synchronized (RPCConfig.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
