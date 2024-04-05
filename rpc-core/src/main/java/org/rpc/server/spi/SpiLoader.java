package org.rpc.server.spi;

import cn.hutool.core.io.resource.ResourceUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpiLoader{
    private static final Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    private static final Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    private static final String SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    private static final String USER_SPI_DIR = "META-INF/rpc/user/";
    private static final String[] SCAN_DIRS = new String[]{SYSTEM_SPI_DIR, USER_SPI_DIR};

    public static <T> T getInstance(Class<?> tClass, String key){
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if(keyClassMap == null){
            System.out.println("keyClassMap == null, 不存在此SPI");
            return null;
        }
        if(!keyClassMap.containsKey(key)){
            System.out.println("keyClassMap.containsKey(key) == false, SPI中不存在此键");
            return null;
        }
        Class<?> implClass = keyClassMap.get(key);
        String implClassName = implClass.getName();
        if(!instanceCache.containsKey(implClassName)){
            try{
                instanceCache.put(implClassName, implClass.getDeclaredConstructor().newInstance());
            }
              catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println("实例化失败");
                throw new RuntimeException(e);
            }
        }
        return (T) instanceCache.get(implClassName);
    }
    public static Map<String, Class<?>> load(Class<?> loadClass){
        System.out.println("加载类型为 " +  loadClass.getName() + " 的SPI");
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        for(String dir: SCAN_DIRS){
            List<URL> resources = ResourceUtil.getResources(dir + loadClass.getName());
            for(URL resource: resources){
                try{
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        String[] strArray = line.split("=");
                        if(strArray.length > 1){
                            String key = strArray[0];
                            String className = strArray[1];
                            keyClassMap.put(key, Class.forName(className));
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        loaderMap.put(loadClass.getName(), keyClassMap);
        return keyClassMap;
    }

}
