### zookeeper 集群
- 新增存储目录  /usr/zookeeper/data
- 新增集群id 在data 下新增文件myid 
- conf/zoo.cfg 配置
~~~
dataDir=/usr/zookeeper/data
server.1=127.0.0.1:2999:3999
server.2=127.0.0.2:2999:3999
server.3=127.0.0.3:2999:3999
~~~
- 启动zookeeper
~~~
bin/zkServer.sh start
~~~
- 关闭zookeeper
~~~
bin/zkServer.sh stop
~~~
- 查看状态
~~~
bin/zkServer.sh status
~~~
