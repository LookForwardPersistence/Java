### 查看系统版本
- cat /etc/redhat-release
- uname -a
### 查看CentOS Linux防火墙状态
- systemctl status firewalld
### 打开防火墙
- systemctl start firewalld
### 关闭防火墙
- systemctl stop firewalld

### 定位文本内容
- 1、less ***.log  
- 2、输入 / “查找内容”

### 查看端口占用情况
~~~
netstat -lnp|grep 8080
~~~
### 授权
~~~
最高权限
chmod -R 777  文件夹明
~~~

### 查看linux空间情况
~~~
free -h
~~~
### linux 服务器件传数据
- 传文件指令
~~~
scp 文件名 远程服务账号@远程服务器ip:文件存放路径
eg: scp test.text root@10.192.20.220:/user/local
~~~
- 传文件夹
~~~
scp -r 文件夹名称 远程服务账号@远程服务器ip:文件存放路径
eg： scp -r rocketmq.4.4.0 root@10.192.20.220:/user/local
~~~

### 给文件夹及文件夹下所有文件授权

~~~
chmod 777 -R 文件夹名/
~~~
