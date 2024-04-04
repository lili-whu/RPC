# rpc框架实现
通过rpc(remote procedure call), 远程过程调用, 去实现像调用本地方法一样去跨进程调用

## rpc示例
1. 服务提供者
    provider-UserService
2. 服务消费者
    consumer, 获取远程类的代理对象, 然后直接调用类的方法调用远程方法
3. 公共接口
    消费者和提供者通过公共接口去发现同一个服务对象
4. web服务器
    Tomcat, Netty, Vert.x
5. 本地服务注册器
    根据服务名获取对应的实现类
6. 序列化与反序列化
    JDK序列化: Object对象序列化成byte数组, 需要对象实现serializable
7. 请求处理器
    vertx通过Handler<HttpServerRequest>实现请求处理
    处理接收到的请求, 找到对应的服务和方法, 反射实现调用, 封装结果并响应
    需要的参数: 服务名, 方法名, 参数类型, 参数列表
8. 响应处理器
    返回请求结果
    包含的参数: 数据, 数据类型, 异常, 提示消息
