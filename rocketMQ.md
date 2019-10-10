- Name Server是一个几乎无状态节点，可集群部署，节点之间无任何信息同步。
- Broker部署相对复杂，Broker分为Master与Slave，一个Master可以对应多个Slave，但是一个Slave只能对应一个Master，Master与Slave的对应关系通过指定相同的BrokerName，不同的BrokerId来定义，BrokerId为0表示Master，非0表示Slave。Master也可以部署多个。每个Broker与Name Server集群中的所有节点建立长连接，定时注册Topic信息到所有Name Server。
- Producer与Name Server集群中的其中一个节点（随机选择）建立长连接，定期从Name Server取Topic路由信息，并向提供Topic服务的Master建立长连接，且定时向Master发送心跳。Producer完全无状态，可集群部署。
- Consumer与Name Server集群中的其中一个节点（随机选择）建立长连接，定期从Name Server取Topic路由信息，并向提供Topic服务的Master、Slave建立长连接，且定时向Master、Slave发送心跳。Consumer既可以从Master订阅消息，也可以从Slave订阅消息，订阅规则由Broker配置决定。
### RocketMQ安装
- git clone https://github.com/apache/rocketmq.git
-  mvn -Prelease-all -DskipTests clean install -U
- 配置conf/broker.conf
~~~
# broker集群名称
brokerClusterName = DefaultCluster

# broker名称
brokerName = broker-a

#broker编号0 表示 Master，>0 表示 Slave
brokerId = 0

#删除文件时间点，默认凌晨 4点
deleteWhen = 04

#文件保留时间，默认 48 小时
fileReservedTime = 48

#Broker 的角色异步复制Master: ASYNC_MASTER  Slave: SLAVE
#同步双写 Master: SYNC_MASTER Slave: SLAVE
brokerRole = SYNC_MASTER

#刷盘方式ASYNC_FLUSH 异步刷盘-SYNC_FLUSH 同步刷盘
flushDiskType = ASYNC_FLUSH

#broker ip地址
brokerIP1=ip

# broker端口 port可自由设置，一般设置10911 对外服务的监听端口
listenPort=19201

#namesrvAddr 服务ip及端口
namesrvAddr=ip:9876
#消息索引是否安全,默认为 false,文件恢复时选择文件检测点（commitlog.consumeque）的最小的与文件最后更新对比，如果为true，文件恢复时选择文件检测点保存的索引更新时间作为对比
messageIndexSafe=true

#是否允许Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true

# 是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true

# commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824

#默认允许的最大消息体默认4M
maxMessageSize= 4194304

# ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000

# 检测可用的磁盘空间大小,当磁盘被占用超过90%，消息写入会直接报错                    
diskMaxUsedSpaceRatio=90

#清除发送线程池任务队列的等待时间。如果系统时间减去任务放入队列中的时间小于waitTimeMillsInSendQueue，本次请求任务暂时不移除该任务
waitTimeMillsInSendQueue=5000

#发送消息线程池数量
sendMessageThreadPoolNums=64

#消息存储到commitlog文件时获取锁类型，如果为true使用ReentrantLock否则使用自旋锁
useReentrantLockWhenPutMessage=true

#创建Topic中默认的读队列数量
defaultReadQueueNums = 16

#创建Topic中默认的写队列数量
defaultWriteQueueNums = 16

#存储根路径
storePathRootDir=/usr/local/rocketMQ/store

#存储提交日志路径
storePathCommitLog= /usr/local/rocketMQ/store/commitlog

#消费队列存储路径
storePathConsumeQueue=/usr/local/rocketMQ/store/consumequeue

#消息索引存储路径
storePathIndex=/usr/local/rocketMQ/store/index

~~~
建议线上配置：同步复制+异步刷盘

同步复制和异步复制是指master节点与slave节点的关系（如果一个Broker组有Master和Slave，消息需要从Master复制到Slave节点上）
	同步复制	异步复制
策略	当数据成功写到内存中Master节点后立刻同步到slave节点中，当Slave也成功的前提下返回写成功状态	当数据成功写到内存中Master节点后，直接返回成功状态，异步将Master节点的数据存入Slave节点
优点	数据安全性高	性能比同步复制高
缺点	性能比异步复制低	数据可能丢失



同步刷盘和异步刷盘指内存和磁盘关系（RocketMQ最终消息是存储在磁盘上，这样既能保证断电后恢复，又可以让存储的消息量超出内存的限制，从客户端发送消息，开始写到内存，再写到磁盘上）
	同步刷盘	异步刷盘
策略	当数据成功写到内存后立刻刷盘，当数据也成功写到磁盘后才返回写成功状态	数据写到内存后，直接返回成功状态，异步将内存中的数据持久化到磁盘
优点	保证了数据的可靠性，保证了数据不会丢失	提高系统的吞吐量
缺点	同步刷盘效率低	不能保证数据的可靠性
3、	参考
https://www.cnblogs.com/qdhxhz/p/11116197.html
https://www.cnblogs.com/zhyg/p/10255656.html
http://hiant.github.io/2016/08/18/rocketmq-0x3/
https://www.cnblogs.com/linjiqin/p/7511062.html
http://jm.taobao.org/2017/01/12/rocketmq-quick-start-in-10-minutes/



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
- 参考 https://blog.csdn.net/licusliu/article/details/85248192  https://blog.csdn.net/zhangcongyi420/article/details/82593982
### rocketmq-console安装
- 修改项目配置文件application.properies
~~~
根据自身情况修改
#修改端口
server.port=8888
rocketmq.config.namesrvAddr=namesrv地址
#if you use rocketmq version < 3.5.8, rocketmq.config.isVIPChannel should be false.default true
rocketmq.config.isVIPChannel=false
~~~
- 打包
~~~
mvn clean packe -Dmaven.test.skip=true
~~~
- 在target中获取jar包部署
~~~
java -jar ***.jar包
~~~


### 常见问题
- 消费者无法监听生产者的消息
~~~
检查客户端的版本，最好是能与服务器的版本保持一致（如服务用4.*版本，客户端用3.*版本，大版本差别是有问题的）
~~~

### 生产者
~~~
  private DefaultMQProducer producer;
    @PostConstruct
    public void messageProducer() throws MQClientException {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setSendMessageWithVIPChannel(false);
        producer.start();
        System.out.println("消息生产者启动");
    }

    @Override
    public String sendMQMessage(MessageCommand command) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String msg=JSONObject.toJSONString(command);
        Message message = new Message(topic,tag,msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SendResult result = producer.send(message);
        stopWatch.stop();
        return result.getMsgId();
    }
~~~

### 消费者
~~~
public class MessageComsumer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topic,tag);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt:msgs){
                    try {
                        System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.err.println(messageExt.getBody());
                        MessageCommand messageCommand = JSONObject.parseObject(new String(messageExt.getBody(),"UTF-8"),MessageCommand.class);
                        //发送图文消息
                        sendImageMessage(messageCommand);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("消息消费者已经启动");
    }
    }
~~~

### 其他问题
- broker无法启动
~~~
修改bin/runbroker.sh文件
JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn1g"
~~~

