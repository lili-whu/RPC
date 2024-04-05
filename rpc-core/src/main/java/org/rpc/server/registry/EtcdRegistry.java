package org.rpc.server.registry;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.op.Op;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import org.rpc.server.config.RegistryConfig;
import org.rpc.server.model.ServiceMetaInfo;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EtcdRegistry implements Registry{

    private Client client;

    private KV kvClient;

    // 项目根key
    private static final String ETCD_ROOT = "rpc/";

    private final Set<String> localRegisterSet = new HashSet<>();
    @Override
    public void init(RegistryConfig registryConfig){
        this.client = Client.builder().endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout())).build();
        this.kvClient = client.getKVClient();
        heartBeat();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException{
        Lease leaseClient = client.getLeaseClient();
        // 30秒租约
        long leaseId = leaseClient.grant(30).get().getID();
        String registerKey = ETCD_ROOT + serviceMetaInfo.getServiceNodeKey();

        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);
        // 添加到etcd中
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key, value, putOption).get();

        localRegisterSet.add(registerKey);
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo){
        // 删除注册中心信息
        kvClient.delete(ByteSequence.from(ETCD_ROOT + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));
        // 删除本地注册信息
        localRegisterSet.remove(ETCD_ROOT + serviceMetaInfo.getServiceNodeKey());
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey){
        String prefix = ETCD_ROOT + serviceKey + "/";
        try{
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(ByteSequence.from(prefix, StandardCharsets.UTF_8), getOption)
                    .get().getKvs();
            return keyValues.stream().map(keyValue -> {
                String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                return JSONUtil.toBean(value, ServiceMetaInfo.class);
            }).collect(Collectors.toList());

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy(){
        System.out.println("当前服务节点下线");
        // 取消注册的服务
        for(String key: localRegisterSet){
            try{
                kvClient.delete(ByteSequence.from(key, StandardCharsets.UTF_8)).get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(kvClient != null) kvClient.close();
        if(client != null) client.close();
    }

    @Override
    public void heartBeat(){
        // 创建定时任务
        CronUtil.schedule("*/10 * * * * *", (Task) () -> {
            for(String key: localRegisterSet){
                try{
                    // 得到key对应的value, 只有一个value, 通过get(0)获取
                    List<KeyValue> keyValues = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                            .get().getKvs();
                    // 节点已过期
                    if(keyValues.isEmpty()) continue;

                    KeyValue keyValue = keyValues.get(0);
                    String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                    ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                    register(serviceMetaInfo);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }


    // Etcd连接测试
    //    public static void main(String[] args) throws ExecutionException, InterruptedException{
    //        Client client = Client.builder().endpoints("http://localhost:2379").build();
    //
    //        KV kvClient = client.getKVClient();
    //        ByteSequence key = ByteSequence.from("key1".getBytes());
    //        ByteSequence value= ByteSequence.from("value1".getBytes());
    //
    //        kvClient.put(key, value).get();
    //        CompletableFuture<GetResponse> getResponseCompletableFuture = kvClient.get(key);
    //        GetResponse response = getResponseCompletableFuture.get();
    //        System.out.println(response.toString());
    //        System.out.println(response.getKvs().toString());
    //        kvClient.delete(key).get();
    //    }
}
