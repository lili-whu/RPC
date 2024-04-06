package org.rpc.server.fault.retry;

import com.github.rholder.retry.*;
import org.rpc.server.model.RPCResponse;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.github.rholder.retry.RetryerBuilder.newBuilder;

public class FixedIntervalRetryStrategy implements RetryStrategy{

    @Override
    public RPCResponse doRetry(Callable<RPCResponse> callable) throws Exception{
        Retryer<RPCResponse> retryer = RetryerBuilder.<RPCResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withRetryListener(new RetryListener(){
                    @Override
                    public <V> void onRetry(Attempt<V> attempt){
                        System.out.println("重试次数: " + attempt.getAttemptNumber());
                    }
                }).build();
        return retryer.call(callable);
    }
}
