package org.rpc.server.protocol;


import io.vertx.core.buffer.Buffer;
import org.rpc.server.model.RPCRequest;
import org.rpc.server.model.RPCResponse;
import org.rpc.server.serializer.Serializer;
import org.rpc.server.serializer.SerializerFactory;

import java.io.IOException;

// 协议消息解码
public class ProtocolMessageDecoder{
    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException{
        // 分别从指定位置读出 Buffer
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        // 校验魔数
        if (magic != ProtocolConstant.PROTOCOL_MAGIC) {
            throw new RuntimeException("消息 magic 非法");
        }
        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getLong(5));
        header.setContentLength(buffer.getInt(13));
        // 解决粘包问题，读取指定长度的数据
        byte[] bodyBytes = buffer.getBytes(17, 17 + header.getContentLength());
        // 解析消息体
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if (serializerEnum == null) {
            throw new RuntimeException("序列化消息的协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        ProtocolMessageTypeEnum messageTypeEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if (messageTypeEnum == null) {
            throw new RuntimeException("序列化消息的类型不存在");
        }
        switch (messageTypeEnum) {
            case REQUEST:
                RPCRequest request = serializer.deserialize(bodyBytes, RPCRequest.class);
                return new ProtocolMessage<>(header, request);
            case RESPONSE:
                RPCResponse response = serializer.deserialize(bodyBytes, RPCResponse.class);
                return new ProtocolMessage<>(header, response);
            case HEART_BEAT:
            case OTHERS:
            default:
                throw new RuntimeException("暂不支持该消息类型");
        }
    }
}
