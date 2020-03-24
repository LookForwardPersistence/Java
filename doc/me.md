https://iqtest.com/quiz/iq-test/
16pf http://www.apesk.com/16pf/#checkboxjuanbiao
~~~
mysql性能优化
1、用什么指令查看mysql进程
explain 执行计划 ;
2、mysql性能优化，设置三个组件，两两组件查询效果


多线程
volatile sycronized作用
多线程应用


springboot
新增注解特性
配置文件获取方式


分布式
redis分布锁实现


dubbo调用过程
协议 dubbo调用原理


MQ topic类型

短信防刷问题

httpcomones底层原理


什么是分布式

http协议是否有状态
http协议是无状态的是说http协议不记录历史的请求，就像指数函数一样，无记忆性。
无状态服务器是指一种把每个请求作为与之前任何请求都无关的独立的事务的服务器。


es

分布式锁实现

syhcronized 是否可以重入

mq消息 消费完后准回写确认，系统异常，下次消息发送就会出现冗余，如何解决冗余问题

如何避免幂等性问题

B树 B+树

doubbo运行原理

mysql误删除如何恢复

rocketmq如何避免重复消费

分库分表有哪些策略

项目时间修改

实际并发测试，并发数量修改

shardingspere查询遇到什么问题 简历上的每一句话都要认真仔细写

操作系统

数据库

算法

你的重点是什么？
直奔重点

mq选型依据
1、业务要求消息失败时候可以在重试，
RocketMq支持服务触发重传、且有丰富的web终命令便于运维、而kafka、activimq都不支持服务器触发重传
2、Rocketmq 是阿里大厂开源中间件，github上标星高，而且基于java开发符合我们现在的技术栈。


redssion实现原理
线程获取锁成功，执行lua脚本，写入数据库
线程去获取锁失败，通过whie循环尝试获取锁，获取成功执行lua脚本，保存数据到redis数据库
watch dog自动延机制：默认事件30秒，业务处理时间大于锁过期时间，给当前线程延长有效，直到业务执行完释放锁

redission 分布式锁缺陷
redis分布锁在哨兵模式下，如何master宕（dang）机，可能导致多个客户端同时完成加锁。
客户端1对某个master写入redisson锁，此时异步复制给slave，但这个过一但master宕机，主备切换slave节点变为master节点，此时客户端2尝试加锁，在新的master上也能上锁成功，导致各种脏数据产生。


服务假死排除
1、top命令
2、jmap -head pid 当前应用堆内存使用情况 重点关注head Usage
3、获取Head Dump （Jmap命令导出：jmap -dump:live,format=b,file=head.hprof pid 或者使用jvm参数获取dump：-xx:+HeapDumpOnOutOfMemaryError  ;-xx:HeadDumpBeforeFullGC 当JVM执行FullGC执行dump；-xx:+HeadDumpAfterFullGC 当jvm执行FullGC执行的dump）
以上参数设置后堆转存储将默认在jvm的“当前目录”生产，可结合一下命令显示重定向dump文件存储路径。-XX:HeadDumpPath=test.hprof
拿到dump文件后下载到本地分析。
通过MAT分析
Memmeory Analyzer Tool(MAT) java堆内存分析工具

自定义String jdk中的String 执行还是两者执行？


                    sentinel               hystrix
隔离策略      信号量隔离         线程池隔离 
 熔断降级策略   基于响应时间或失败比率      基于失败比率
 实时指标实现   滑动窗口             滑动窗口（基于 RxJava） 
规则配置    支持多种数据源       支持多种数据源
 扩展性   多个扩展点    插件的形式 
基于注解的支持    支持    支持 
限流       基于 QPS，支持基于调用关系的限流            不支持 
流量整形    支持慢启动、匀速器模式           不支持 
系统负载保护    支持    不支持 
控制台 开箱即用，可配置规则、查看秒级监控、机器发现等      不完善 
常见框架的适配 Servlet、Spring Cloud、Dubbo、gRPC 等               Servlet、Spring Cloud Netflix

sentinel原理 如何做资源隔离
sentinel中的基准测试也是通过jmh做的
原理：
1、sentinel主要基于7个不同的slot形成一个链表，每个slot各司其职，自己做完分内之事外，调用fireEntry通知下一个slot，直到某个slot中命中规则后抛出BlockException后而终止。
2、前三个slot（NodeSelectorslot[资源收集路径、以树状结构存储，根据路径来限流降级]/cluterBuilderslot【存储资源统计信息及调用者信息如资源的rt、thread、count、qps等】/staticslot【不同维度的runtime】）负责做统计，后面的slot（Flowslot【根据设计的限流规则及前面的slot统计状态来进行限流】/Authorizationslot[黑白名单控制]/Degradeslot【根据预设规则做熔断降级】/systemslot【根据系统的状态来控制总的入口流量】）负责将根据统计结果结合配置规则进行具体控制，是block请求还是放行。

数据库索引原理
innodb
myIsam

Aop原理 实现 应用场景
(1). AOP面向方面编程基于IoC，是对OOP的有益补充；
(2). AOP利用一种称为“横切”的技术，剖解开封装的对象内部，并将那些影响了 多个类的公共行为封装到一个可重用模块，并将其名为“Aspect”，即方面。所谓“方面”，简单地说，就是将那些与业务无关，却为业务模块所共同调用的 逻辑或责任封装起来，比如日志记录，便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可操作性和可维护性。
(3). AOP代表的是一个横向的关 系，将“对象”比作一个空心的圆柱体，其中封装的是对象的属性和行为；则面向方面编程的方法，就是将这个圆柱体以切面形式剖开，选择性的提供业务逻辑。而 剖开的切面，也就是所谓的“方面”了。然后它又以巧夺天功的妙手将这些剖开的切面复原，不留痕迹，但完成了效果。
(4). 实现AOP的技术，主要分为两大类：一是采用动态代理技术，利用截取消息的方式，对该消息进行装饰，以取代原有对象行为的执行；二是采用静态织入的方式，引入特定的语法创建“方面”，从而使得编译器可以在编译期间织入有关“方面”的代码。
(5). Spring实现AOP：JDK动态代理和CGLIB代理 JDK动态代理：其代理对象必须是某个接口的实现，它是通过在运行期间创建一个接口的实现类来完成对目标对象的代理；其核心的两个类是InvocationHandler和Proxy。 CGLIB代理：实现原理类似于JDK动态代理，只是它在运行期间生成的代理对象是针对目标类扩展的子类。CGLIB是高效的代码生成包，底层是依靠ASM（开源的java字节码编辑类库）操作字节码实现的，性能比JDK强；需要引入包asm.jar和cglib.jar。     使用AspectJ注入式切面和@AspectJ注解驱动的切面实际上底层也是通过动态代理实现的。
(6). AOP使用场景：                     
Authentication 权限检查        
Caching 缓存        
Context passing 内容传递        
Error handling 错误处理        
Lazy loading　延迟加载        
Debugging　　调试      
logging, tracing, profiling and monitoring　日志记录，跟踪，优化，校准        
Performance optimization　性能优化，效率检查        
Persistence　　持久化        
Resource pooling　资源池        
Synchronization　同步        
Transactions 事务管理    
另外Filter的实现和struts2的拦截器的实现都是AOP思想的体现。  

Ioc原理
  (1). IoC（Inversion of Control）是指容器控制程序对象之间的关系，而不是传统实现中，由程序代码直接操控。控制权由应用代码中转到了外部容器，控制权的转移是所谓反转。 对于Spring而言，就是由Spring来控制对象的生命周期和对象之间的关系；IoC还有另外一个名字——“依赖注入（Dependency Injection）”。从名字上理解，所谓依赖注入，即组件之间的依赖关系由容器在运行期决定，即由容器动态地将某种依赖关系注入到组件之中。  
(2). 在Spring的工作方式中，所有的类都会在spring容器中登记，告诉spring这是个什么东西，你需要什么东西，然后spring会在系统运行到适当的时候，把你要的东西主动给你，同时也把你交给其他需要你的东西。所有的类的创建、销毁都由 spring来控制，也就是说控制对象生存周期的不再是引用它的对象，而是spring。对于某个具体的对象而言，以前是它控制其他对象，现在是所有对象都被spring控制，所以这叫控制反转。
(3). 在系统运行中，动态的向某个对象提供它所需要的其他对象。  
(4). 依赖注入的思想是通过反射机制实现的，在实例化一个类时，它通过反射调用类中set方法将事先保存在HashMap中的类属性注入到类中。 总而言之，在传统的对象创建方式中，通常由调用者来创建被调用者的实例，而在Spring中创建被调用者的工作由Spring来完成，然后注入调用者，即所谓的依赖注入or控制反转。 注入方式有两种：依赖注入和设置注入； IoC的优点：降低了组件之间的耦合，降低了业务对象之间替换的复杂性，使之能够灵活的管理对象

设计模式及实现
AOP靠代理模式实现
单例模式（单例的代理对象）
工程模式
CGlib代理工厂（如果被代理的对象是实现了接口的话就使用jdk的接口代理模式否则使用CGLib提供的代理模式）

JdkDynamicAopProxy （jdk动态代理）源码
MethodInterceptor（环绕通知，这个方法中调用处理程序的invoke方法将会把调用处理委托方法拦截器）

spring代理原理
调用过程：ProxyFactoryBean调用AOPProxyFactory创建代理AOPProxy，AOPProxy现实对被代理对象的代理。
spring代理原理就是对JDK和CGLib进行封装和扩展，把他们的InvocationHandler调用处理程序委托给MethodBeforeAdvice（前通知）,ReturnAfterAdvice(后通知),MethodInterceptor(环绕通知)。


解析org.springframework.transaction.interceptor.TransactionProxyFactoryBean：
TransactionProxyFactoryBean是对事物进行控制，具体来说他就是将需要应用事物控制的类进行代理，然后当调用被代理对象的方法时实现方法拦截器的事物拦截器就会检查该类的方法是否符合事物属性的规则，如果符合那么事物拦截器就会调用事物管理器发起事物。

总结：事物是被事物拦截器所调用和控制的，事物管理器是被事物拦截器调用和控制的，代理就是一种来实现这种事物控制的方式和机制而已。这个AOP类似。

spring bean生命周期
Spring 容器可以管理 singleton 作用域 Bean 的生命周期，在此作用域下，Spring 能够精确地知道该 Bean 何时被创建，何时初始化完成，以及何时被销毁。
而对于 prototype 作用域的 Bean，Spring 只负责创建，当容器创建了 Bean 的实例后，Bean 的实例就交给客户端代码管理，Spring 容器将不再跟踪其生命周期。每次客户端请求 prototype 作用域的 Bean 时，Spring 容器都会创建一个新的实例，并且不会管那些被配置成 prototype 作用域的 Bean 的生命周期。

