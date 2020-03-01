### 基于docker安装应用

- 安装mysql集群，主从
~~~
docker pull mysql:5.7
#master
docker run --name mysql-master --privileged=true -v /home/mysql/master-data:/var/lib/mysql -p 3339:3306 -e MYSQL_ROOT_PASSWORD=csotdawn -d mysql:5.7
#slave
docker run --name mysql-slave --privileged=true -v /home/mysql/slave-data:/var/lib/mysql -p 3340:3306 --link mysql-master:master -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7

### 2、进入主容器
```
docker exec -it mysql-master /bin/bash
mysql -uroot -proot

grant replication slave on *.* to 'test'@'%' identified by '123456';
flush privileges;
// 查看主的状态
show master status;
```

### 3、进入savle开启数据同步
```
docker exec -it mysql-slave /bin/bash
mysql -uroot -proot
change master to master_host='master', master_user='test', master_password='123456', \
master_port=3306, master_log_file='mysql-bin.000003', master_log_pos=589, master_connect_retry=30;

start slave;
// 查看从的状态	
show slave status\G;
------------------------------------------------------------第二--------------------
#查看
docker ps

#navicate连接数据库
ip：宿主ip
映射端口：3339（master），3340（slave）
账号：root
密码：csotdawn
~~~
- 配置master
~~~
#进入master
docker exec -it f431dd7d69de（容器id） /bin/bash（或docker exec -it dawnmysql(容器名称) /bin/bash）
#安装vim
apt-get install vim
apt-get update
apt-get install vim

#编辑配置文件
vi my.cnf
#同一局域网注意为疑似
service-id:100
#开启二进制功能，可以随便取
log-bin=mysql-bin

#重启mysql
servie mysql restart
#重启docker
docker start dawnmysql

#Master数据库创建数据同步用户，授予用户 slave REPLICATION SLAVE权限和REPLICATION CLIENT权限，用于在主从库之间同步数据。
#进入master的mysql
mysql -uroot -p

 create user 'slave'@'%' identified by 'csotdawn';
 GRANT REPLICATION SLAVE,REPLICATION CLIENT ON *.* TO 'slave'@'%';
 --------------------------------------------------------------
~~~

参考
https://www.cnblogs.com/songwenjie/p/9371422.html

### rabbitmq安装
~~~
docker pull rabbitmq
--management才有图形界面
docker run -d --name rabbitmq -p 5671:5671 -p 5672:5672 -p 4369:4369 -p 25672:25672 -p 15671:15671 -p 15672:15672 -p 15674:15674 -p 15670:15670 -p 15673:15673 --restart=always xiaochunping/rabbitmq:management
~~~
### redis 安装
~~~
docker pull redis
docker run -d --name redis -p 6379:6379 redis redis-server --requirepass "csotdawn" --appendonly yes

~~~
## elasticsearch6.4.0 及 elasticsearch-head插件

- 创建挂载目录，目录下新建elasticsearch.yml

```
mkdir -p /root/docker/elasticsearch/config
vi /root/docker/elasticsearch/config/elasticsearch.yml
```
elasticsearch.yml文件内容如下：
```
cluster.name: "docker-es-cluster"
network.host: 0.0.0.0
discovery.zen.minimum_master_nodes: 1

#设置HTTP开启状态
http.enabled: true

