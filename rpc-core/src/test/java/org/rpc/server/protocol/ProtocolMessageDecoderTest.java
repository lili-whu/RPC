package org.rpc.server.protocol;

import cn.hutool.core.util.IdUtil;
import io.vertx.core.buffer.Buffer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.rpc.server.constant.RPCConstant;
import org.rpc.server.model.RPCRequest;

import java.io.IOException;

public class ProtocolMessageDecoderTest extends TestCase{
    public void testEncodeAndDecode() throws IOException{
        // 构造消息
        ProtocolMessage<RPCRequest> protocolMessage = new ProtocolMessage<>();
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializerEnum.JDK.getKey());
        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue());
        header.setRequestId(IdUtil.getSnowflakeNextId());
        header.setContentLength(0);
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setServiceName("myService");
        rpcRequest.setMethodName("myMethod");
        rpcRequest.setServiceVersion(RPCConstant.DEFAULT_VERSION);
        rpcRequest.setParameterType(new Class[]{String.class, String.class});
        rpcRequest.setArgs(new Object[]{"aaa", "bbb"});
        protocolMessage.setHeader(header);
        protocolMessage.setBody(rpcRequest);

        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolMessage<?> message = ProtocolMessageDecoder.decode(encodeBuffer);
        System.out.println(message);
        Assert.assertNotNull(message);
    }
}