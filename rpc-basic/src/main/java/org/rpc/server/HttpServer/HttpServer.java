package org.rpc.server.HttpServer;

/**
 * Http服务器接口
 */
public interface HttpServer{
    /**
     * 启动服务器
     * @param port
     */
    void start(int port);
}
