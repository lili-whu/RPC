package org.rpc.server.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 协议消息结构
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T>{
    private Header header;
    private T body;

    @Data
    public static class Header{
        // magic num
        private byte magic;

        private byte version;

        private byte serializer;

        private byte type;

        private byte status;

        // 请求ID
        private long requestId;
        // 消息体长度
        private int contentLength;

    }
}
