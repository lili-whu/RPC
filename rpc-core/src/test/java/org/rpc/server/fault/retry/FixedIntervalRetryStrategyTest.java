package org.rpc.server.fault.retry;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.rpc.server.model.RPCResponse;

@RunWith(Enclosed.class)
public class FixedIntervalRetryStrategyTest extends TestCase{


    /**
     * 重试策略测试
     *
     * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
     * @learn <a href="https://codefather.cn">程序员鱼皮的编程宝典</a>
     * @from <a href="https://yupi.icu">编程导航知识星球</a>
     */
    public static class RetryStrategyTest {

        RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

        @Test
        public void doRetry() {
            try {
                RPCResponse rpcResponse = retryStrategy.doRetry(() -> {
                    System.out.println("测试重试");
                    throw new RuntimeException("模拟重试失败");
                });
                System.out.println(rpcResponse);
            } catch (Exception e) {
                System.out.println("重试多次失败");
                e.printStackTrace();
            }
        }
    }

}