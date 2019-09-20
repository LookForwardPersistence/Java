### nginx 安装
~~~
1、下载安装 openssl https://github.com/openssl/openssl/releases/tag/
    ./config
    make
    make install
2、环境准备 
    yum install pcre*
    yum install openssl*
    
3、下载nginx http://nginx.org/en/download.html 
   ./config --with-openssl = openssl解压目录
   make 
   make install
~~~
### nginx 操作
~~~
开启:sbin/nginx
     验证： curl读取web信息 curl -s http://localhost | grep nginx.com
关闭:sbin/nginx -s stop
重置：修改配置文件需要操作
sbin/nginx -s reload
~~~
### 域名指向配置
~~~
vi /etc/hosts
~~~
