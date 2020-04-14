- jenkins下载安装
~~~

1、curl -O http://static.outsidelook.cn/software/jenkins/jenkins.war
 官网：curl -O http://mirrors.jenkins.io/war-stable/latest/jenkins.war

运行jenkins
2、sudo nohup java -jar jenkins.war --httpPort=12000 &
这里将jenkins的端口改为 12000

------------------------------------
安装：
sudo rpm -ih jenkins-1.562-1.1.noarch.rpm

启动：
sudo service jenkins start

jenkins 端口及jenkins_user：
 vi /etc/sysconfig/jenkins

在jenkins配置文件中配置 jdk
vi /etc/init.d/jenkins

查看全部端口
netstat -apn

查看某一端口
netstat -ef|grep 端口

注意：如果安装最新版本的jenkins需要设置linux默认jdk版本
修改linux默认jdk版本：
[root@EABOMMST bin]# ln -s -f /usr/java/jdk1.8.0_111/jre/bin/java
[root@EABOMMST bin]# ln -s -f /usr/java/jdk1.8.0_111/bin/javac

linux 生成key ：ssh-keygen -t rsa -C 邮箱（账号）
秘钥路径：/root/.ssh
~~~
