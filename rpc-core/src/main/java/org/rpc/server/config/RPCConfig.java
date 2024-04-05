package org.rpc.server.config;

import lombok.Data;
import org.rpc.server.serializer.Serializer;

@Data
public class RPCConfig{
    private String name = "rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private Boolean mock = false;

    // 序列化器 jdk, hessian, json
    private String serializer = "jdk";
}