#设置运行跨域访问
http.cors.enabled: true
http.cors.allow-origin: "*"
http.max_content_length: 500mb
```

- 运行es6.4版本并把配置文件挂载出来（方便修改es跨域，head插件需要）
```
docker run -d --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" -v /etc/timezone:/etc/timezone -v /etc/localtime:/etc/localtime -v /root/docker/elasticsearch/data:/usr/share/elasticsearch/data -v /root/docker/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml elasticsearch:6.4.0
```

- 运行head插件 http://192.168.100.249:9100/
```
docker run -d --name es-head -p 9100:9100 xiaochunping/elasticsearch-head:5
```


## IK分词器的安装
elasticsearch分词器，对中文分词并不是太友好。这里我们可以下载开源的IK分词器，来解决这一问题。
- 下载资源
```
https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.4.0
```
IK与ES版本兼容 ,详情请参考https://github.com/medcl/elasticsearch-analysis-ik


1. 普通方式安装 【或者参考：https://www.cnblogs.com/Hero-/p/10070575.html】
1.download or compile

 - optional 1 - download pre-build package from here: https://github.com/medcl/elasticsearch-analysis-ik/releases

    create plugin folder `cd your-es-root/plugins/ && mkdir ik`
    
    unzip plugin to folder `your-es-root/plugins/ik`

 - optional 2 - use elasticsearch-plugin to install ( supported from version v5.5.1 ):

    ```
    ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.4.0/elasticsearch-analysis-ik-6.4.0.zip
    ```

   NOTE: replace `6.4.0` to your own elasticsearch version

 2.restart elasticsearch

2. docker容器启动的es安装 IK分词器
 1. 方式一：使用 docker cp 命令把下载解压的分词器拷贝进容器中
 2. 方式二：直接挂载目录到容器中
```
docker run -d --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" -v /root/docker/elasticsearch/data:/usr/share/elasticsearch/data -v /root/docker/elasticsearch/plugins/analysis-ik-6.4.0:/usr/share/elasticsearch/plugins/analysis-ik-6.4.0 -v /root/docker/elasticsearch/plugins/analysis-pinyin-6.4.0:/usr/share/elasticsearch/plugins/analysis-pinyin-6.4.0 -v /root/docker/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml elasticsearch:6.4.0
```
 3. 自定义镜像
```
FROM elasticsearch:6.4.0
RUN mkdir -p /usr/share/elasticsearch/plugins/ik
RUN cd /usr/share/elasticsearch/plugins/ik && wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.4.0/elasticsearch-analysis-ik-6.4.0.zip && unzip elasticsearch-analysis-ik-6.4.0.zip
```
然后在命令行运行：
```
docker build . -t es_ik:6.4.0
```


## docker部署logstash
使用ElasticSearch，需要将MySQL内的数据同步到ElasticSearch中去。根据网上文章，觉得logstash属于比较好的同步工具。
不想被logstash环境的搭建与配置困扰。使用docker制作一个镜像，然后可以做到到处运行
```
docker pull docker.elastic.co/logstash/logstash:6.4.0
```
- 管道配置
你需要将管道配置文件放在Logstash可以找到的地方，这是很有必要的。默认情况下，容器中Logstash的管道配置可以在/usr/share/logstash/pipeline中找到。
我们使用绑定挂载卷来提供配置通过docker run命令：
```
docker run --rm -it -v ~/pipeline/:/usr/share/logstash/pipeline/ docker.elastic.co/logstash/logstash:6.4.0
```

- Settings
镜像给配置设置提供了一些方法，传统的方法是提供一个可以自定义的logstash.yml文件，但是也可以使用环境变量来定义设置。
设置文件也可以通过绑定挂载来提供。Logstash希望这些文件的路径是/usr/share/logstash/config/。
 + 可以通过提供一个目录来包含所有需要的文件：
```
docker run --rm -it -v ~/settings/:/usr/share/logstash/config/ docker.elastic.co/logstash/logstash:6.4.0
```
 + 或者挂载单个文件：
```
docker run --rm -it -v ~/settings/logstash.yml:/usr/share/logstash/config/logstash.yml docker.elastic.co/logstash/logstash:6.4.0
```
>NOTE：绑定挂载的配置文件在容器中的权限等同于文件在宿主机上的权限。确定文件的权限是可以被读取的，并且理想情况下对于容器Logstash用户(UID1000)不可写。原文： Be sure to set permissions such that the files will be readable and, ideally, not writeable by the container’s logstash user (UID 1000).

- 同步数据 mysql -> es
根据上面部署logstash知识，我们可以通过挂载目录或者文件的方式来启动管道。
但是同步数据库到es的功能需要使用到 **logstash-input-jdbc** 插件，这里需要我们定义官方镜像进行自定义。
 + 定义镜像：
    直接使用gitlab上实战项目下的 docker-logstash 目录中的 Dockerfile构建镜像单独启动，也可以使用 README 文件中的docker-compose来启动elk环境。

 + 编写管道配置文件（参考 jdbc-course.sql 文件，记住自行修改下面的数据库和es地址）：
	```
	input {
	     jdbc {
	         # 驱动
	         jdbc_driver_library => "mysql-connector-java-5.1.47.jar"
	         # 驱动类名
	         jdbc_driver_class => "com.mysql.jdbc.Driver"
	         # mysql 数据库链接,shop为数据库名
	         jdbc_connection_string => "jdbc:mysql://ip:3306/test"
	         # 用户名和密码
	         jdbc_user => "root"
	         jdbc_password => "root"
	         jdbc_paging_enabled => "true"
	         jdbc_page_size => "1000"
	         jdbc_default_timezone =>"Asia/Shanghai"
	         # 设置监听间隔  各字段含义（由左至右）分、时、天、月、年，全部为*默认含义为每分钟都更新
	         schedule => "* * * * *"
	         # 执行的sql 文件路径+名称
	         #statement_filepath => "D:/software/logstash-6.2.2/logstash-6.2.2/mysql/jdbc.sql"
	         # 执行的sql语句
	         statement => "select * from mysql_es_employee where updatetime > :sql_last_value"
	         use_column_value => true
	         tracking_column => "updatetime"
	         last_run_metadata_path => "./logstash_jdbc_last_run"
	       }
	}
	output {
	      elasticsearch {
	         # ES的IP地址及端口
	         hosts => ["ip:9200"]
	         #user => "elastic"
	         #password => ""
	         # 索引名称
	         index => "employee"
	         # 需要关联的数据库中有有一个id字段，对应类型中的id
	         document_id => "%{id}"
	      }
	      stdout {
	         # JSON格式输出
	         codec => json_lines
	     }
	 } 

	```

## zipkin  
使用docker-compose方式启动zipkin，由于上面我们部署过es，es也不直接默认把数据存在mysql性能要好，所以zipkin也把数据存到es中。
***记住要修改compose配置文件中的es_host地址为自己的es地址***
```
docker-compose -f docker-compose-elasticsearch.yml up -d
```
