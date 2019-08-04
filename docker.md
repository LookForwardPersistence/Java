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
