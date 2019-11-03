### docker提供公有仓库
- 注册
~~~
https://hub.docker.com
~~~
- 登录
~~~
docker login
~~~
- 拉取镜像
~~~
#查看镜像 
docker search 镜像名
docker pull 镜像名
~~~
- 推送镜像
~~~
#重新给镜像仓库命名
docker tag 容器Id 新名称
docker push 镜像（镜像格式：用户名/镜像名）
~~~
- 退出登录
~~~
docker logout
~~~
### 构建私有仓库
- 安装运行docker-registry
~~~
#默认情况下，仓库会被创建在容器的/var/lib/registry目录下，可以通过-v参数来镜像文件存放在本地指定路径
docker run --namge registry -d -p 5000:5000 -restart=always -v /usr/local/data/registry:/var/lib/registry regitstry
~~~
- 在私有仓库上传、搜索、下载镜像
~~~
# 使用docker tag 标记所需上传的镜像标记为127.0.0.1:5000/web:latest
docker tag web:latest 127.0.0.1:5000/web:latest
# 上传镜像
docker push 127.0.0.1:5000/web:latest
~~~
- 查看仓库中的镜像
~~~
curl 127.0.0.1:5000/v2/_catlog
~~~
- 取消docker默认不允许非https推送镜像（除本地外）的配置
~~~
# 在/etc/docker/daemon.json文件中添加
{
  "insecure-registries":[
        "ip:端口"
  ]
}
~~~
