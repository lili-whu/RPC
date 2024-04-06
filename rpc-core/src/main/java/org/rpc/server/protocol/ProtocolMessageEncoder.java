package org.rpc.server.protocol;

import io.vertx.core.buffer.Buffer;
import org.rpc.server.serializer.Serializer;
import org.rpc.server.serializer.SerializerFactory;

import java.io.IOException;

// 包装为自定义协议
public class ProtocolMessageEncoder{
    // 编码
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException{
        if(protocolMessage == null || protocolMessage.getHeader() == null){
            return Buffer.buffer();
        }
        ProtocolMessage.Header header = protocolMessage.getHeader();
        // 写入自定义协议头
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());

        // 获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum == null) throw new RuntimeException("序列化协议不存在");

        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());

        byte[] bodybytes = serializer.serialize(protocolMessage.getBody());
        // 写入buffer长度和数据
        buffer.appendInt(bodybytes.length);
        buffer.appendBytes(bodybytes);
        return buffer;

    }
}
