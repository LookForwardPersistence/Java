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