Bean 生命周期的整个执行过程描述如下。
1）根据配置情况调用 Bean 构造方法或工厂方法实例化 Bean。
2）利用依赖注入完成 Bean 中所有属性值的配置注入。
3）如果 Bean 实现了 BeanNameAware 接口，则 Spring 调用 Bean 的 setBeanName() 方法传入当前 Bean 的 id 值。
4）如果 Bean 实现了 BeanFactoryAware 接口，则 Spring 调用 setBeanFactory() 方法传入当前工厂实例的引用。
5）如果 Bean 实现了 ApplicationContextAware 接口，则 Spring 调用 setApplicationContext() 方法传入当前 ApplicationContext 实例的引用。
6）如果 BeanPostProcessor 和 Bean 关联，则 Spring 将调用该接口的预初始化方法 postProcessBeforeInitialzation() 对 Bean 进行加工操作，此处非常重要，Spring 的 AOP 就是利用它实现的。
7）如果 Bean 实现了 InitializingBean 接口，则 Spring 将调用 afterPropertiesSet() 方法。
8）如果在配置文件中通过 init-method 属性指定了初始化方法，则调用该初始化方法。
9）如果 BeanPostProcessor 和 Bean 关联，则 Spring 将调用该接口的初始化方法 postProcessAfterInitialization()。此时，Bean 已经可以被应用系统使用了。
10）如果在 <bean> 中指定了该 Bean 的作用范围为 scope="singleton"，则将该 Bean 放入 Spring IoC 的缓存池中，将触发 Spring 对该 Bean 的生命周期管理；如果在 <bean> 中指定了该 Bean 的作用范围为 scope="prototype"，则将该 Bean 交给调用者，调用者管理该 Bean 的生命周期，Spring 不再管理该 Bean。
11）如果 Bean 实现了 DisposableBean 接口，则 Spring 会调用 destory() 方法将 Spring 中的 Bean 销毁；如果在配置文件中通过 destory-method 属性指定了 Bean 的销毁方法，则 Spring 将调用该方法对 Bean 进行销毁。
Spring 为 Bean 提供了细致全面的生命周期过程，通过实现特定的接口或 <bean> 的属性设置，都可以对 Bean 的生命周期过程产生影响。虽然可以随意配置 <bean> 的属性，但是建议不要过多地使用 Bean 实现接口，因为这样会导致代码和 Spring 的聚合过于紧密。

singleton作用域、prototype作用域
sngleton作用域及时单例模式
prototype（原型）作用域可以理解为多例模式

mybatis原理	
1）读取 MyBatis 配置文件：mybatis-config.xml 为 MyBatis 的全局配置文件，配置了 MyBatis 的运行环境等信息，例如数据库连接信息。
2）加载映射文件。映射文件即 SQL 映射文件，该文件中配置了操作数据库的 SQL 语句，需要在 MyBatis 配置文件 mybatis-config.xml 中加载。mybatis-config.xml 文件可以加载多个映射文件，每个文件对应数据库中的一张表。
3）构造会话工厂：通过 MyBatis 的环境等配置信息构建会话工厂 SqlSessionFactory。


图 2  MyBatis 框架的执行流程图

4）创建会话对象：由会话工厂创建 SqlSession 对象，该对象中包含了执行 SQL 语句的所有方法。
5）Executor 执行器：MyBatis 底层定义了一个 Executor 接口来操作数据库，它将根据 SqlSession 传递的参数动态地生成需要执行的 SQL 语句，同时负责查询缓存的维护。
6）MappedStatement 对象：在 Executor 接口的执行方法中有一个 MappedStatement 类型的参数，该参数是对映射信息的封装，用于存储要映射的 SQL 语句的 id、参数等信息。
7）输入参数映射：输入参数类型可以是 Map、List 等集合类型，也可以是基本数据类型和 POJO 类型。输入参数映射过程类似于 JDBC 对 preparedStatement 对象设置参数的过程。
8）输出结果映射：输出结果类型可以是 Map、 List 等集合类型，也可以是基本数据类型和 POJO 类型。输出结果映射过程类似于 JDBC 对结果集的解析过程。

hibernate原理 
Hibernate六大核心接口，两个主要配置文件
1、Configuration接口:负责配置并启动Hibernate
2、SessionFactory接口:负责初始化Hibernate
3、Session接口:负责持久化对象的CRUD操作
4、Transaction接口:负责事务
5、Query接口和Criteria接口:负责执行各种数据库查询
注意：Configuration实例是一个启动期间的对象，一旦SessionFactory创建完成它就被丢弃了。
调用过程：
1.通过Configuration().configure();读取并解析hibernate.cfg.xml配置文件
2.由hibernate.cfg.xml中的<mappingresource="com/xx/User.hbm.xml"/>读取并解析映射信息
3.通过config.buildSessionFactory();//创建SessionFactory
4.sessionFactory.openSession();//打开Sesssion
5.session.beginTransaction();//创建事务Transation
6.persistent operate持久化操作 //一般指Save这个方法
7.session.getTransaction().commit();//提交事务
8.关闭Session
9.关闭SesstionFactory
mybatic hibernate优缺点，有什么区别
mybatics：
优点：
1、针对响应场景设计sql语句，mybatics的sql会更灵活、可控性更好、更优化
2、居于原生的jdbc，运行速度上优势
3、mybatics入门门槛低
4、mapper xml支持动态sql
缺点：
1、所有的sql语句都是依赖所有使用的数据库，所以不同的数据库类型支持不好（一致性差）
2、mybatic为半产品，自身功能有限（支持plugin）
hibernate:
优点：
1、更加对象化
2、移植性强
3、Hibernate是一个没有侵入性的框架，没有侵入性的框架我们称为轻量级框架。
4、Hibernate代码测试方便。
5、提高效率，提高生产力。
6、提供诸多功能及特性
* 制定合理的缓存策略；
* 尽量使用延迟加载特性；
* 采用合理的Session管理机制；
* 使用批量抓取，设定合理的批处理参数（batch_size）;
* 进行合理的O/R映射设计
缺点：
1、使用数据库特性的语句，将很难调优
2、对大批量数据更新存在问题
3、系统中存在大量的攻击查询功能
4、复杂查询性能低（多表查询性能差或难实现）
5、hibernate入门门槛高
6、封装了jdbc运行效率慢
7、不支持动态sql


 实际项目关于Hibernate和Mybatis的选型：
    1、数据量：有以下情况最好选用Mybatis
             如果有超过千万级别的表
             如果有单次业务大批量数据提交的需求（百万条及以上的），这个尤其不建议用Hibernat
             如果有单次业务大批量读取需求（百万条及以上的）(注，hibernate多表查询比较费劲，用不好很容易造成性能问题)
    2、表关联复杂度
            如果主要业务表的关联表超过20个（大概值），不建议使用hibernate
    3、人员
           如果开发成员多数不是多年使用hibernate的情况，建议使用mybatis
    4、数据库对于项目的重要程度
           如果项目要求对于数据库可控性好，可深度调优，用mybatis
redis 热点数据问题 20w万
解决办法：
1、新增二级缓存 如ecache、hashmap,发现热key就把它加载jvm中，针对热key直接从jvm取，不走redis层
2、备份热key
不让热key走同一服务，这个热key多个redis上存上一份，接下请求时候通过热key，与随机选取一台redis进行取值，返回数

如何监控热点key？
1、凭借业务经验识别、比如商品秒杀、开市
2、在客户端收集 统计并预警
3、同一入口，redis增加代理层
4、redis自带工具，monitor命令，缺点内存暴增，降低性能。redis4.0.3提供hotkey参数通过redis-cli -hotkey发现热key功能
5、自己抓包评估
redis客户端使用tcp协议与服务进行交互，通信协议采用resp，自己写协议监听端口，安装

springboot spring优缺点
spring 原理 

springmvc原理 

springboot原理

线程池原理
1、是否大于核心线程数？
2、是否大于工作队列数？
3、是否大于最大线程数？
其他执行拒绝策略
a、丢任务抛异常
b、丢任务不抛异常
c、将最早进入队列的任务删除，之后再加入队列
d、添加到线程池失败，那么主线程会自己去执行该任务
mq消息

分库分布有哪些策略、如何实现

jvm优化

redis存储方式如何实现
RDB:
（1）redis根据配置自己尝试去生成rdb快照文件
（2）fork一个子进程出来
（3）子进程尝试将数据dump到临时的rdb快照文件中
（4）完成rdb快照文件的生成之后，就替换之前的旧的快照文件
dump.rdb，每次生成一个新的快照，都会覆盖之前的老快照

AOF:
aof方式:由于快照方式是在一定间隔时间做一次的，所以如果redis意外down掉的话，就会丢失最后一次快照后的所有修改。aof比快照方式有更好的持久化性，是由于在使用aof时，redis会将每一个收到的写命令都通过write函数追加到文件中，当redis重启时会通过重新执行文件中保存的写命令来在内存中重建整个数据库的内容。      
当然由于os会在内核中缓存write做的修改，所以可能不是立即写到磁盘上。这样aof方式的持久化也还是有可能会丢失部分修改。可以通过配置文件告诉redis我们想要通过fsync函数强制os写入到磁盘的时机。
Appendfsync no/always/everysec
no:表示等操作系统进行数据缓存同步到磁盘。性能最好，持久化没有保障。
Always:表示每次更新操作后手动调用fsync()将数据写到磁盘.每次收到写命令就立即强制写入磁盘，最慢的，但是保障完全的持久化。
Everysec:表示每秒同步一次.每秒钟强制写入磁盘一次，在性能和持久化方面做了很好的折中。

RDB的问题
Redis有两种存储方式，默认是snapshot方式，实现方法是定时将内存的快照(snapshot)持久化到硬盘，这种方法缺点是持久化之后如果出现crash则会丢失一段数据。

AOF的问题
aof即append only mode，在写入内存数据的同时将操作命令保存到日志文件，在一个并发更改上万的系统中，命令日志是一个非常庞大的数据，管理维护成本非常高，恢复重建时间会非常长，这样导致失去aof高可用性本意。另外更重要的是Redis是一个内存数据结构模型，所有的优势都是建立在对内存复杂数据结构高效的原子操作上，这样就看出aof是一个非常不协调的部分。其实aof目的主要是数据可靠性及高可用性.
如果同时使用RDB和AOF两种持久化机制，那么在redis重启的时候，会使用AOF来重新构建数据，因为AOF中的数据更加完整

RDB和AOF到底该如何选择
（1）不要仅仅使用RDB，因为那样会导致你丢失很多数据
（2）也不要仅仅使用AOF，因为那样有两个问题，第一，你通过AOF做冷备，没有RDB做冷备 恢复的速度快; 第二，RDB每次简单粗暴生成数据快照，更加健壮，可以避免AOF这种复杂的备份和恢复机制的bug
（3）综合使用AOF和RDB两种持久化机制，用AOF来保证数据不丢失，作为数据恢复的第一选择; 用RDB来做不同程度的冷备，在AOF文件都丢失或损坏不可用的时候，还可以使用RDB来进行快速的数据恢复
另外，要注意的是：如果我们想要redis仅仅作为纯内存的缓存来用，那么可以禁止RDB和AOF所有的持久化机制

redis单线程如何架构满足高可用
netty selector 线程模型 

oracle与mysql 区别
(1) 对事务的提交
    MySQL默认是自动提交，而Oracle默认不自动提交，需要用户手动提交，需要在写commit;指令或者点击commit按钮
(2) 分页查询
    MySQL是直接在SQL语句中写"select... from ...where...limit  x, y",有limit就可以实现分页;而Oracle则是需要用到伪列ROWNUM和嵌套查询
