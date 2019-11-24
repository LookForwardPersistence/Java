### Docker swarm 集群搭建
- 创建集群命令获取加入集群token
~~~
docker swarm init --advertise-addr ip
~~~
- 加入其他节点
~~~
 docker swarm join --token SWMTKN-1-4yly22dbag174oalsleypus4ajko925abtgude53a1aaxfif77-7xse460pcx0gqny4ch4bnxafx ip:2377
~~~
- 获取token
~~~
docker swarm join-token manager
~~~
- 在master节点查看集群
~~~
docker node ls
~~~
- 升级其他节点作为master 节点
~~~
docker node promote 节点hostname
~~~
- swarm 运行服务
~~~
docker service create --replicas 3 -p 宿主端口:容器端口 --name 容器名称 容器:版本

eg：
docker service create --replicas 3 -p 80:80 --name nginx nginx:latest
~~~
- 当前swarm集群运行的服务
~~~
docker service ls
~~~
- 查看某个服务的详情
~~~
docker service ps 镜像名
~~~
- 查看某个服务的日志
~~~
docker service logs 镜像名
~~~
- swarm集群中移除服务
~~~
docker service rm 镜像名
~~~
- portainer 集群运行
~~~
#查看portainer镜像
docker search portainer
#拉取镜像
docker pull portainer/portainer
#安装
docker run -d -p 9000:9000 --name portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
~~~
### 安装问题
~~~
问题：Error response from daemon: --live-restore daemon configuration is incompatible with swarm mode
解决：
1、/etc/docker/daemon.json 中的“live-store”属性改为false
2、重启docker：sudo systemctl restart docker
~~~
