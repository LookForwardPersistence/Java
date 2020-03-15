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