(3) 事务隔离级别
      MySQL是read commited的隔离级别，而Oracle是repeatable read的隔离级别，同时二者都支持serializable串行化事务隔离级别，可以实现最高级别的
    读一致性。每个session提交后其他session才能看到提交的更改。Oracle通过在undo表空间中构造多版本数据块来实现读一致性，每个session
    查询时，如果对应的数据块发生变化，Oracle会在undo表空间中为这个session构造它查询时的旧的数据块
    MySQL没有类似Oracle的构造多版本数据块的机制，只支持read commited的隔离级别。一个session读取数据时，其他session不能更改数据，但
    可以在表最后插入数据。session更新数据时，要加上排它锁，其他session无法访问数据
(4) 对事务的支持
    MySQL在innodb存储引擎的行级锁的情况下才可支持事务，而Oracle则完全支持事务
(5) 保存数据的持久性
    MySQL是在数据库更新或者重启，则会丢失数据，Oracle把提交的sql操作线写入了在线联机日志文件中，保持到了磁盘上，可以随时恢复
(6) 并发性
    MySQL以表级锁为主，对资源锁定的粒度很大，如果一个session对一个表加锁时间过长，会让其他session无法更新此表中的数据。
  虽然InnoDB引擎的表可以用行级锁，但这个行级锁的机制依赖于表的索引，如果表没有索引，或者sql语句没有使用索引，那么仍然使用表级锁。
  Oracle使用行级锁，对资源锁定的粒度要小很多，只是锁定sql需要的资源，并且加锁是在数据库中的数据行上，不依赖与索引。所以Oracle对并
  发性的支持要好很多。
(7) 逻辑备份
    MySQL逻辑备份时要锁定数据，才能保证备份的数据是一致的，影响业务正常的dml使用,Oracle逻辑备份时不锁定数据，且备份的数据是一致
(8) 复制
    MySQL:复制服务器配置简单，但主库出问题时，丛库有可能丢失一定的数据。且需要手工切换丛库到主库。
    Oracle:既有推或拉式的传统数据复制，也有dataguard的双机或多机容灾机制，主库出现问题是，可以自动切换备库到主库，但配置管理较复杂。
(9) 性能诊断
    MySQL的诊断调优方法较少，主要有慢查询日志。
    Oracle有各种成熟的性能诊断调优工具，能实现很多自动分析、诊断功能。比如awr、addm、sqltrace、tkproof等    
(10)权限与安全
    MySQL的用户与主机有关，感觉没有什么意义，另外更容易被仿冒主机及ip有可乘之机。
    Oracle的权限与安全概念比较传统，中规中矩。
(11)分区表和分区索引
    MySQL的分区表还不太成熟稳定。
    Oracle的分区表和分区索引功能很成熟，可以提高用户访问db的体验。
(12)管理工具
    MySQL管理工具较少，在linux下的管理工具的安装有时要安装额外的包（phpmyadmin， etc)，有一定复杂性。
    Oracle有多种成熟的命令行、图形界面、web管理工具，还有很多第三方的管理工具，管理极其方便高效。
(13)最重要的区别
    MySQL是轻量型数据库，并且免费，没有服务恢复数据。
    Oracle是重量型数据库，收费，Oracle公司对Oracle数据库有任何服务
    
    
cpu负载及使用率理解
 cpu负载就是cpu在一段时间内正在处理及等待cpu处理的进程数之和的统计信息，也是cpu使用队列的长度统计信息（超过cpu核数*0.7就是不正常）
 cpu使用率：程序在运行期间实时占用的cpu百分比，cpu使用率反应当前cpu繁忙程度，忽高忽低原因在于占用cpu处理的线程可能处于io等待状态但且未释放进入wait
cpu使用率低负载高原因：等待io完成的进程多，导致进程队列过大，但cpu运行的进程却很少，这就提现了到负载过大了，cpu使用率

load average 3个值 分别记录 1min 5min 15min内的系统平均负载 
负载高但cpu使用率低可能原因是频繁上下文切换导致耗费大量的cpu时间，以至于用在运算cpu时间片较少，却有很多进程在等待进行。
cpu上下文频繁切换原因：
1、对应抢占式操作系统而言，当前任务时间片用完之后，系统cpu正常调度下一个任务；
2、当前任务碰到io阻塞，调用线程将挂起此任务，继续下一任务；
3、多个任务抢占锁资源，当前任务没有抢到，被调度器挂起，继续下移任务；
4、用户代码挂起当前任务，让出cpu时间；
5、硬件中断。
https://blog.csdn.net/mimi_csdn/article/details/79375471

分库分表跨表查询
sql解析->执行器（查询）优化->sql路由->sql改写->sql执行->结果归并

继承、超类执行顺序

系统设计

分布式事务
项目管理工具

0、领域驱动设计DDD
      领域服务查询操作、命令操作聚合根（实体1、实体2）
      DDD：是一种软件开发方法
      目标：可测试、可伸缩、组织良好的的软件模型，通过DDD同时提供战略上和战术上的建模工具来帮组我们设计高质量的软件模型
      参与者：用户、业务人员、开发、测试
      领域专家：精通业务的任何人
      领域模型：通过对象模型实现，对象模型包含了数据和行为，并且表达了精准的业务含义
      为什么要使用DDD：
         a、使领域专家和开发者一起工作，这样开发出来的软件能够精准的传达业务规则
         b、精准传达业务规则就是说，此时的软件就像领域专家是编码人员时所开发出来一样
         c、可以帮助业务人员自我提高，提高业务的掌握能力
         e、关键在于对只是的集中、以为这样可以确保软件知识并不是掌握在少数人手中
         f、在领域专家、开发者和软件本身之前不存在“翻译”
          g、设计就是代码、代码就是设计
          h、DDD同时提供了战略设计和战术设计两种方式
        
Dubbo工作原理
       流程：provider向注册中心注册、consumer从注册中心订阅服务，注册中心会通知consumer注册好的服务、consumer通过RPC调用provider、consumer和provider异步通知监控中心
       原理：a、service接口层给服务提供者实现、消费者调用，
                  b、config配置层对dubbo进行各种配置，
                  c、proxy服务代理层，consumer、provider有自己代理的服务，代理间进行网络通信
                  e、registry服务注册层，负责服务的注册与发现
                  f、cluster集群层，封装多个服务提供者的路由及负载均衡，将多个实例组成一个服务
                  g、monitor监控层，对rpc接口调用次数及调用时间进行监控
                  h、protocol远程调用层，封装rpc调动
                   i、exchange信息交流层，封装请求响应模式，同步转异步
                   j、transport网络传输出层，抽象，mina和netty为同一接口
                   k、serices数据序列化层
          RPC：远程过程调用，rpc是采用client-service架构，通过request-respone消息模式实现
          rpc流程：
               a、客户端处理流程中调用Client Stub（就像调用本地方法一样），传递参数
                b、Client Stub将参数编组为消息，然后通过系统系统调用向服务端发送消息
                c、客户端本地操作系统将消息从客户端机器发送到服务机器
                e、服务操作系统将接收到的数据包传递给server Stub
                f、Server Stub解组消息为参数
                g、Server Stub 再调用用服务端的过程，过程执行结果以反方向的相同步骤响应给客户端
         rpc协议：rpc调用过程采用的协议称为rpc协议（xml-rpc/soap-rpc/json-rpc等）。消息由哪些部分构成及消息的表示形式构成消息协议。rpc协议规定的请求、响应消息格式在TCP（传输控制协议）上选用或自定义协议来完成消息交互。（可以选用通用的标准协议如http、https，也可以根据需要定义自己的消息协议）
       rpc框架：封装好参数编组、消息解组、底层网络通信的rpc程序开发框架，带来的便捷是可以直接在其基础上只专注过程代码编写。
        传统webservice：cxf、axis、java自带的ax-ws等，大多数基于标准的soap协议
         新兴微服务架构：Dubbo、Spring Cloud Apache Thrift等等
       为什么要用rpc框架：服务化、可重用、系统间调用

可见性问题：
1、所见非所得
2、无法肉眼检测程序的准确性
3、不同的运行平台有不同的表现
4、错误很难重现
                
volatile(解决可见性问题)
1、禁止缓存
2、对volatile变量相关的指令不做重排
final：在对象的函数构造中设置对象字段，当线程看到该对象时，将始终看到该对象的final字段的正确构造版本。
Word Tearing 字节处理
有些处理器没有提供些单个字节的功能，这样的处理器上更新byte数组，若只是简单的读取整个内容，更新对应的字节，让后再将整个内容回写到内存，将是不合法的。这个问题有时候称“字节分裂”
注意：经量不要对bye[]中的元素进行重新赋值，更不要在多线程程序中这样做
dubbo/long 每次读取只读32位，需要加上volatile才能保证原子操作
java内存模型：java语言多线程的规则、cpu缓存、编译器指令重排
原子操作

CAS:(Compare san swap)：属于硬件源语,是原子操作
第一个参数跟内存的旧值比较，相同才替换旧值。如果失败重试（自旋）
J.U.C

ABA问题

出现线程安全的原因：1、可见性问题  2、原子性问题（数据一致性）	

Java锁：自旋锁、乐观锁、悲观锁；独享锁（写）、共享锁（读）；可重入、不可重入锁

Synchronized:（可重入、独享锁、悲观锁）
1、用于实例方法、静态方法时，隐式指定锁对象
2、用于代码块时，显示指定锁对象
3、锁的作用域：对象锁、类锁、分布式锁
原理：状态存放在对象头mark Word中（5中状态：未锁定、偏向锁、轻量级锁、重量级锁），用cas操作修改mark world内存区
优点：
1、使用简单、语义清晰
2、jvm提供多种方案（锁粗化、锁消除、偏向锁、轻量级锁）
3、锁的释放有jvm来完成，不用人工干预，降低了死锁的可能性
缺点：无法实现一些高级功能如公平锁、中断锁、超时锁、读写锁、共享锁等
RentantLock:
await队列、owner、count（cas抢锁修改count）
ReadWriteLock(waiters/read/write/count)
原理复述：
使用场景：读取操作多于写入操作，改进互斥锁的性能，比如：集合的并发线程安全改造、缓存组件
HashMap:线程不安全
HastTable：线程安全（不推荐原因：用了Synchronized关键字修饰）
ConcunrrentHashMap:并发线程安全
锁降级：获取写锁再获取读锁再释放写锁
HashMap1.7:数组+链表 非原子操作，线程不安全
HashMap1.8:
ConcurrentHashMap1.7: segment+hashTable，分段锁，并发级别决绝segment 
ConcurrentHashMap1.8:cas+sychronized 数组+链表（红黑树） 锁住粒度小，并发级别高
ConcurrentSkipListMap:
JVM

百万连接

resful api 参数放在url路径中的参数
@PathVariable

nginx配置https
a、证书生成
b、配置444接口
c、http重定向https
d、检查配置文件 /application/nginx/sbin/nginx -t
e、加载配置文件 /application/nginx/sbin/nginx -s reload


docker查看日志
$ docker logs [OPTIONS] CONTAINER
  Options:
        --details        显示更多的信息
    -f, --follow         跟踪实时日志
        --since string   显示自某个timestamp之后的日志，或相对时间，如42m（即42分钟）
        --tail string    从日志末尾显示多少行日志， 默认是all
    -t, --timestamps     显示时间戳
        --until string   显示自某个timestamp之前的日志，或相对时间，如42m（即42分钟）
  eg:查看指定时间后的日志，只显示最后100行：
  $ docker logs -f -t --since="2018-02-08" --tail=100 CONTAINER_ID      

