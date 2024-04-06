package org.rpc.server.fault.retry;

import org.rpc.server.model.RPCResponse;

import java.util.concurrent.Callable;

public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RPCResponse doRetry(Callable<RPCResponse> callable) throws Exception{
        return callable.call();
    }
}
