#### redis 配置

- 运行远程访问设置
~~~
redis.conf 中的protected-mode yes 改为 protected-mode no
~~~
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


### redis 发布订阅
- 订阅
~~~
subscribe channel
~~~
- 发布
~~~
publish channel message
~~~
- 取消订阅
~~~
unsubscribe channel
~~~