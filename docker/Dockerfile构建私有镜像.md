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
### Dockerfile指令详解
- COPY复制指令
1. COPY <源路径><目标路径>
2. 构建上下文目录中<源路径>的文件目录复制到新一层镜像的镜像内的<目标路径>位置
~~~
COPY pk.json /user/src/app/
#多个源路径
COPY hom* /dir/
COPY hom?.txt /dir
~~~
- ADD高级的复制指令
~~~
除了COPY功能外，ADD新增一些功能，源路径可以是URL
~~~
- ENTRYPOINT 入口点
~~~
ENTRYPOINT与CMD功能一样，都是在指定程序指定容器及参数
~~~
- ENV设置环境变量
1. ENV <key><value>
2. ENV <key1>=<value1> <key2>=<value2>
~~~
ENV VERSION=1.0 DEBBUG=on NAME="Dawn Feet"
~~~  
- ARG构建参数
~~~
ARG <参数名>[=<默认值>]
~~~
  
