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
