#### redis 配置
- 开启后台守护进程
~~~
redis.conf 中的 daemeonize no 改为 daemenize yes
~~~
- 启动
~~~
src/redis-server redis.conf
~~~
- 链接到redis 控制台
~~~
./redis-cli
~~~
- 关闭运行的redis的端口
~~~
./redis-cli -p 6379 shutdown
~~~
