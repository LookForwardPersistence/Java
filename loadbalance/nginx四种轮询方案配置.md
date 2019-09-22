### 基于权重weight配置
~~~
#user  nobody;
worker_processes  1;
#pid        logs/nginx.pid;
events {
    #客户端线程轮询方法 linux2.6+使用epoll, 如果是*BSD 则使用kqueue
    use epoll;
    worker_connections  1024;
}
http {
#   基于权重轮询
    upstream dawn {
    server ip:8081 weight=2;
    server ip:8082 weight=10;
    server ip:8083 weight=5;
    }
    keepalive_timeout  65;
    #gzip  on;
    server {
        listen       80;
        server_name  www.dawn.com;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;
        location / {
            #root   html;
            index  index.html index.htm;
			#将请求转向自行服务列表dawn
            proxy_pass http://dawn;
			#将请求头发给后端服务器
            proxy_set_header Host $host;
			#后端的Web服务器可以通过X-Forwarded-For获取用户真实IP
            proxy_set_header X-Forwarded-For $remote_addr;
        }
    }
}

~~~

### 基于默认轮询方式
~~~

#user  nobody;
worker_processes  1;
#pid        logs/nginx.pid;
events {
    #客户端线程轮询方法 linux2.6+使用epoll, 如果是*BSD 则使用kqueue
    use epoll;
    worker_connections  1024;
}
http {
#   基于权重轮询
    upstream dawn {
    server ip:8081;
    server ip:8082;
    server ip:8083;
    }
    keepalive_timeout  65;
    #gzip  on;
    server {
        listen       80;
        server_name  www.dawn.com;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;
        location / {
            #root   html;
            index  index.html index.htm;
			#将请求转向自行服务列表dawn
            proxy_pass http://dawn;
			#将请求头发给后端服务器
            proxy_set_header Host $host;
			#后端的Web服务器可以通过X-Forwarded-For获取用户真实IP
            proxy_set_header X-Forwarded-For $remote_addr;
        }
    }
}

~~~

### 基于ip_hash (待验证)
~~~

#user  nobody;
worker_processes  1;
#pid        logs/nginx.pid;
events {
    #客户端线程轮询方法 linux2.6+使用epoll, 如果是*BSD 则使用kqueue
    use epoll;
    worker_connections  1024;
}
http {
#   基于权重轮询
    upstream dawn {
    ip_hash;
    server ip:8081;
    server ip:8082;
    server ip:8083;
    }
    keepalive_timeout  65;
    #gzip  on;
    server {
        listen       80;
        server_name  www.dawn.com;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;
        location / {
            #root   html;
            index  index.html index.htm;
			#将请求转向自行服务列表dawn
            proxy_pass http://dawn;
			#将请求头发给后端服务器
            proxy_set_header Host $host;
			#后端的Web服务器可以通过X-Forwarded-For获取用户真实IP
            proxy_set_header X-Forwarded-For $remote_addr;
        }
    }
}

~~~

### 基于至少连接 least_conn
~~~
#user  nobody;
worker_processes  1;
#pid        logs/nginx.pid;
events {
    #客户端线程轮询方法 linux2.6+使用epoll, 如果是*BSD 则使用kqueue
    use epoll;
    worker_connections  1024;
}
http {
#   基于权重轮询
    upstream dawn {
    least_conn;
    server ip:8081;
    server ip:8082;
    server ip:8083;
    }
    keepalive_timeout  65;
    #gzip  on;
    server {
        listen       80;
        server_name  www.dawn.com;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;
        location / {
            #root   html;
            index  index.html index.htm;
			#将请求转向自行服务列表dawn
            proxy_pass http://dawn;
			#将请求头发给后端服务器
            proxy_set_header Host $host;
			#后端的Web服务器可以通过X-Forwarded-For获取用户真实IP
            proxy_set_header X-Forwarded-For $remote_addr;
        }
    }
}
~~~