主线程停止及通知，如何监听


设计模式

数据库乐观锁实现
数据库加一个版本号字段，更新时候比较 相同则更新否则拒绝

jpa居于什么Hibernate 还是Mybatics

url带参数 
注解：@PathVaribale
1.介绍几种如何处理url中的参数的注解
@PathVaribale 获取url中的数据
@RequestParam 获取请求参数的值
@GetMapping 组合注解，是 @RequestMapping(method = RequestMethod.GET) 的缩写

分布式事务
   高并发场景：两阶段提交+最终一致性
    tcc编程模式：是两阶段提交一种变种，tcc提供一种编程框架，整个业务逻辑分三块：try、 confirm、cancel；try阶段去扣库存、confirm阶段去更新订单状态，如更新订单失败进入Cancel阶段，去回复库存。tcc就是就是通过代码认为实现两阶段提交，不同的业务场景写的代码不一样.


1、你对什么技术熟悉：dubbo、springcoud、redis、mq、

2、抽象类用过吗？具体举例子？

关键点必须掌握： 分布式锁（redis、zookeeper） dubbo运行原理 分库分表 mysql
评价：基础原理性掌握不足


mysql性能优化
1、用什么指令查看mysql进程
explain 执行计划 ;
2、mysql性能优化，设置三个组件，两两组件查询效果


多线程
volatile sycronized作用
多线程应用


springboot
新增注解特性
配置文件获取方式


分布式
redis分布锁实现


dubbo调用过程
协议 dubbo调用原理


MQ topic类型

短信防刷问题

httpcomones底层原理

什么是分布式

http协议是否有状态
http协议是无状态的是说http协议不记录历史的请求，就像指数函数一样，无记忆性。
无状态服务器是指一种把每个请求作为与之前任何请求都无关的独立的事务的服务器。


es

分布式锁实现

syhcronized 是否可以重入

mq消息 消费完后准回写确认，系统异常，下次消息发送就会出现冗余，如何解决冗余问题

如何避免幂等性问题

B树 B+树

doubbo运行原理

mysql误删除如何恢复

rocketmq如何避免重复消费

分库分表有哪些策略

项目时间修改

实际并发测试，并发数量修改

shardingspere查询遇到什么问题 简历上的每一句话都要认真仔细写

操作系统

数据库

算法

你的重点是什么？
直奔重点

mq选型依据
1、业务要求消息失败时候可以在重试，
RocketMq支持服务触发重传、且有丰富的web终命令便于运维、而kafka、activimq都不支持服务器触发重传
2、Rocketmq 是阿里大厂开源中间件，github上标星高，而且基于java开发符合我们现在的技术栈。


redssion实现原理
线程获取锁成功，执行lua脚本，写入数据库
线程去获取锁失败，通过whie循环尝试获取锁，获取成功执行lua脚本，保存数据到redis数据库
watch dog自动延机制：默认事件30秒，业务处理时间大于锁过期时间，给当前线程延长有效，直到业务执行完释放锁

redission 分布式锁缺陷
redis分布锁在哨兵模式下，如何master宕（dang）机，可能导致多个客户端同时完成加锁。
客户端1对某个master写入redisson锁，此时异步复制给slave，但这个过一但master宕机，主备切换slave节点变为master节点，此时客户端2尝试加锁，在新的master上也能上锁成功，导致各种脏数据产生。


服务假死排除
1、top命令
2、jmap -head pid 当前应用堆内存使用情况 重点关注head Usage
3、获取Head Dump （Jmap命令导出：jmap -dump:live,format=b,file=head.hprof pid 或者使用jvm参数获取dump：-xx:+HeapDumpOnOutOfMemaryError  ;-xx:HeadDumpBeforeFullGC 当JVM执行FullGC执行dump；-xx:+HeadDumpAfterFullGC 当jvm执行FullGC执行的dump）
以上参数设置后堆转存储将默认在jvm的“当前目录”生产，可结合一下命令显示重定向dump文件存储路径。-XX:HeadDumpPath=test.hprof
拿到dump文件后下载到本地分析。
通过MAT分析
Memmeory Analyzer Tool(MAT) java堆内存分析工具

自定义String jdk中的String 执行还是两者执行？


                    sentinel               hystrix
隔离策略      信号量隔离         线程池隔离 
 熔断降级策略   基于响应时间或失败比率      基于失败比率
 实时指标实现   滑动窗口             滑动窗口（基于 RxJava） 
规则配置    支持多种数据源       支持多种数据源
 扩展性   多个扩展点    插件的形式 
基于注解的支持    支持    支持 
限流       基于 QPS，支持基于调用关系的限流            不支持 
流量整形    支持慢启动、匀速器模式           不支持 
系统负载保护    支持    不支持 
控制台 开箱即用，可配置规则、查看秒级监控、机器发现等      不完善 
常见框架的适配 Servlet、Spring Cloud、Dubbo、gRPC 等               Servlet、Spring Cloud Netflix

sentinel原理 如何做资源隔离
sentinel中的基准测试也是通过jmh做的
原理：
1、sentinel主要基于7个不同的slot形成一个链表，每个slot各司其职，自己做完分内之事外，调用fireEntry通知下一个slot，直到某个slot中命中规则后抛出BlockException后而终止。
2、前三个slot（NodeSelectorslot[资源收集路径、以树状结构存储，根据路径来限流降级]/cluterBuilderslot【存储资源统计信息及调用者信息如资源的rt、thread、count、qps等】/staticslot【不同维度的runtime】）负责做统计，后面的slot（Flowslot【根据设计的限流规则及前面的slot统计状态来进行限流】/Authorizationslot[黑白名单控制]/Degradeslot【根据预设规则做熔断降级】/systemslot【根据系统的状态来控制总的入口流量】）负责将根据统计结果结合配置规则进行具体控制，是block请求还是放行。

数据库索引原理

Aop原理 实现 应用场景
(1). AOP面向方面编程基于IoC，是对OOP的有益补充；
(2). AOP利用一种称为“横切”的技术，剖解开封装的对象内部，并将那些影响了 多个类的公共行为封装到一个可重用模块，并将其名为“Aspect”，即方面。所谓“方面”，简单地说，就是将那些与业务无关，却为业务模块所共同调用的 逻辑或责任封装起来，比如日志记录，便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可操作性和可维护性。
(3). AOP代表的是一个横向的关 系，将“对象”比作一个空心的圆柱体，其中封装的是对象的属性和行为；则面向方面编程的方法，就是将这个圆柱体以切面形式剖开，选择性的提供业务逻辑。而 剖开的切面，也就是所谓的“方面”了。然后它又以巧夺天功的妙手将这些剖开的切面复原，不留痕迹，但完成了效果。
(4). 实现AOP的技术，主要分为两大类：一是采用动态代理技术，利用截取消息的方式，对该消息进行装饰，以取代原有对象行为的执行；二是采用静态织入的方式，引入特定的语法创建“方面”，从而使得编译器可以在编译期间织入有关“方面”的代码。
(5). Spring实现AOP：JDK动态代理和CGLIB代理 JDK动态代理：其代理对象必须是某个接口的实现，它是通过在运行期间创建一个接口的实现类来完成对目标对象的代理；其核心的两个类是InvocationHandler和Proxy。 CGLIB代理：实现原理类似于JDK动态代理，只是它在运行期间生成的代理对象是针对目标类扩展的子类。CGLIB是高效的代码生成包，底层是依靠ASM（开源的java字节码编辑类库）操作字节码实现的，性能比JDK强；需要引入包asm.jar和cglib.jar。     使用AspectJ注入式切面和@AspectJ注解驱动的切面实际上底层也是通过动态代理实现的。
(6). AOP使用场景：                     
Authentication 权限检查        
Caching 缓存        
Context passing 内容传递        
Error handling 错误处理        
Lazy loading　延迟加载        
Debugging　　调试      
logging, tracing, profiling and monitoring　日志记录，跟踪，优化，校准        
Performance optimization　性能优化，效率检查        
Persistence　　持久化        
Resource pooling　资源池        
Synchronization　同步        
Transactions 事务管理    
另外Filter的实现和struts2的拦截器的实现都是AOP思想的体现。  

Ioc原理
  (1). IoC（Inversion of Control）是指容器控制程序对象之间的关系，而不是传统实现中，由程序代码直接操控。控制权由应用代码中转到了外部容器，控制权的转移是所谓反转。 对于Spring而言，就是由Spring来控制对象的生命周期和对象之间的关系；IoC还有另外一个名字——“依赖注入（Dependency Injection）”。从名字上理解，所谓依赖注入，即组件之间的依赖关系由容器在运行期决定，即由容器动态地将某种依赖关系注入到组件之中。  
(2). 在Spring的工作方式中，所有的类都会在spring容器中登记，告诉spring这是个什么东西，你需要什么东西，然后spring会在系统运行到适当的时候，把你要的东西主动给你，同时也把你交给其他需要你的东西。所有的类的创建、销毁都由 spring来控制，也就是说控制对象生存周期的不再是引用它的对象，而是spring。对于某个具体的对象而言，以前是它控制其他对象，现在是所有对象都被spring控制，所以这叫控制反转。
(3). 在系统运行中，动态的向某个对象提供它所需要的其他对象。  
(4). 依赖注入的思想是通过反射机制实现的，在实例化一个类时，它通过反射调用类中set方法将事先保存在HashMap中的类属性注入到类中。 总而言之，在传统的对象创建方式中，通常由调用者来创建被调用者的实例，而在Spring中创建被调用者的工作由Spring来完成，然后注入调用者，即所谓的依赖注入or控制反转。 注入方式有两种：依赖注入和设置注入； IoC的优点：降低了组件之间的耦合，降低了业务对象之间替换的复杂性，使之能够灵活的管理对象

设计模式及实现
AOP靠代理模式实现
单例模式（单例的代理对象）
工程模式
CGlib代理工厂（如果被代理的对象是实现了接口的话就使用jdk的接口代理模式否则使用CGLib提供的代理模式）

JdkDynamicAopProxy （jdk动态代理）源码
MethodInterceptor（环绕通知，这个方法中调用处理程序的invoke方法将会把调用处理委托方法拦截器）

spring代理原理
调用过程：ProxyFactoryBean调用AOPProxyFactory创建代理AOPProxy，AOPProxy现实对被代理对象的代理。
spring代理原理就是对JDK和CGLib进行封装和扩展，把他们的InvocationHandler调用处理程序委托给MethodBeforeAdvice（前通知）,ReturnAfterAdvice(后通知),MethodInterceptor(环绕通知)。


解析org.springframework.transaction.interceptor.TransactionProxyFactoryBean：
TransactionProxyFactoryBean是对事物进行控制，具体来说他就是将需要应用事物控制的类进行代理，然后当调用被代理对象的方法时实现方法拦截器的事物拦截器就会检查该类的方法是否符合事物属性的规则，如果符合那么事物拦截器就会调用事物管理器发起事物。

总结：事物是被事物拦截器所调用和控制的，事物管理器是被事物拦截器调用和控制的，代理就是一种来实现这种事物控制的方式和机制而已。这个AOP类似。

