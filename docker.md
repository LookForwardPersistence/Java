### docker 基本操作（windows）
启动 Docker for widows，启动成功，在cmd命令框输入 PowerShell，打开命令窗口
- 启动一次容器操作：docker run IMAGE_NAME
启动一个容器输出hello Dawn。由于刚装上Docker，没有任何镜像，所以会先下载一个最新的ubuntu的docker镜像。一次操作容器在处理完操作后会立即关闭容器。
~~~
docker run ubuntu echo "Hello Dawn"
~~~
- 启动交互式容器：docker run -i -t -name（自定义名称，方便重启容器）
启动交互式的容器，就是类似虚拟机、云主机的操作方式，操作完一个命令后仍然可以继续
~~~
docker run -i -t ubuntu /bin/bash
~~~
- 查看容器：docker ps [-a][-l]
- 查看指定容器： docker inspect name|id
- 重新启动停止的容器：docker start -i name
- 删除停止的容器：docker rm name|id
- 以守护方式运行容器：连续执行 ctrl+p ctrl+q
- 查看容器日志：docker logs [-f][-t][-tail] IMAGE_NAME
- 查看容器内进程:docker top IMAGE_NAME
- 停止守护容器： docker stop IMAGE_NAME
- 强制停止： docker kill IMAGE_NAME
- 退出后从新进入容器： docker attach IMAGE_NAME

### 容器中部署静态网站案例
- 容器的端口映射
命令：run [-P] [-p]

-P，–publish-all=true | false，大写的P表示为容器暴露的所有端口进行映射；

-p，–publish=[]，小写的p表示为容器指定的端口进行映射，有四种形式：

containerPort：只指定容器的端口，宿主机端口随机映射；
hostPort:containerPort：同时指定容器与宿主机端口一一映射；
ip::containerPort：指定ip和容器的端口；
ip:hostPort:containerPort：指定ip、宿主机端口以及容器端口。

- 容器部署nginx服务
~~~
# 1. 创建映射80端口的交互式容器
docker run -p 80 --name web -i -t ubuntu /bin/bash
# 2. 更新源
apt-get update
# 3. 安装Nginx
apt-get install -y nginx
# 4. 安装Vim
apt-get install -y vim
~~~
创建静态页面：
~~~
mkdir -p /var/www/html
cd /var/www/html
vim index.html
~~~
修改Nginx配置文件:
~~~
# 查看Nginx安装位置
whereis nginx
# 修改配置文件
vim /etc/nginx/sites-enabled/default
~~~

运行nginx：
~~~
# 查看Nginx安装位置
whereis nginx
# 修改配置文件
vim /etc/nginx/sites-enabled/default
~~~

验证网站访问：
~~~
# 退出容器
Ctrl+P Ctrl+Q
# 查看容器进程
docker top web
# 查看容器端口映射情况
docker port web
~~~

### 启动已经停止的服务
- 启动容器 docker start IMAGE_NAME
- 进入容器 docker attach IMAGE_NAME
- 启动nginx：nginx
- 查看映射端口： docker port IMAGE_NAME


### linux 安装docker
- linux 3.10以上才能安装
~~~
查看系统版本指令
uname -r
~~~
- 更新yum包
~~~
yum update
~~~
- 安装需要的软件包，yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的
~~~
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
~~~
- 设置yum源
~~~
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
~~~
- 查看 仓库中所有docker版本
~~~
yum list docker-ce --showduplicates | sort -r
~~~
- 安装指定版本docker
~~~
sudo yum install docker-ce-17.12.0.ce --skip-broken
~~~
- 启动、设置开启开机启动
~~~
sudo systemctl start docker
sudo systemctl enable docker
~~~
- 验证是否成功
~~~
docker version
~~~
- 查看docker启动状态
~~~
systemctl status docker
~~~

### 卸载docker
- 查询docker安装过的包
~~~
yum list installed | grep docker
~~~
- 删除安装包
~~~
yum remove docker-ce.x86_64 ddocker-ce-cli.x86_64 -y
~~~
- 删除镜像/容器
~~~
rm -rf /var/lib/docker
~~~


### linux docker
- 获取安装脚本get-docker.sh
~~~
curl -fsSL https://get.docker.com -o get-docker.sh
~~~
- 启动docker
~~~
sudo systemctl start docker 
~~~

- 获取公共ip地址
~~~
#正常服务器获取本服务ip：
hostname -I
#无头服务获取本机ip
curl -s http://tnx.nl/ip
curl -s https://checkip.amazonaws.com
curl -s api.infoip.io/ip
curl -s ip.appspot.com
wget -O - -q https://icanhazip.com/
~~~

### 安装redis 及其使用
- 按redis
~~~
docker pull redis
~~~
- 查看镜像文件
~~~
docker images redis
~~~
- docker 启动redis
-p 6379:6379 : 将容器的6379端口映射到主机的6379端口
-v $PWD/data:/data : 将主机中当前目录下的data挂载到容器的/data
redis-server --appendonly yes : 在容器执行redis-server启动命令，并打开redis持久化配置
~~~
docker run -p 6379:6379 -v $PWD/data:/data -d redis redis-server --appendonly=true
~~~
- 查看容器启动情况
~~~
docker ps
~~~
- exec进入容器
docker exec -it 容器id  redis-cli
~~~
docker exec -it a826468cbd3e redis-cli
~~~

### linux安装container-selinux版本低问题问题1
~~~
yum 安装container-selinux 一般的yum源又找不到这个包
需要安装epel源 才能yum安装container-selinux
然后在安装docker-ce就可以了。
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum install epel-release   #阿里云上的epel源
然后yum install container-selinux
~~~
### docker安装问题2 
Public key for *.rpm is not installed解决方法
~~~
#本机是CentOS7
rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
~~~
