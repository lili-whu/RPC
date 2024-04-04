package org.rpc.server.config;

import lombok.Data;

@Data
public class RPCConfig{
    private String name = "rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;
}
