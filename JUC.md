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

### Java 线程池
~~~
Java通过Executors提供四种线程池，分别为：
newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
~~~
