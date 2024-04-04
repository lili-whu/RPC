package org.rpc.server.HttpServer;

import io.vertx.core.Vertx;

public class VertXHttpServer implements HttpServer{
    @Override
    public void start(int port){
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        // 创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口
        server.requestHandler(new VertxHttpHandler());

        server.listen(port, result -> {
            if(result.succeeded()){
                System.out.println("Server is listening on port: " + port);
            }else{
                System.out.println("vertx failed: "+ result.cause());
            }
        });

    }
}
