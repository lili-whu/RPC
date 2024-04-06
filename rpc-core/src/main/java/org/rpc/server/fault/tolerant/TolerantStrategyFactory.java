package org.rpc.server.fault.tolerant;

import org.rpc.server.loadBalancer.LoadBalancer;
import org.rpc.server.spi.SpiLoader;

public class TolerantStrategyFactory{
    static {
        SpiLoader.load(TolerantStrategy.class);
    }
    public static TolerantStrategy getInstance(String key){
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
