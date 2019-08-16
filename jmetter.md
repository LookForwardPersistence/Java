#### JMetter使用
- 添加线程组 Thread Group
- 在新增的线程组添加 simple->HTTP REQUEST
- 在线程程组添加http 头管理器 Config Element-> HTTP HEAD MANAGER
~~~
一般是post 带参数请求需要配置
Content-Type:application/json;charset=utf-8
~~~
- 需要监听监控元素在 linner中选
