pm2 start projectName
pm2 restart projectName
### 配置全局变量
配置软连接
~~~
ln -s /usr/local/node/node-v12.10.0-linux-x64/bin/node /usr/local/bin/node
ln -s /usr/local/node/node-v12.10.0-linux-x64/bin/npm /usr/local/bin/npm
~~~

~~~
#
  tar xvf node-v0.10.28.tar.gz 
#  cd node-v0.10.28 
#  ./configure 
# make 
# make install 
# cp /usr/local/bin/node /usr/sbin/ 
 
查看当前安装的Node的版本 
# node -v 
 
v0.10.28

查看linux系统信息
cat /proc/version


Linux下查看mysql、apache是否安装,并卸载。
指令 ps -ef|grep mysql 得出结果

1,mysql 的守护进程是mysqld
如果已经安装则:
[root@localhost ~]# service mysqld start 
启动 MySQL：                                               [确定]
如果没有安装则:
[root@localhost ~]# service mysqld start 
mysqld:未被识别的服务
你可以看看你的服务是否已经添加到linux上
[root@localhost ~]# chkconfig --list mysqld 
mysqld          0:关闭  1:关闭  2:关闭  3:关闭  4:关闭  5:启用  6:关闭
没有安装则: 
[root@localhost ~]# chkconfig --list mysqld 
在mysqld服务中读取信息时出错,没有那个文件或目录
一旦你启动了服务，可以这样检查服务器是否在运行
[root@localhost ~]# ps -el | grep mysqld 
4 S     0  1796     1  0  85   0 -  1513 wait   ?        00:00:00 mysqld_safe 
4 S    27  1856  1796  0  78   0 - 34055 -      ?        00:00:00 mysqld 
[root@localhost ~]# 
如果你看到有，就说明服务器安装起来了～～
linux下查看apache是否安装及版本
如通是通过rpm包安装的话直接用下面的命令： 
rpm -q httpd
也可以使用如下两种方法： 
httpd   -v
apachectl   -v
linux下卸载mysql方法
a. 查找已安装的myslq 版本： 
#rpm  -qa | grep  mysql （注意大小写，如果mysql 不行就换MySQL）
显示: 
[root@localhost ~]# rpm  -qa | grep  mysql 
mysql-5.0.77-4.el5_4.2 
mysql-server-5.0.77-4.el5_4.2 
php-mysql-5.2.13-1.el5.art 
复制代码 
在屏幕上将显示已安装的mysql包名如：mysql-5.0.77-4.el5_4.2 ;
将搜索出的包名卸载：
#rpm -e  –nodeps mysql-5.0.77-4.el5_4.2  （nodeps表示强制删除）
提示 
error: package –nodeps is not installed 
error: package mysql-.0.77-4.e15_.2 is not installed
Linux查看mysql 安装路径
一、查看文件安装路径
由于软件安装的地方不止一个地方，所有先说查看文件安装的所有路径(地址)。
这里以mysql为例。比如说我安装了mysql,但是不知道文件都安装在哪些地方、放在哪些文件夹里，可以用下面的命令查看所有的文件路径 
在终端输入：
whereis mysql
回车，如果你安装好了mysql，就会显示文件安装的地址，例如我的显示(安装地址可能会不同)
[root@localhost ~]# whereis mysql 
mysql: /usr/bin/mysql /usr/lib/mysql /usr/share/mysql /usr/share/man/man1/mysql.1.gz
二、查询运行文件所在路径(文件夹地址)
如果你只要查询文件的运行文件所在地址，直接用下面的命令就可以了(还是以mysql为例)：
which mysql
终端显示:
[root@localhost ~]# which mysql 
/usr/bin/mysql
~~~
