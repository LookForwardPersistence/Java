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
### 安装问题
~~~
问题：Error response from daemon: --live-restore daemon configuration is incompatible with swarm mode
解决：
1、/etc/docker/daemon.json 中的“live-store”属性改为false
2、重启docker：sudo systemctl restart docker
~~~
