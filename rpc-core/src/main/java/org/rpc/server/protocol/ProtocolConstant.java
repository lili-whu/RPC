package org.rpc.server.protocol;

//协议常量
public interface ProtocolConstant{
    int HEADER_LENGTH = 17;

    byte PROTOCOL_MAGIC = 0x1;

    byte PROTOCOL_VERSION = 0x1;
}
