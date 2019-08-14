### RocketMQ安装
- git clone https://github.com/apache/rocketmq.git
-  mvn -Prelease-all -DskipTests clean install -U
- 配置conf/broker.conf
~~~
# broker集群名称
brokerClusterName = DefaultCluster
# broker名称
brokerName = broker-a
#broker编号
brokerId = 0
#删除文件时间点，默认凌晨 4点
deleteWhen = 04
#文件保留时间，默认 48 小时
fileReservedTime = 48
#Broker 的角色异步复制Master: ASYNC_MASTER  Slave: SLAVE
#同步双写 Master: SYNC_MASTER Slave: SLAVE
brokerRole = ASYNC_MASTER
#刷盘方式ASYNC_FLUSH 异步刷盘-SYNC_FLUSH 同步刷盘
flushDiskType = ASYNC_FLUSH
#broker ip
brokerIP1=ip
#namesrvAddr 服务ip及端口
namesrvAddr=ip:9876
#
messageIndexSafe=true
#是否允许Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true

waitTimeMillsInSendQueue=5000
#发送消息线程池数量
sendMessageThreadPoolNums=64

useReentrantLockWhenPutMessage=true
#创建Topic中默认的读队列数量
defaultReadQueueNums = 16
#创建Topic中默认的写队列数量
defaultWriteQueueNums = 16
# broker端口 port可自由设置，一般设置10911
listenPort=19201
storePathRootDir=/usr/local/rocketMQ/store
storePathCommitLog= /usr/local/rocketMQ/store/commitlog
#消费队列存储路径
storePathConsumeQueue=/usr/local/rocketMQ/store/consumequeue
#消息索引存储路径
storePathIndex=/usr/local/rocketMQ/store/index
~~~
- cd distribution/target/apache-rocketmq
- start name server 
~~~
  > nohup sh bin/mqnamesrv &
  > tail -f ~/logs/rocketmqlogs/namesrv.log
~~~
- start broker
~~~
 > nohup sh bin/mqbroker -n localhost:9876 &
  > tail -f ~/logs/rocketmqlogs/broker.log 
~~~
- 发送接收消息
~~~
 > export NAMESRV_ADDR=localhost:9876
 > sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
 SendResult [sendStatus=SEND_OK, msgId= ...

 > sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
 ConsumeMessageThread_%d Receive New Messages: [MessageExt...
~~~
- shutdown servers
~~~
> sh bin/mqshutdown broker
> sh bin/mqshutdown namesrv
~~~

- 查看消费者推荐消息
~~~
sh bin/mqadmin consumerProgress -g dawnGroup(组)
~~~
- 安装参考 https://blog.csdn.net/licusliu/article/details/85248192
### 可视化管理
