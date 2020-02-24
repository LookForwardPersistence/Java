### redis 安装
~~~
$ wget http://download.redis.io/releases/redis-3.0.6.tar.gz
$ tar xzf redis-3.0.6.tar.gz
$ cd redis-3.0.6
$ make
src/redis-service       --启动服务
src/redis-cli            --开启编辑命令
~~~

#### redis 配置

- 运行远程访问设置
~~~
redis.conf 中的protected-mode yes 改为 protected-mode no
~~~
- 开启后台守护进程
~~~
redis.conf 中的 daemeonize no 改为 daemenize yes
~~~
- 启动
~~~
src/redis-server redis.conf
~~~
- 链接到redis 控制台
~~~
./redis-cli
~~~
- 关闭运行的redis的端口
~~~
./redis-cli -p 6379 shutdown
~~~


### redis 发布订阅
- 订阅
~~~
subscribe channel
~~~
- 发布
~~~
publish channel message
~~~
- 取消订阅
~~~
unsubscribe channel
~~~


## Redisson springboot集群配置

### springboot.properties
~~~
#Redisson settings
spring.redis.redisson.config=classpath:redisson.yaml
~~~

### redison.yaml
~~~
clusterServersConfig:
  #连接空闲超时，单位：毫秒
  idleConnectionTimeout: 10000
  pingTimeout: 1000
  #同任何节点建立连接时的等待超时。时间单位是毫秒
  connectTimeout: 10000
  #等待节点回复命令的时间。该时间从命令发送成功时开始计时
  timeout: 3000
  #如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时
  retryAttempts: 3
  #在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒
  retryInterval: 1500
  failedSlaveReconnectionInterval: 3000
  failedSlaveCheckInterval: 3
  password: null
  #每个连接的最大订阅数量
  subscriptionsPerConnection: 5
  #在Redis节点里显示的客户端名称
  clientName: center-strategy
  loadBalancer: !<org.redisson.connection.balancer.RoundRobinLoadBalancer> {}
  slaveSubscriptionConnectionMinimumIdleSize: 1
  slaveSubscriptionConnectionPoolSize: 50
  slaveConnectionMinimumIdleSize: 32
  slaveConnectionPoolSize: 64
  masterConnectionMinimumIdleSize: 32
  masterConnectionPoolSize: 64
  readMode: "SLAVE"
  nodeAddresses:
  - "redis://localhost:7000"
  - "redis://localhost:7001"
  scanInterval: 1000
threads: 0
nettyThreads: 0
codec: !<org.redisson.codec.JsonJacksonCodec> {}
transportMode: "NIO"
~~~


### rediss RedisTemplate 获取value 多双引号问题解决
StringRedisSerializer 替换  Jackson2JsonRedisSerializer
~~~
# 解决前
 public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
~~~
~~~
# 解决后
 public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        //序列化为json字符串 modified by FQH 解决redision value多双引号问题
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
~~~
