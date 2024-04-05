package org.rpc.server.config;

import lombok.Data;
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

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
}
