### linux 切换jdk版本
~~~
如何在多个jdk版本中切换
1.sudo update-alternatives --display java 查看安装了几个java
2.sudo update-alternatives --config java 设置java的版本
alternatives --config javac 设置javac的版本
link：https://blog.csdn.net/eggsdevil/article/details/53729424
~~~
### 通过localhost:8080 直接访问在root外的项目
~~~
在tomcat配置文件conf/server.xml 添加配置
在<Host>节点添加
 <Context path="" docBase="/usr/local/tomcat7/webapps/webName" debug="0" reloadable="true" crossContext="true" />
~~~
