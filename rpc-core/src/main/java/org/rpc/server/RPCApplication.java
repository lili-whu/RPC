package org.rpc.server;

import org.rpc.server.config.RPCConfig;
import org.rpc.server.constant.RPCConstant;
import org.rpc.server.utils.ConfigUtils;

public class RPCApplication{
    private static volatile RPCConfig rpcConfig;

    public static void init(RPCConfig newRPCconfig){
        rpcConfig = newRPCconfig;
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
