- 登录报权限问题
~~~
每个服务都需要修改 /ect/docker/daemon.json
~~~

- 镜像重命名
~~~
docker tag 镜像名：版本  私有仓库/项目名（harbor新建）/镜像名：版本
~~~
- 推送
~~~
docker push 私有仓库/镜像名：版本
~~~
