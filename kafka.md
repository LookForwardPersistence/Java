### 启动zoookeepr
- bin/zookeeper-server-start.sh config/zookeeper.properties

### 启动kafka Broker
- bin/kafka-server-start.sh config/server.properties

### 单节点-单代理配置：
### 创建主题
- bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-name

### 主体列表：
- bin/kafka-topics.sh --list --zookeeper localhost:2181

### 启动生产者，并发送消息：
- bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Dawn-kafka

### 启动消费者（09版本后）：
- bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic Dawn-kafka --from-beginning

### 修改主题：
- bin/kafka-topics.sh --zookeeper localhost:2181 --alter --topic Dawn-kafka --partitions 2

### 删除主题
- bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic topic_name

### 异常 Exception key=null  topic
- kafka service.properties 配置 listenners 服务地址 zookeeper服务地址
- 换一个topic
