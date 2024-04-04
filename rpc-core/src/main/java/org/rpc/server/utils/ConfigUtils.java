package org.rpc.server.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

public class ConfigUtils{
    public static <T> T loadConfig(Class<T> class1, String prefix){
        return loadConfig(class1, prefix, "");
    }

    public static <T> T loadConfig(Class<T> class1, String prefix, String env){
        StringBuilder configFile = new StringBuilder("application");
        if(StrUtil.isNotBlank(env)){
            configFile.append("-").append(env);
        }
        configFile.append(".properties");
        Props props = new Props(configFile.toString());
        return props.toBean(class1, prefix);
    }
}
