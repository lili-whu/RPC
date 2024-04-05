package org.rpc.server.config;

import lombok.Data;
import org.rpc.server.serializer.Serializer;
import org.rpc.server.serializer.SerializerKeys;

@Data
public class RPCConfig{
    private String name = "rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private Boolean mock = false;

    // 序列化器
    private String serializer = SerializerKeys.JDK;
}
