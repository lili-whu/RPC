package org.rpc.server.fault.retry;

import org.rpc.server.model.RPCResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy{

    RPCResponse doRetry(Callable<RPCResponse> callable) throws Exception;

}
