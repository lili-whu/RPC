package org.rpc.server.config;

import lombok.Data;
import org.rpc.server.fault.tolerant.TolerantStrategy;
import org.rpc.server.serializer.Serializer;

@Data
public class RPCConfig{
    private String name = "rpc";

    private String version = "1.0";

    // 使用注册中心发现服务地址
    @Deprecated
    private String serverHost = "http://localhost";
    // 使用注册中心发现服务地址
    @Deprecated
    private Integer serverPort = 8080;

    private Boolean mock = false;

    // 序列化器 jdk, hessian, json
    private String serializer = "jdk";

    // 重试策略 no, fixedInterval
    private String retryStrategy = "fixedInterval";

    // 负载均衡配置 consistentHash, random, round
    private String loadBalancer = "consistentHash";

    // 容错配置
    private String tolerantStrategy = "failSafe";
    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
}