spring bean生命周期
Spring 容器可以管理 singleton 作用域 Bean 的生命周期，在此作用域下，Spring 能够精确地知道该 Bean 何时被创建，何时初始化完成，以及何时被销毁。
而对于 prototype 作用域的 Bean，Spring 只负责创建，当容器创建了 Bean 的实例后，Bean 的实例就交给客户端代码管理，Spring 容器将不再跟踪其生命周期。每次客户端请求 prototype 作用域的 Bean 时，Spring 容器都会创建一个新的实例，并且不会管那些被配置成 prototype 作用域的 Bean 的生命周期。

Bean 生命周期的整个执行过程描述如下。
1）根据配置情况调用 Bean 构造方法或工厂方法实例化 Bean。
2）利用依赖注入完成 Bean 中所有属性值的配置注入。
3）如果 Bean 实现了 BeanNameAware 接口，则 Spring 调用 Bean 的 setBeanName() 方法传入当前 Bean 的 id 值。
4）如果 Bean 实现了 BeanFactoryAware 接口，则 Spring 调用 setBeanFactory() 方法传入当前工厂实例的引用。
5）如果 Bean 实现了 ApplicationContextAware 接口，则 Spring 调用 setApplicationContext() 方法传入当前 ApplicationContext 实例的引用。
6）如果 BeanPostProcessor 和 Bean 关联，则 Spring 将调用该接口的预初始化方法 postProcessBeforeInitialzation() 对 Bean 进行加工操作，此处非常重要，Spring 的 AOP 就是利用它实现的。
7）如果 Bean 实现了 InitializingBean 接口，则 Spring 将调用 afterPropertiesSet() 方法。
8）如果在配置文件中通过 init-method 属性指定了初始化方法，则调用该初始化方法。
9）如果 BeanPostProcessor 和 Bean 关联，则 Spring 将调用该接口的初始化方法 postProcessAfterInitialization()。此时，Bean 已经可以被应用系统使用了。
10）如果在 <bean> 中指定了该 Bean 的作用范围为 scope="singleton"，则将该 Bean 放入 Spring IoC 的缓存池中，将触发 Spring 对该 Bean 的生命周期管理；如果在 <bean> 中指定了该 Bean 的作用范围为 scope="prototype"，则将该 Bean 交给调用者，调用者管理该 Bean 的生命周期，Spring 不再管理该 Bean。
11）如果 Bean 实现了 DisposableBean 接口，则 Spring 会调用 destory() 方法将 Spring 中的 Bean 销毁；如果在配置文件中通过 destory-method 属性指定了 Bean 的销毁方法，则 Spring 将调用该方法对 Bean 进行销毁。
Spring 为 Bean 提供了细致全面的生命周期过程，通过实现特定的接口或 <bean> 的属性设置，都可以对 Bean 的生命周期过程产生影响。虽然可以随意配置 <bean> 的属性，但是建议不要过多地使用 Bean 实现接口，因为这样会导致代码和 Spring 的聚合过于紧密。

singleton作用域、prototype作用域
sngleton作用域及时单例模式
prototype（原型）作用域可以理解为多例模式

mybatis原理	
1）读取 MyBatis 配置文件：mybatis-config.xml 为 MyBatis 的全局配置文件，配置了 MyBatis 的运行环境等信息，例如数据库连接信息。
2）加载映射文件。映射文件即 SQL 映射文件，该文件中配置了操作数据库的 SQL 语句，需要在 MyBatis 配置文件 mybatis-config.xml 中加载。mybatis-config.xml 文件可以加载多个映射文件，每个文件对应数据库中的一张表。
3）构造会话工厂：通过 MyBatis 的环境等配置信息构建会话工厂 SqlSessionFactory。


图 2  MyBatis 框架的执行流程图

4）创建会话对象：由会话工厂创建 SqlSession 对象，该对象中包含了执行 SQL 语句的所有方法。
5）Executor 执行器：MyBatis 底层定义了一个 Executor 接口来操作数据库，它将根据 SqlSession 传递的参数动态地生成需要执行的 SQL 语句，同时负责查询缓存的维护。
6）MappedStatement 对象：在 Executor 接口的执行方法中有一个 MappedStatement 类型的参数，该参数是对映射信息的封装，用于存储要映射的 SQL 语句的 id、参数等信息。
7）输入参数映射：输入参数类型可以是 Map、List 等集合类型，也可以是基本数据类型和 POJO 类型。输入参数映射过程类似于 JDBC 对 preparedStatement 对象设置参数的过程。
8）输出结果映射：输出结果类型可以是 Map、 List 等集合类型，也可以是基本数据类型和 POJO 类型。输出结果映射过程类似于 JDBC 对结果集的解析过程。

hibernate原理 
Hibernate六大核心接口，两个主要配置文件
1、Configuration接口:负责配置并启动Hibernate
2、SessionFactory接口:负责初始化Hibernate
3、Session接口:负责持久化对象的CRUD操作
4、Transaction接口:负责事务
5、Query接口和Criteria接口:负责执行各种数据库查询
注意：Configuration实例是一个启动期间的对象，一旦SessionFactory创建完成它就被丢弃了。
调用过程：
1.通过Configuration().configure();读取并解析hibernate.cfg.xml配置文件
2.由hibernate.cfg.xml中的<mappingresource="com/xx/User.hbm.xml"/>读取并解析映射信息
3.通过config.buildSessionFactory();//创建SessionFactory
4.sessionFactory.openSession();//打开Sesssion
5.session.beginTransaction();//创建事务Transation
6.persistent operate持久化操作 //一般指Save这个方法
7.session.getTransaction().commit();//提交事务
8.关闭Session
9.关闭SesstionFactory
mybatic hibernate优缺点，有什么区别
mybatics：
优点：
1、针对响应场景设计sql语句，mybatics的sql会更灵活、可控性更好、更优化
2、居于原生的jdbc，运行速度上优势
3、mybatics入门门槛低
4、mapper xml支持动态sql
缺点：
1、所有的sql语句都是依赖所有使用的数据库，所以不同的数据库类型支持不好（一致性差）
2、mybatic为半产品，自身功能有限（支持plugin）
hibernate:
优点：
1、更加对象化
2、移植性强
3、Hibernate是一个没有侵入性的框架，没有侵入性的框架我们称为轻量级框架。
4、Hibernate代码测试方便。
5、提高效率，提高生产力。
6、提供诸多功能及特性
* 制定合理的缓存策略；
* 尽量使用延迟加载特性；
* 采用合理的Session管理机制；
* 使用批量抓取，设定合理的批处理参数（batch_size）;
* 进行合理的O/R映射设计
缺点：
1、使用数据库特性的语句，将很难调优
2、对大批量数据更新存在问题
3、系统中存在大量的攻击查询功能
4、复杂查询性能低（多表查询性能差或难实现）
5、hibernate入门门槛高
6、封装了jdbc运行效率慢
7、不支持动态sql


 实际项目关于Hibernate和Mybatis的选型：
    1、数据量：有以下情况最好选用Mybatis
             如果有超过千万级别的表
             如果有单次业务大批量数据提交的需求（百万条及以上的），这个尤其不建议用Hibernat
             如果有单次业务大批量读取需求（百万条及以上的）(注，hibernate多表查询比较费劲，用不好很容易造成性能问题)
    2、表关联复杂度
            如果主要业务表的关联表超过20个（大概值），不建议使用hibernate
    3、人员
           如果开发成员多数不是多年使用hibernate的情况，建议使用mybatis
    4、数据库对于项目的重要程度
           如果项目要求对于数据库可控性好，可深度调优，用mybatis
redis 热点数据问题 20w万
解决办法：
1、新增二级缓存 如ecache、hashmap,发现热key就把它加载jvm中，针对热key直接从jvm取，不走redis层
2、备份热key
不让热key走同一服务，这个热key多个redis上存上一份，接下请求时候通过热key，与随机选取一台redis进行取值，返回数

如何监控热点key？
1、凭借业务经验识别、比如商品秒杀、开市
2、在客户端收集 统计并预警
3、同一入口，redis增加代理层
4、redis自带工具，monitor命令，缺点内存暴增，降低性能。redis4.0.3提供hotkey参数通过redis-cli -hotkey发现热key功能
5、自己抓包评估
redis客户端使用tcp协议与服务进行交互，通信协议采用resp，自己写协议监听端口，安装

springboot spring优缺点
spring 原理 springmvc原理 springboot原理
线程池原理
1、是否大于核心线程数？
2、是否大于工作队列数？
3、是否大于最大线程数？
其他执行拒绝策略

mq消息

分库分布有哪些策略、如何实现

jvm优化

redis存储方式如何实现

redis单线程如何架构满足高可用

oracle与mysql 自增区别

cpu负载及使用率理解
 cpu负载就是cpu在一段时间内正在处理及等待cpu处理的进程数之和的统计信息，也是cpu使用队列的长度统计信息（超过cpu核数*0.7就是不正常）
 cpu使用率：程序在运行期间实时占用的cpu百分比，cpu使用率反应当前cpu繁忙程度，忽高忽低原因在于占用cpu处理的线程可能处于io等待状态但且未释放进入wait
cpu使用率低负载高原因：等待io完成的进程多，导致进程队列过大，但cpu运行的进程却很少，这就提现了到负载过大了，cpu使用率

load average 3个值 分别记录 1min 5min 15min内的系统平均负载 
负载高但cpu使用率低可能原因是频繁上下文切换导致耗费大量的cpu时间，以至于用在运算cpu时间片较少，却有很多进程在等待进行。
cpu上下文频繁切换原因：
1、对应抢占式操作系统而言，当前任务时间片用完之后，系统cpu正常调度下一个任务；
2、当前任务碰到io阻塞，调用线程将挂起此任务，继续下一任务；
3、多个任务抢占锁资源，当前任务没有抢到，被调度器挂起，继续下移任务；
4、用户代码挂起当前任务，让出cpu时间；
5、硬件中断。
https://blog.csdn.net/mimi_csdn/article/details/79375471

分库分表跨表查询
sql解析->执行器（查询）优化->sql路由->sql改写->sql执行->结果归并

继承、超类执行顺序

系统设计

分布式事务
项目管理工具

0、领域驱动设计DDD
      领域服务查询操作、命令操作聚合根（实体1、实体2）
      DDD：是一种软件开发方法
      目标：可测试、可伸缩、组织良好的的软件模型，通过DDD同时提供战略上和战术上的建模工具来帮组我们设计高质量的软件模型
      参与者：用户、业务人员、开发、测试
      领域专家：精通业务的任何人
      领域模型：通过对象模型实现，对象模型包含了数据和行为，并且表达了精准的业务含义
      为什么要使用DDD：
         a、使领域专家和开发者一起工作，这样开发出来的软件能够精准的传达业务规则
         b、精准传达业务规则就是说，此时的软件就像领域专家是编码人员时所开发出来一样
         c、可以帮助业务人员自我提高，提高业务的掌握能力
         e、关键在于对只是的集中、以为这样可以确保软件知识并不是掌握在少数人手中
         f、在领域专家、开发者和软件本身之前不存在“翻译”
          g、设计就是代码、代码就是设计
          h、DDD同时提供了战略设计和战术设计两种方式
        
