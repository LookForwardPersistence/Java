### HashMap 源码解析
~~~
查看类的成员方法及属性
alt+7
~~~
- HashMap底层以什么方式存储？


### ConcurrentHasp
可以保证线程安全，cas sychrozied

### ConcurrentSkipListMap 跳表查找
使用场景：key 有序 线程安全

### volatile 
~~~
先写保证后序线程可以读到（可见性问题）
~~~
### synchrozied同步关键字
~~~
解决线程同步问题
~~~

### 锁降级
~~~
释放读锁，获得写锁（独占），获取读锁再释放写锁
~~~
