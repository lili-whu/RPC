package org.rpc.server.fault.retry;

import org.rpc.server.spi.SpiLoader;

public class RetryStrategyFactory{
    static {
        SpiLoader.load(RetryStrategy.class);
    }

    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new FixedIntervalRetryStrategy();

    public static RetryStrategy getInstance(String key){
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
