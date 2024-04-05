# rpc框架实现
通过rpc(remote procedure call), 远程过程调用, 去实现像调用本地方法一样去跨进程调用
![rpc框架图](assets/截屏2024-04-04%2014.46.07.png)
## rpc示例
1. 服务提供者
    provider-UserService
2. 服务消费者
    consumer, 获取远程类的代理对象, 然后直接调用类的方法调用远程方法
3. 公共接口
    消费者和提供者通过公共接口去发现同一个服务对象
4. web服务器
    Tomcat, Netty, Vert.x
5. 服务提供者的本地服务注册器
    根据服务名从ConcurrentHashMap中获取对应的实现类
6. 序列化与反序列化
    JDK序列化: Object对象序列化成byte数组, 需要对象实现serializable
7. 请求处理器
    vertx通过Handler<HttpServerRequest>实现请求处理
    处理接收到的请求, 找到对应的服务和方法, 反射实现调用, 封装结果并响应
    需要的参数: 服务名, 方法名, 参数类型, 参数列表
8. 响应处理器
    返回请求结果
    包含的参数: 数据, 数据类型, 异常, 提示消息

## 优化1: 全局配置加载
通过定义PRCConfig类, 写入默认配置, 在application.properties覆盖默认配置, 通过Hutool-Props工具类完成Config加载, 通过双检锁单例模式创建单例Config对象

## 优化2: 提供mock服务
定义全局配置, 是否使用mock, 然后在动态代理时读取mock配置, 并根据返回类型生成mock数据 

## 优化3: 使用不同的序列化器
第一种: JDK自带序列化实现
第二种: JSON序列化 可读性好, 但是数据量较大, 不能处理复杂的数据结构和循环引用
第三种: protobuf 二进制序列化, 数据量小
第四种: Hessian 二进制序列化, 传输效率高, 但必须实现Serializable接口
第五种: Kryo 只有Java, 速度快, 兼容性高
其他: 用户自定义实现, 通过**SPI机制**实现插件化扩展
> SPI机制: 服务提供者通过特定的配置文件将用户实现注册到系统中, 并通过反射机制动态加载这些实现, 在不修改框架代码的情况下实现解耦和扩展(例如JDBC驱动开发, ORM, Spring)
原生实现/自定义SPI实现(详细了解)