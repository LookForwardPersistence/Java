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


1.1 Redission分布式锁
   
2、Mybatis原理

3、SpringBoot

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