Dubbo工作原理
       流程：provider向注册中心注册、consumer从注册中心订阅服务，注册中心会通知consumer注册好的服务、consumer通过RPC调用provider、consumer和provider异步通知监控中心
       原理：a、service接口层给服务提供者实现、消费者调用，
                  b、config配置层对dubbo进行各种配置，
                  c、proxy服务代理层，consumer、provider有自己代理的服务，代理间进行网络通信
                  e、registry服务注册层，负责服务的注册与发现
                  f、cluster集群层，封装多个服务提供者的路由及负载均衡，将多个实例组成一个服务
                  g、monitor监控层，对rpc接口调用次数及调用时间进行监控
                  h、protocol远程调用层，封装rpc调动
                   i、exchange信息交流层，封装请求响应模式，同步转异步
                   j、transport网络传输出层，抽象，mina和netty为同一接口
                   k、serices数据序列化层
          RPC：远程过程调用，rpc是采用client-service架构，通过request-respone消息模式实现
          rpc流程：
               a、客户端处理流程中调用Client Stub（就像调用本地方法一样），传递参数
                b、Client Stub将参数编组为消息，然后通过系统系统调用向服务端发送消息
                c、客户端本地操作系统将消息从客户端机器发送到服务机器
                e、服务操作系统将接收到的数据包传递给server Stub
                f、Server Stub解组消息为参数
                g、Server Stub 再调用用服务端的过程，过程执行结果以反方向的相同步骤响应给客户端
         rpc协议：rpc调用过程采用的协议称为rpc协议（xml-rpc/soap-rpc/json-rpc等）。消息由哪些部分构成及消息的表示形式构成消息协议。rpc协议规定的请求、响应消息格式在TCP（传输控制协议）上选用或自定义协议来完成消息交互。（可以选用通用的标准协议如http、https，也可以根据需要定义自己的消息协议）
       rpc框架：封装好参数编组、消息解组、底层网络通信的rpc程序开发框架，带来的便捷是可以直接在其基础上只专注过程代码编写。
        传统webservice：cxf、axis、java自带的ax-ws等，大多数基于标准的soap协议
         新兴微服务架构：Dubbo、Spring Cloud Apache Thrift等等
       为什么要用rpc框架：服务化、可重用、系统间调用

可见性问题：
1、所见非所得
2、无法肉眼检测程序的准确性
3、不同的运行平台有不同的表现
4、错误很难重现
                
volatile(解决可见性问题)
1、禁止缓存
2、对volatile变量相关的指令不做重排
final：在对象的函数构造中设置对象字段，当线程看到该对象时，将始终看到该对象的final字段的正确构造版本。
Word Tearing 字节处理
有些处理器没有提供些单个字节的功能，这样的处理器上更新byte数组，若只是简单的读取整个内容，更新对应的字节，让后再将整个内容回写到内存，将是不合法的。这个问题有时候称“字节分裂”
注意：经量不要对bye[]中的元素进行重新赋值，更不要在多线程程序中这样做
dubbo/long 每次读取只读32位，需要加上volatile才能保证原子操作
java内存模型：java语言多线程的规则、cpu缓存、编译器指令重排
原子操作

CAS:(Compare san swap)：属于硬件源语,是原子操作
第一个参数跟内存的旧值比较，相同才替换旧值。如果失败重试（自旋）
J.U.C

ABA问题

出现线程安全的原因：1、可见性问题  2、原子性问题（数据一致性）	

Java锁：自旋锁、乐观锁、悲观锁；独享锁（写）、共享锁（读）；可重入、不可重入锁

Synchronized:（可重入、独享锁、悲观锁）
1、用于实例方法、静态方法时，隐式指定锁对象
2、用于代码块时，显示指定锁对象
3、锁的作用域：对象锁、类锁、分布式锁
原理：状态存放在对象头mark Word中（5中状态：未锁定、偏向锁、轻量级锁、重量级锁），用cas操作修改mark world内存区
优点：
1、使用简单、语义清晰
2、jvm提供多种方案（锁粗化、锁消除、偏向锁、轻量级锁）
3、锁的释放有jvm来完成，不用人工干预，降低了死锁的可能性
缺点：无法实现一些高级功能如公平锁、中断锁、超时锁、读写锁、共享锁等
RentantLock:
await队列、owner、count（cas抢锁修改count）
ReadWriteLock(waiters/read/write/count)
原理复述：
使用场景：读取操作多于写入操作，改进互斥锁的性能，比如：集合的并发线程安全改造、缓存组件
HashMap:线程不安全
HastTable：线程安全（不推荐原因：用了Synchronized关键字修饰）
ConcunrrentHashMap:并发线程安全
锁降级：获取写锁再获取读锁再释放写锁
HashMap1.7:数组+链表 非原子操作，线程不安全
HashMap1.8:
ConcurrentHashMap1.7: segment+hashTable，分段锁，并发级别决绝segment 
ConcurrentHashMap1.8:cas+sychronized 数组+链表（红黑树） 锁住粒度小，并发级别高
ConcurrentSkipListMap:
JVM

百万连接

resful api 参数放在url上

nginx配置https

docker查看日志

主线程停止及通知，如何监听

设计模式

数据库乐观锁实现
数据库加一个版本号字段，更新时候比较 相同则更新否则拒绝

jpa居于什么Hibernate 还是Mybatics

url带参数 
注解：@PathVaribale
1.介绍几种如何处理url中的参数的注解
@PathVaribale 获取url中的数据
@RequestParam 获取请求参数的值
@GetMapping 组合注解，是 @RequestMapping(method = RequestMethod.GET) 的缩写

分布式事务
   高并发场景：两阶段提交+最终一致性
    tcc编程模式：是两阶段提交一种变种，tcc提供一种编程框架，整个业务逻辑分三块：try、 confirm、cancel；try阶段去扣库存、confirm阶段去更新订单状态，如更新订单失败进入Cancel阶段，去回复库存。tcc就是就是通过代码认为实现两阶段提交，不同的业务场景写的代码不一样.


1、你对什么技术熟悉：dubbo、springcoud、redis、mq、

2、抽象类用过吗？具体举例子？

3、字节流字符流的区别
字节流与字符流的区别
字节流和字符流使用是非常相似的，那么除了操作代码的不同之外，还有哪些不同呢？
字节流在操作的时候本身是不会用到缓冲区（内存）的，是与文件本身直接操作的，而字符流在操作的时候是使用到缓冲区的
字节流在操作文件时，即使不关闭资源（close方法），文件也能输出，但是如果字符流不使用close方法的话，则不会输出任何内容，说明字符流用的是缓冲区，并且可以使用flush方法强制进行刷新缓冲区，这时才能在不close的情况下输出内容

那开发中究竟用字节流好还是用字符流好呢？
在所有的硬盘上保存文件或进行传输的时候都是以字节的方法进行的，包括图片也是按字节完成，而字符是只有在内存中才会形成的，所以使用字节的操作是最多的。

如果要java程序实现一个拷贝功能，应该选用字节流进行操作（可能拷贝的是图片），并且采用边读边写的方式（节省内存）。

遇到什么线上问题？

springboot注解

缓存会话

1.1 Redission分布式锁
   
2、Mybatis原理

3、SpringBoot原理

4、SpringCloud 

5、Saas

6、MQ

7、ES （搜索）

8、Redis

9、Nginx

10、Shareding jdbc原理

11、Mysql B+数
       mysqldump -u 用户名 -p 数据库名 > 导出的文件名
       mysqldump -u 用户名 -p 数据库 表名 > 导出文件名
       导出数据库结构
       mysqldump -u 用户名 -p -d --add-drop-table 数据库名 >路径及文件名（-d 无数据，--add-drop-table ：create前添加 drop table）

12、执行计划具体表示 explain

1）、id列数字越大越先执行，如果说数字一样大，那么就从上往下依次执行，id列为null的就表是这是一个结果集，不需要使用它来进行查询。
 
2）、select_type列常见的有：
A：simple：表示不需要union操作或者不包含子查询的简单select查询。有连接查询时，外层的查询为simple，且只有一个
B：primary：一个需要union操作或者含有子查询的select，位于最外层的单位查询的select_type即为primary。且只有一个
C：union：union连接的两个select查询，第一个查询是dervied派生表，除了第一个表外，第二个以后的表select_type都是union
D：dependent union：与union一样，出现在union 或union all语句中，但是这个查询要受到外部查询的影响
E：union result：包含union的结果集，在union和union all语句中,因为它不需要参与查询，所以id字段为null
F：subquery：除了from字句中包含的子查询外，其他地方出现的子查询都可能是subquery
G：dependent subquery：与dependent union类似，表示这个subquery的查询要受到外部表查询的影响
H：derived：from字句中出现的子查询，也叫做派生表，其他数据库中可能叫做内联视图或嵌套select
 
3）、table
显示的查询表名，如果查询使用了别名，那么这里显示的是别名，如果不涉及对数据表的操作，那么这显示为null，如果显示为尖括号括起来的<derived N>就表示这个是临时表，后边的N就是执行计划中的id，表示结果来自于这个查询产生。如果是尖括号括起来的<union M,N>，与<derived N>类似，也是一个临时表，表示这个结果来自于union查询的id为M,N的结果集。
 
4）、type
依次从好到差：system，const，eq_ref，ref，fulltext，ref_or_null，unique_subquery，index_subquery，range，index_merge，index，ALL，除了all之外，其他的type都可以使用到索引，除了index_merge之外，其他的type只可以用到一个索引
A：system：表中只有一行数据或者是空表，且只能用于myisam和memory表。如果是Innodb引擎表，type列在这个情况通常都是all或者index
B：const：使用唯一索引或者主键，返回记录一定是1行记录的等值where条件时，通常type是const。其他数据库也叫做唯一索引扫描
C：eq_ref：出现在要连接过个表的查询计划中，驱动表只返回一行数据，且这行数据是第二个表的主键或者唯一索引，且必须为not null，唯一索引和主键是多列时，只有所有的列都用作比较时才会出现eq_ref
D：ref：不像eq_ref那样要求连接顺序，也没有主键和唯一索引的要求，只要使用相等条件检索时就可能出现，常见与辅助索引的等值查找。或者多列主键、唯一索引中，使用第一个列之外的列作为等值查找也会出现，总之，返回数据不唯一的等值查找就可能出现。
E：fulltext：全文索引检索，要注意，全文索引的优先级很高，若全文索引和普通索引同时存在时，mysql不管代价，优先选择使用全文索引
F：ref_or_null：与ref方法类似，只是增加了null值的比较。实际用的不多。
G：unique_subquery：用于where中的in形式子查询，子查询返回不重复值唯一值
H：index_subquery：用于in形式子查询使用到了辅助索引或者in常数列表，子查询可能返回重复值，可以使用索引将子查询去重。
I：range：索引范围扫描，常见于使用>,<,is null,between ,in ,like等运算符的查询中。
J：index_merge：表示查询使用了两个以上的索引，最后取交集或者并集，常见and ，or的条件使用了不同的索引，官方排序这个在ref_or_null之后，但是实际上由于要读取所个索引，性能可能大部分时间都不如range
K：index：索引全表扫描，把索引从头到尾扫一遍，常见于使用索引列就可以处理不需要读取数据文件的查询、可以使用索引排序或者分组的查询。
L：all：这个就是全表扫描数据文件，然后再在server层进行过滤返回符合要求的记录。
 
