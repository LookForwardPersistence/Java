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
