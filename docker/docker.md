# Docker 指令
- 登录  dokcer login
- 查看运行的容器 docker ps
- 查看docker版本 docker verison
- docker 安装信息 docker info
- 测试docker 是否按照成功 docker run hello-world
~~~
成功会显示：
Hello from Docker!
This message shows that your installation appears to be working correctlyd
~~~
- 查看本机安装的镜像 docker image list
- 列出显示运行的容器信息  docker container ls 
- 显示全部容器信息 docker container ls --all
- 查看cotainer id docker container ls -aq


# 先删除容器再删除镜像
~~~~
查看镜像
docker ps -a
删除容器
docker rm containerID
查看镜像
docker images
删除镜像
docker rmi imageId
~~~~
### window 安装启动redis
- 下载redis
~~~
docker pull redis
~~~
- 启动redis
~~~
 docker run -d -p 6378:6378 --name myredis redis
 -d表示在后台运行，不阻塞命令行界面，让我们可继续输入其它命令，是detach单词缩写。
-p 表示端口号，左边的6379表示win10系统的端口（自已换其它的也随便），右边的则表表容器中redis端口。
--name表示运行redis镜像的一个实例名称。
~~~