5）、possible_keys
查询可能使用到的索引都会在这里列出来
 
6）、key
查询真正使用到的索引，select_type为index_merge时，这里可能出现两个以上的索引，其他的select_type这里只会出现一个。
 
7）、key_len
用于处理查询的索引长度，如果是单列索引，那就整个索引长度算进去，如果是多列索引，那么查询不一定都能使用到所有的列，具体使用到了多少个列的索引，这里就会计算进去，没有使用到的列，这里不会计算进去。留意下这个列的值，算一下你的多列索引总长度就知道有没有使用到所有的列了。要注意，mysql的ICP特性使用到的索引不会计入其中。另外，key_len只计算where条件用到的索引长度，而排序和分组就算用到了索引，也不会计算到key_len中。
 
8）、ref
如果是使用的常数等值查询，这里会显示const，如果是连接查询，被驱动表的执行计划这里会显示驱动表的关联字段，如果是条件使用了表达式或者函数，或者条件列发生了内部隐式转换，这里可能显示为func
 
9）、rows
这里是执行计划中估算的扫描行数，不是精确值
 
10）、extra
这个列可以显示的信息非常多，有几十种，常用的有
A：distinct：在select部分使用了distinc关键字
B：no tables used：不带from字句的查询或者From dual查询
C：使用not in()形式子查询或not exists运算符的连接查询，这种叫做反连接。即，一般连接查询是先查询内表，再查询外表，反连接就是先查询外表，再查询内表。
D：using filesort：排序时无法使用到索引时，就会出现这个。常见于order by和group by语句中
E：using index：查询时不需要回表查询，直接通过索引就可以获取查询的数据。
F：using join buffer（block nested loop），using join buffer（batched key accss）：5.6.x之后的版本优化关联查询的BNL，BKA特性。主要是减少内表的循环数量以及比较顺序地扫描查询。
G：using sort_union，using_union，using intersect，using sort_intersection：
using intersect：表示使用and的各个索引的条件时，该信息表示是从处理结果获取交集
using union：表示使用or连接各个使用索引的条件时，该信息表示从处理结果获取并集
using sort_union和using sort_intersection：与前面两个对应的类似，只是他们是出现在用and和or查询信息量大时，先查询主键，然后进行排序合并后，才能读取记录并返回。
H：using temporary：表示使用了临时表存储中间结果。临时表可以是内存临时表和磁盘临时表，执行计划中看不出来，需要查看status变量，used_tmp_table，used_tmp_disk_table才能看出来。
I：using where：表示存储引擎返回的记录并不是所有的都满足查询条件，需要在server层进行过滤。查询条件中分为限制条件和检查条件，5.6之前，存储引擎只能根据限制条件扫描数据并返回，然后server层根据检查条件进行过滤再返回真正符合查询的数据。5.6.x之后支持ICP特性，可以把检查条件也下推到存储引擎层，不符合检查条件和限制条件的数据，直接不读取，这样就大大减少了存储引擎扫描的记录数量。extra列显示using index condition
J：firstmatch(tb_name)：5.6.x开始引入的优化子查询的新特性之一，常见于where字句含有in()类型的子查询。如果内表的数据量比较大，就可能出现这个
K：loosescan(m..n)：5.6.x之后引入的优化子查询的新特性之一，在in()类型的子查询中，子查询返回的可能有重复记录时，就可能出现这个
 
除了这些之外，还有很多查询数据字典库，执行计划过程中就发现不可能存在结果的一些提示信息
 
11）、filtered
使用explain extended时会出现这个列，5.7之后的版本默认就有这个字段，不需要使用explain extended了。这个字段表示存储引擎返回的数据在server层过滤后，剩下多少满足查询的记录数量的比例，注意是百分比，不是具体记录数。

13、索引有哪几种类型（普通索引（可为空）、唯一索引（可为空）、注解索引（可为空）、组合索引、全文索引）
1、普通索引
普通索引是最基本的索引，它没有任何限制，值可以为空；仅加速查询。可以通过以下几种方式来创建或删除：
1）、直接创建索引
1 CREATE INDEX index_name ON table(column(length))
2）、修改表结构的方式添加索引
1 ALTER TABLE table_name ADD INDEX index_name ON (column(length))
3）、删除索引
1 DROP INDEX index_name ON table
2、唯一索引
唯一索引与普通索引类似，不同的就是：索引列的值必须唯一，但允许有空值。如果是组合索引，则列值的组合必须唯一。简单来说：唯一索引是加速查询 + 列值唯一（可以有null）。以通过以下几种方式来创建：
1）、创建唯一索引
1 CREATE UNIQUE INDEX indexName ON table(column(length))
2）、修改表结构
1 ALTER TABLE table_name ADD UNIQUE indexName ON (column(length))
3、主键索引
主键索引是一种特殊的唯一索引，一个表只能有一个主键，不允许有空值。简单来说：主键索引是加速查询 + 列值唯一（不可以有null）+ 表中只有一个。
一般是在建表的时候同时创建主键索引：
1 CREATE TABLE mytable( ID INT NOT NULL, username VARCHAR(16) NOT NULL, PRIMARY KEY(ID) );
当然也可以用 ALTER 命令。记住：一个表只能有一个主键。
4、组合索引
组合索引指在多个字段上创建的索引，只有在查询条件中使用了创建索引时的第一个字段，索引才会被使用。使用组合索引时遵循最左前缀集合。
可以说：组合索引是多列值组成的一个索引，专门用于组合搜索，其效率大于索引合并。
1 ALTER TABLE `table` ADD INDEX name_city_age (name,city,age);
5、全文索引
全文索引主要用来查找文本中的关键字，而不是直接与索引中的值相比较。fulltext索引跟其它索引大不相同，它更像是一个搜索引擎，而不是简单的where语句的参数匹配。fulltext索引配合match against操作使用，而不是一般的where语句加like。它可以在create table，alter table ，create index使用，不过目前只有char、varchar，text 列上可以创建全文索引。值得一提的是，在数据量较大时候，现将数据放入一个没有全局索引的表中，然后再用CREATE index创建fulltext索引，要比先为一张表建立fulltext然后再将数据写入的速度快很多。
1）、创建表的适合添加全文索引
12345678 CREATE TABLE `table` (    `id` int(11) NOT NULL AUTO_INCREMENT ,    `title` char(255) CHARACTER NOT NULL ,    `content` text CHARACTER NULL ,    `time` int(10) NULL DEFAULT NULL ,    PRIMARY KEY (`id`),    FULLTEXT (content));
2）、修改表结构添加全文索引
1 ALTER TABLE article ADD FULLTEXT index_content(content)
3）、直接创建索引
1 CREATE FULLTEXT INDEX index_content ON article(content)
简单来说：全文索引是对文本的内容进行分词，进行搜索。

14、ThreadLocal原理
      线程变量
       拒绝策略：
AbortPolicy
为java线程池默认的阻塞策略，不执行此任务，而且直接抛出一个运行时异常，切记ThreadPoolExecutor.execute需要try catch，否则程序会直接退出。
DiscardPolicy
直接抛弃，任务不执行，空方法
DiscardOldestPolicy
从队列里面抛弃head的一个任务，并再次execute 此task。
CallerRunsPolicy
在调用execute的线程里面执行此command，会阻塞入口
用户自定义拒绝策略
实现RejectedExecutionHandler，并自己定义策略模式
再次需要注意的是，ThreadPoolExecutor.submit（） 函数，此方法内部调用的execute方法，并把execute执行完后的结果给返回，但如果任务并没有执行的话（被拒绝了），则submit返回的future.get()会一直等到。

15、JUC包
　CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。

CyclicBarrier字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了。

　Semaphore翻译成字面意思为 信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。

　1）CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：
　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；
　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；
　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。
　　2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
16、list.foreach(remove)??

17、缓存一致性问题
      解决缓存一致性问题的方法有一下2种：
      a、通过在总线加LOCK#锁的方式
      b、通过缓存一致性协议（所以就出现了缓存一致性协议。该协议保证了每个缓存中使用的共享变量的副本是一致的。它的核心思想是：当CPU向内存写入数据时，如果发现操作的变量是共享变量，即在其他CPU中也存在该变量的副本，会发出信号通知其他CPU将该变量的缓存置为无效状态，因此当其他CPU需要读取这个变量时，发现自己缓存中缓存行是无效的，那么它就会从内存重新读取。java中的volatile就是该协议的实现）
18、会话管理

19、cas优缺点
cas优点：如一描述在并发量不是很高时cas机制会提高效率。
cas缺点：
a、cpu开销大，在高并发下，许多线程，更新一变量，多次更新不成功，循环反复，给cpu带来大量压力。
b、只是一个变量的原子性操作，不能保证代码块的原子性。
c、ABA问题

20、百万连接
用Netty实现单机百万TCP长连接



1、掌握DDD领域驱动设计实现，基于领域驱动设计了项目评审预约项目、Bom一致性比对预警项目、华星展厅参观预约项目
及用户行为分析平台。
2、熟悉主流的java Web框架，熟悉多层架构开发。
3、熟悉分布消息中间件、缓存中间件
4、
熟悉spring、springmvc、springboot、cxf、dubbo、xxljob等分布式应用。
3、基于redis及mq处理过高并发场景，如图文消费发送服务、用户行为分析平台，应用到rediison分布式锁、rocketMQ、sharding-sphere分片技术，多线程、考勤异常推送服务
简历项目周期设置长点
~~~


关键点必须掌握：
分布式锁（redis、zookeeper） dubbo运行原理 分库分表 mysql 
~~~
（开发、架构设计、自动化部署）
介绍项目亮点
a、工作经历及担任职位
b、项目介绍 
熟悉spring、springmvc、springboot、cxf、dubbo、xxljob等分布式应用。
3、基于redis及mq处理过高并发场景，如图文消费发送服务、用户行为分析平台，应用到rediison分布式锁、rocketMQ、sharding-sphere分片技术，多线程、考勤异常推送服务
~~~
- mysql性能优化
~~~
1、用什么指令查看mysql进程
explain 执行计划 ;
2、mysql性能优化，设置三个组件，两两组件查询效果
~~~
- 多线程
~~~
volatile sycronized作用
多线程应用
~~~
- springboot
~~~
新增注解特性
配置文件获取方式
~~~

- 分布式
~~~
redis分布锁实现
~~~
- dubbo调用过程
~~~
协议 dubbo调用原理
~~~
- MQ topic类型
- 短信防刷问题
- httpcomones底层原理

- 什么是分布式

- http协议是否有状态
~~~

http协议是无状态的是说http协议不记录历史的请求，就像指数函数一样，无记忆性。

无状态服务器是指一种把每个请求作为与之前任何请求都无关的独立的事务的服务器。
~~~

