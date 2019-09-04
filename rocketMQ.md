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
    }
    }
~~~
