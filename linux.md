### 新增linux服务账号密码
- useradd 用户名
- passwd 密码
- 修改密码 passwd 用户名
### 查看系统版本
- cat /proc/version
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

~~~
# uname -a # 查看内核/操作系统/CPU信息
# head -n 1 /etc/issue # 查看操作系统版本
# cat /proc/cpuinfo # 查看CPU信息
# hostname # 查看计算机名
# lspci -tv # 列出所有PCI设备
# lsusb -tv # 列出所有USB设备
# lsmod # 列出加载的内核模块
# env # 查看环境变量资源
# free -m # 查看内存使用量和交换区使用量
# df -h # 查看各分区使用情况
# du -sh <目录名> # 查看指定目录的大小
# grep MemTotal /proc/meminfo # 查看内存总量
# grep MemFree /proc/meminfo # 查看空闲内存量
# uptime # 查看系统运行时间、用户数、负载
# cat /proc/loadavg # 查看系统负载磁盘和分区
# mount | column -t # 查看挂接的分区状态
# fdisk -l # 查看所有分区
# swapon -s # 查看所有交换分区
# hdparm -i /dev/hda # 查看磁盘参数(仅适用于IDE设备)
# dmesg | grep IDE # 查看启动时IDE设备检测状况网络
# ifconfig # 查看所有网络接口的属性
# iptables -L # 查看防火墙设置
# route -n # 查看路由表
# netstat -lntp # 查看所有监听端口
# netstat -antp # 查看所有已经建立的连接
# netstat -s # 查看网络统计信息进程
# ps -ef # 查看所有进程
# top # 实时显示进程状态用户
# w # 查看活动用户
# id <用户名> # 查看指定用户信息
# last # 查看用户登录日志
# cut -d: -f1 /etc/passwd # 查看系统所有用户
# cut -d: -f1 /etc/group # 查看系统所有组
# crontab -l # 查看当前用户的计划任务服务
# chkconfig –list # 列出所有系统服务
# chkconfig –list | grep on # 列出所有启动的系统服务程序
# rpm -qa # 查看所有安装的软件包

https://blog.csdn.net/lhf_tiger/article/details/7102753
~~~

### vim添加行号
~~~
vim 文件
:set nu
~~~


### 查找并删除
~~~
find *.tmp -exec rm -rf {} \;
~~~
- 删除4分钟前的后缀为.tmp的文件（-mtime n  n天前）
~~~
find *.tmp -mmin 4 -exec rm -rf {} \;
~~~
### 监控
- 查看内存
~~~
free -h
~~~
- 每2秒监控一次内存
~~~
watch -n 2 -d free
~~~
- vmstat命令监视虚拟内存使用情况()vmstat是Virtual Meomory Statistics（虚拟内存统计）的缩写，可对操作系统的虚拟内存、进程、CPU活动进行监视
~~~
vmstat 5 
~~~

- 不用crontab实现定时任务
~~~
# do 指令 脚本; 休眠1秒; done;
while true ; do sh dl.sh; sleep 1; done;

# dl.sh文件
#!/bin/sh
 echo "Auto to move the temp ok!"
 find *.tmp -mmin 2 -exec rm -rf {} \;
~~~

### 压缩、解压命令
~~~
.tar
压缩：tar cvf FileName.tar FileName
解压：tar xvf FileName.tar
--------------------------------------------- 
.gz
解压1：gunzip FileName.gz 
解压2：gzip -d FileName.gz 
压缩：gzip FileName 
.tar.gz 
解压：tar zxvf FileName.tar.gz 
压缩：tar zcvf FileName.tar.gz DirName 
--------------------------------------------- 
.bz2 
解压1：bzip2 -d FileName.bz2 
解压2：bunzip2 FileName.bz2 
压缩： bzip2 -z FileName 
.tar.bz2 
解压：tar jxvf FileName.tar.bz2 
压缩：tar jcvf FileName.tar.bz2 DirName 
--------------------------------------------- 
.bz 
解压1：bzip2 -d FileName.bz 
解压2：bunzip2 FileName.bz 
压缩：未知 
.tar.bz 
解压：tar jxvf FileName.tar.bz 
压缩：未知 
--------------------------------------------- 
.Z 
解压：uncompress FileName.Z 
压缩：compress FileName 
.tar.Z 
解压：tar Zxvf FileName.tar.Z 
压缩：tar Zcvf FileName.tar.Z DirName 
--------------------------------------------- 
.tgz 
解压：tar zxvf FileName.tgz 
压缩：未知 
.tar.tgz 
解压：tar zxvf FileName.tar.tgz 
压缩：tar zcvf FileName.tar.tgz FileName 
--------------------------------------------- 
.zip 
解压：unzip FileName.zip 
压缩：zip FileName.zip DirName 
--------------------------------------------- 
.rar 
解压：rar a FileName.rar 
压缩：rar e FileName.rar 
~~~
-- vim快捷键
~~~
一、查找

查找命令

/pattern<Enter> ：向下查找pattern匹配字符串
?pattern<Enter>：向上查找pattern匹配字符串
使用了查找命令之后，使用如下两个键快速查找：
n：按照同一方向继续查找
N：按照反方向查找

字符串匹配

pattern是需要匹配的字符串，例如：

1:  /abc<Enter>      #查找abc
2:  / abc <Enter>    #查找abc单词（注意前后的空格） 
  除此之外，pattern还可以使用一些特殊字符，包括（/、^、$、*、.），其中前三个这两个是vi与vim通用的，“/”为转义字符。

1:  /^abc<Enter>    #查找以abc开始的行 
2:  /test$<Enter>    #查找以abc结束的行 
3:  //^test<Enter>    #查找^tabc字符串
 
二、替换

基本替换

1:  :s/vivian/sky/         #替换当前行第一个 vivian 为 sky
2:  :s/vivian/sky/g     #替换当前行所有 vivian 为 sky
3:  :n,$s/vivian/sky/     #替换第 n 行开始到最后一行中每一行的第一个 vivian 为 sky
4:  :n,$s/vivian/sky/g     #替换第 n 行开始到最后一行中每一行所有 vivian 为 sky
5:  （n 为数字，若 n 为 .，表示从当前行开始到最后一行）
6:  
7:  :%s/vivian/sky/        #（等同于 :g/vivian/s//sky/） 替换每一行的第一个 vivian 为 sky
8:  :%s/vivian/sky/g    #（等同于 :g/vivian/s//sky/g） 替换每一行中所有 vivian 为 sky
  可以使用 #或+ 作为分隔符，此时中间出现的 / 不会作为分隔符

1:  :s#vivian/#sky/#         替换当前行第一个 vivian/ 为 sky/
2:  :%s+/oradata/apras/+/user01/apras1+ （
3:  使用+ 来 替换 / ）： /oradata/apras/替换成/user01/apras1/
http://localhost:8080/#/apply/mediaApply?activeNav=1&action=create&location=
~~~
