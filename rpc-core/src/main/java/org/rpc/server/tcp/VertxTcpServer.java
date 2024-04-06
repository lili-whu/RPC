package org.rpc.server.tcp;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import org.rpc.server.HttpServer.HttpServer;

public class VertxTcpServer implements HttpServer{

    private byte[] handleRequest(byte[] requestData){
        // todo 处理请求
        return "Hello, VertxTCPClient".getBytes();
    }
    @Override
    public void start(int port){
        Vertx vertx = Vertx.vertx();

        // 创建TCP服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(new TCPServerHandler());


//        server.connectHandler(netSocket -> {
//
//            String testMessage = "Hello, server!!!Hello, server!!!Hello, Server!!!";
//            int length = testMessage.getBytes().length;
//            RecordParser parser = RecordParser.newFixed(length);
//            parser.setOutput(buffer -> {
//                if(buffer.getBytes().length < length){
//                    System.out.println("半包 length = " + buffer.getBytes().length);
//                }else{
//                    if(buffer.getBytes().length > length){
//                        System.out.println("粘包 length = " + buffer.getBytes().length);
//                    }
//                }
//                String str = new String(buffer.getBytes());
//                System.out.println(str);
//                if(testMessage.equals(str)){
//                    System.out.println("good");
//                }
//
//                // 处理收到的信息
////                byte[] requestData = buffer.getBytes();
//                // 消息处理逻辑
////                byte[] responseData = handleRequest(requestData);
//                // 发送响应
//                netSocket.handler(parser);
////                netSocket.write(Buffer.buffer(requestData));
//            });
//            netSocket.handler(parser);
//        });

        //启动TCP服务器
        server.listen(port, result -> {
            if(result.succeeded()){
                System.out.println("TCP服务器启动成功, port: " + port);
            }else{
                System.err.println("TCP服务器启动失败, " + result.cause());
            }
        });
    }

    public static void main(String[] args){
        new VertxTcpServer().start(8888);
    }
}
