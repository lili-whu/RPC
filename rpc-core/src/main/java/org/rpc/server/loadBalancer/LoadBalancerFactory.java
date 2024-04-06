package org.rpc.server.loadBalancer;

import org.rpc.server.spi.SpiLoader;

public class LoadBalancerFactory{
    static {
        SpiLoader.load(LoadBalancer.class);
    }
    public static LoadBalancer getInstance(String key){
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