- es
- 分布式锁实现
- syhcronized 是否可以重入
- mq消息 消费完后准回写确认，系统异常，下次消息发送就会出现冗余，如何解决冗余问题
- 如何避免幂等性问题
- B树 B+树
- doubbo运行原理
- mysql误删除如何恢复
- rocketmq如何避免重复消费
- 分库分表有哪些策略
- 项目时间修改
- 实际并发测试，并发数量修改
- shardingspere查询遇到什么问题
- 操作系统
- 数据库
- 算法
- 你的重点是什么？
- 直奔重点
- mq选型依据
- redssion实现原理
~~~
https://www.cnblogs.com/kiko2014551511/p/11527108.html
~~~
- sentinel原理 如何做资源隔离
- 数据库索引原理
~~~
索引类别
唯一索引:
主键索引：
聚集索引：
索引是在存储引擎中实现的，也就是说不同的存储引擎，会使用不同的索引。MyISAM和InnoDB存储引擎：只支持BTREE索引，也就是说默认使用BTREE，不能够更换。MEMORY/HEAP存储引擎：支持HASH和BTREE索引。
1、索引我们分为四类来讲单列索引(普通索引，唯一索引，主键索引)、组合索引、全文索引、空间索引、
1.1、单列索引：一个索引只包含单个列，但一个表中可以有多个单列索引。 这里不要搞混淆了。
    1.1.1、普通索引：MySQL中基本索引类型，没有什么限制，允许在定义索引的列中插入重复值和空值，纯粹为了查询数据更快一点。
    1.1.2、唯一索引：索引列中的值必须是唯一的，但是允许为空值，
    1.1.3、主键索引：是一种特殊的唯一索引，不允许有空值。（主键约束，就是一个主键索引）
1.2、组合索引：在表中的多个字段组合上创建的索引，只有在查询条件中使用了这些字段的左边字段时，索引才会被使用，使用组合索引时遵循最左前缀集合。例如，这里由id、name和age3个字段构成的索引，索引行中就按id/name/age的顺序存放，索引可以索引下面字段组合(id，name，age)、(id，name)或者(id)。如果要查询的字段不构成索引最左面的前缀，那么就不会是用索引，比如，age或者（name，age）组合就不会使用索引查询
1.3、全文索引：全文索引，只有在MyISAM引擎上才能使用，只能在CHAR,VARCHAR,TEXT类型字段上使用全文索引，介绍了要求，说说什么是全文索引，就是在一堆文字中，通过其中的某个关键字等，就能找到该字段所属的记录行，比如有"你是个大煞笔，二货 ..." 通过大煞笔，可能就可以找到该条记录。这里说的是可能，因为全文索引的使用涉及了很多细节，我们只需要知道这个大概意思。
1.4、空间索引：空间索引是对空间数据类型的字段建立的索引，MySQL中的空间数据类型有四种，GEOMETRY、POINT、LINESTRING、POLYGON。在创建空间索引时，使用SPATIAL关键字。要求，引擎为MyISAM，创建空间索引的列，必须将其声明为NOT NULL。可能跟游戏开发有关。
原文链接：https://blog.csdn.net/qiuchaoxi/article/details/80010489
~~~
- 索引类型
~~~
MySQL目前主要有以下几种索引类型：FULLTEXT，HASH，BTREE，RTREE
全文索引：
即为全文索引，目前只有MyISAM引擎支持。其可以在CREATE TABLE ，ALTER TABLE ，CREATE INDEX 使用，不过目前只有 CHAR、VARCHAR ，TEXT 列上可以创建全文索引。值得一提的是，在数据量较大时候，现将数据放入一个没有全局索引的表中，然后再用CREATE INDEX创建FULLTEXT索引，要比先为一张表建立FULLTEXT然后再将数据写入的速度快很多。
创建ALTER TABLE table ADD INDEX `FULLINDEX` USING FULLTEXT(`cname1`[,cname2…]);
使用SELECT * FROM table WHERE MATCH(cname1[,cname2…]) AGAINST ('word' MODE );
其中， MODE为搜寻方式（IN BOOLEAN MODE ，IN NATURAL LANGUAGE MODE ，IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION / WITH QUERY EXPANSION）。
关于这三种搜寻方式，愚安在这里也不多做交代，简单地说，就是，布尔模式，允许word里含一些特殊字符用于标记一些具体的要求，如+表示一定要有，-表示一定没有，*表示通用匹配符，是不是想起了正则，类似吧

———————————————
原文链接：https://blog.csdn.net/jaryle/article/details/52023295

HASH：
，hash就是一种（key=>value）形式的键值对，如数学中的函数映射，允许多个key对应相同的value，但不允许一个key对应多个value。正是由于这个特性，hash很适合做索引，为某一列或几列建立hash索引，就会利用这一列或几列的值通过一定的算法计算出一个hash值，对应一行或几行数据（这里在概念上和函数映射有区别，不要混淆）。在Java语言中，每个类都有自己的hashcode()方法，没有显示定义的都继承自object类，该方法使得每一个对象都是唯一的，在进行对象间equal比较，和序列化传输中起到了很重要的作用。hash的生成方法有很多种，足可以保证hash码的唯一性，例如在MongoDB中，每一个document都有系统为其生成的唯一的objectID（包含时间戳，主机散列值，进程PID，和自增ID）也是一种hash的表现。
由于hash索引可以一次定位，不需要像树形索引那样逐层查找,因此具有极高的效率。那为什么还需要其他的树形索引呢？
（1）Hash 索引仅仅能满足"=","IN"和"<=>"查询，不能使用范围查询。 
（2）Hash 索引无法被用来避免数据的排序操作。 
（3）Hash 索引不能利用部分索引键查询。 
对于组合索引，Hash 索引在计算 Hash 值的时候是组合索引键合并后再一起计算 Hash 值，而不是单独计算 Hash 值，所以通过组合索引的前面一个或几个索引键进行查询的时候，Hash 索引也无法被利用。 
（4）Hash 索引在任何时候都不能避免表扫描。 
（5）Hash 索引遇到大量Hash值相等的情况后性能并不一定就会比B-Tree索引高。 
对于选择性比较低的索引键，如果创建 Hash 索引，那么将会存在大量记录指针信息存于同一个 Hash 值相关联。这样要定位某一条记录时就会非常麻烦，会浪费多次表数据的访问，而造成整体性能低下。
原文链接：https://blog.csdn.net/jaryle/article/details/52023295

当我们为某一列或某几列建立hash索引时（目前就只有MEMORY引擎显式地支持这种索引），会在硬盘上生成类似如下的文件：

hash值 	存储地址    
1db54bc745a1	77#45b5 
4bca452157d4	76#4556,77#45cc…
…

hash值即为通过特定算法由指定列数据计算出来，磁盘地址即为所在数据行存储在硬盘上的地址（也有可能是其他存储地址，其实MEMORY会将hash表导入内存）。

这样，当我们进行WHERE age = 18 时，会将18通过相同的算法计算出一个hash值==>在hash表中找到对应的储存地址==>根据存储地址取得数据。

所以，每次查询时都要遍历hash表，直到找到对应的hash值，如（4），数据量大了之后，hash表也会变得庞大起来，性能下降，遍历耗时增加，

BTREE
BTREE索引就是一种将索引值按一定的算法，存入一个树形的数据结构中
BTREE在MyISAM里的形式和Innodb稍有不同
在 Innodb里，有两种形态：一是primary key形态，其leaf node里存放的是数据，而且不仅存放了索引键的数据，还存放了其他字段的数据。二是secondary index，其leaf node和普通的BTREE差不多，只是还存放了指向主键的信息.
而在MyISAM里，主键和其他的并没有太大区别。不过和Innodb不太一样的地方是在MyISAM里，leaf node里存放的不是主键的信息，而是指向数据文件里的对应数据行的信息.
RTREE

RTREE在mysql很少使用，仅支持geometry数据类型，支持该类型的存储引擎只有MyISAM、BDb、InnoDb、NDb、Archive几种。

相对于BTREE，RTREE的优势在于范围查找.
~~~
- 各种索引使用
~~~
（1）对于BTREE这种Mysql默认的索引类型，具有普遍的适用性
（2）由于FULLTEXT对中文支持不是很好，在没有插件的情况下，最好不要使用。其实，一些小的博客应用，只需要在数据采集时，为其建立关键字列表，通过关键字索引，也是一个不错的方法，至少愚安我是经常这么做的。
（3）对于一些搜索引擎级别的应用来说，FULLTEXT同样不是一个好的处理方法，Mysql的全文索引建立的文件还是比较大的，而且效率不是很高，即便是使用了中文分词插件，对中文分词支持也只是一般。真要碰到这种问题，Apache的Lucene或许是你的选择。
（4）正是因为hash表在处理较小数据量时具有无可比拟的素的优势，所以hash索引很适合做缓存（内存数据库）。如mysql数据库的内存版本Memsql，使用量很广泛的缓存工具Mencached，NoSql数据库redis等，都使用了hash索引这种形式。当然，不想学习这些东西的话Mysql的MEMORY引擎也是可以满足这种需求的。

（5）
~~~
- 增加索引为什么会快
- 数据库二进制文件
- redis（数据库）如何存储

~~~
正是因为这个二叉树算法，让查询速度快很多，二叉树的原理，就是取最中间的一个数，然后把大于这个数的往右边排，小于这个数的就向左排，每次减半，然后依次类推，每次减半，形成一个树状结构图
~~~
- Aop原理 实现 应用场景
- spring bean生命周期
- mybatis原理 hibernate原理 优缺点，有什么区别
- redis 热点数据问题 20w万
- springboot spring优缺点
- 线程池原理
- mq消息 
- 分库分布有哪些策略、如何实现


https://blog.csdn.net/u011665991/article/details/89206148?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task#%E4%B8%80%E3%80%81Java%20%E5%9F%BA%E7%A1%80


- JavaCore HeapDump 区别
~~~
l  JavaCore是关于CPU的
JavaCore文件主要保存的是Java应用各线程在某一时刻的运行的位置，即JVM执行到哪一个类、哪一个方法、哪一个行上。它是一个文本文件，打开后可以看到每一个线程的执行栈，以stack trace的显示。通过对JavaCore文件的分析可以得到应用是否“卡”在某一点上，即在某一点运行的时间太长，例如数据库查询，长期得不到响应，最终导致系统崩溃等情况。
l  HeapDump文件是关于内存的。
HeapDump文件是一个二进制文件，它保存了某一时刻JVM堆中对象使用情况，这种文件需要相应的工具进行分析，如IBM Heap Analyzer这类工具。这类文件最重要的作用就是分析系统中是否存在内存溢出的情况。

https://www.cnblogs.com/jingmoxukong/p/5509196.html
~~~
~~~
a.精通Javaee、Web，具有扎实的算法及数据结构功底；
b.掌握Spring、SpringMvc、Springboot、Mybatis等开源框架，有较强的源码研究理解能力；
c.掌握Redis、Ngnix、Tomcat部署、优化参数配置；
d.熟悉公众号、服务号和小程序开发；
e.熟悉消息中间件(ActiveMQ、RabbitMQ等)、事务处理中间件、数据访问中间件(ODBC)、工作流中间件(JBPM、Activiti等)；

a.满足（1）的所有要求；
b.能够独立进行项目的大部分开发工作,有系统进度把控能力；
c.熟悉高并发、高性能的分布式系统的设计、应用及调优，如缓存技术、网站优化、服务器优化、集群技术处理、网站负载均衡、系统性能调优、安全防御等技术；
d.精通Java体系，Gradle，Maven，Git等常用工具的使用, 熟悉JVM。

http://www.nnjr.co/html/3760/816.html
~~~
