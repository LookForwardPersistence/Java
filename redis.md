### redis 安装
~~~
$ wget http://download.redis.io/releases/redis-3.0.6.tar.gz
$ tar xzf redis-3.0.6.tar.gz
$ cd redis-3.0.6
$ make
src/redis-service redis.conf      --后台启动服务
src/redis-cli            --开启编辑命令

关闭服务：redis-cli -h ip -p 端口 -a 密码 shutdown


#开启线程守护,后台启动
daemonize yes
#redis启动端口设置为7001
port  7001
#关闭保护模式,可以远程访问redis
protected-mode no
#启用redis群集支持
cluster-enabled yes
#Redis群集节点不可用的最长时间
cluster-node-timeout 5000
#集群配置文件,官网上说:请注意，尽管有此选项的名称，但这不是用户可编辑的配置文件. 但是实际上我还是编辑了它
cluster-config-file nodes.conf
#开启AOF日志
appendonly yes
#设置你的密码,当然你也可以不设置,直接注释掉,那么你的redis在关闭保护模式的情况下,任何人都可以进行操作了.
masterauth password
requirepass password
~~~
### 查看安装目录
~~~
ls -l /proc/进程号/cwd
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

### Spring 集成redis
~~~
spring4.3.2
 <!--redis-->
      <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-redis</artifactId>
        <version>1.5.2.RELEASE</version>
      </dependency>
      <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>2.9.3</version>
      </dependency>
      
  spring-redis.xml
  
  <?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!--引入配置文件-->
    <context:property-placeholder location="redis.properties" ignore-unresolvable="true"></context:property-placeholder>
    <!-- jedis 配置 -->
    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <property name="maxIdle" value="${redis.pool.maxIdle}" />
        <property name="maxWaitMillis" value="${redis.pool.maxWaitMillis}" />
        <property name="testOnBorrow" value="${redis.pool.testOnBorrow}" />
    </bean>

    <!-- redis服务器中心 -->
    <bean id="connectionFactory"
          class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <property name="poolConfig" ref="poolConfig" />
        <property name="port" value="${redis.port}" />
        <property name="hostName" value="${redis.host}" />
         <property name="password" value="${redis.pwd}" />
        <property name="database" value="${redis.database}"/>
        <property name="timeout" value="${redis.timeout}"></property>
    </bean>

    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
        <property name="connectionFactory" ref="connectionFactory" />
        <property name="keySerializer">
            <bean
                    class="org.springframework.data.redis.serializer.StringRedisSerializer" />
        </property>
        <property name="valueSerializer">
            <bean
                    class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer" />
        </property>
    </bean>
    <!-- 配置缓存 -->
    <bean id="cacheManager" class="org.springframework.data.redis.cache.RedisCacheManager">
        <constructor-arg ref="redisTemplate" />
    </bean>
</beans>


redis.properties

redis.host=ip
redis.port=6379
redis.pwd=passwar
redis.database=0
redis.timeout=10000
redis.userPool=true
#最大空闲连接数
redis.pool.maxIdle=500
#最小空闲连接数, 默认0
redis.pool.minidle=0
#最大连接数, 默认8个
redis.pool.maxTotal=10000
#获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
redis.pool.maxWaitMillis=10000
#资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除  默认1800000毫秒(30分钟)
redis.pool.minEvictableTimeMillis=30000
#每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
redis.pool.numTestsPerEvictionRun=10
#对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
redis.pool.numTestsPerEvictionRunMills=10000
#空闲资源的检测周期(单位为毫秒) 如果为负数,则不运行逐出线程, 默认-1
redis.pool.timeBetweenEvictionRunsMillis=300000
#在获取连接的时候检查有效性, 默认false
redis.pool.testOnBorrow=true
#
redis.pool.testOnReturn=true
#是否开启空闲资源监测, 默认false
redis.pool.testWhileIdle=true

redis.enableTransactionSupport=true
~~~
