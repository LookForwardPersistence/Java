Dockerfile是一个脚本文本文件，镜像定制就是把每一层的修改、安装、构建操作的指令（每一条指令的内容是描述该层是如果构建的）都写入这个脚本文本文件。
### 构建自己的nginx镜像
1. 新建一个空白目录，创建Dockerfile文本文件
~~~
mkdir dawnnginx
cd dawnnginx
touch Dockerfile
vim Dockerfile
~~~
2. Dockerfile文本文件内容
首条指令需要大写如FROM RUN
~~~
FROM nginx
RUN echo '<h1> Wellcome to Docker,Down!</h1>'>/user/share/nginx/html/index.html
~~~
- FROM指定基础镜像
- FROM scratch 指定空白镜像
- RUN 执行命令
~~~
RUN ["可执行文件","参数1","参数2"]
~~~
3. 构建镜像
~~~
#最后有一个‘.’表示当前目录
docker build -t nginx:v1 .
~~~
