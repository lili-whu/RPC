package org.rpc.server.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.rpc.server.constant.RPCConstant;

/**
 * 注册服务的元信息
 */
@Data
public class ServiceMetaInfo{

    // 服务名称
    private String serviceName;
    //服务版本号
    private String serviceVersion = RPCConstant.DEFAULT_VERSION;
    // 服务域名
    private String serviceHost;
    // 服务端口
    private Integer servicePort;
    //服务
    private String serviceGroup = "default";


    public String getServiceKey(){
        return String.format("%s:%s", getServiceName(), getServiceVersion());
    }

    public String getServiceNodeKey(){
        // 使用/分割, 层次化存储
        return String.format("%s/%s", getServiceKey(), getServiceAddress());
    }

    // 获取服务地址
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
