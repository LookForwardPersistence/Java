### RocketMQ安装
- git clone https://github.com/apache/rocketmq.git
-  mvn -Prelease-all -DskipTests clean install -U
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
sh bin/mqadmin consumberProgress -g dawnGroup(组)
~~~
- 安装参考 https://blog.csdn.net/licusliu/article/details/85248192
### 可视化管理
