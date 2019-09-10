# dubbo
- dubbo-admin 配置
~~~
https://github.com/apache/dubbo-admin

dubbo-admin\dubbo-admin-server\src\main\resources\applicationproperties
application.properties配置项说明

dubbo-admin-server
推荐使用，配置中心地址，比如admin.config-center="zookeeper://127.0.0.1:2181"
需要在配置中心中，配置注册中心和元数据中心地址配置格式如下：
# centers in dubbo2.7
admin.registry.address=zookeeper://127.0.0.1:2181
admin.config-center=zookeeper://127.0.0.1:2181
admin.metadata-report.address=zookeeper://127.0.0.1:2181
~~~
- 集群配置 backup
~~~
admin.registry.address=zookeeper://127.0.0.1:2181?backup=127.0.0.1:2181,127.0.0.1:2181
~~~

- 打包
~~~
Clone source code on develop branch git clone https://github.com/apache/dubbo-admin.git

Specify registry address in dubbo-admin-server/src/main/resources/application.properties

Build

mvn clean package
Start

mvn --projects dubbo-admin-server spring-boot:run
OR
cd dubbo-admin-distribution/target; java -jar dubbo-admin-0.1.jar
Visit http://localhost:8080
~~~
