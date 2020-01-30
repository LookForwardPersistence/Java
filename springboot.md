### 事物注解
面向切面编程，aop 方法前增强（开始事物）、方法后置增强（提交、回滚）

### 配置
~~~
#thymeleaf配置
#缓存配置，生产环境设置true
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.check-template-location=true
spring.thymeleaf.suffix=.html
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML5
~~~

### sringboot 通过jar包启动配置  java -jar *.jar
~~~
 <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <!-- java -jar *.jar-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring.boot.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
~~~

### 互联网架构演进之路
~~~
性能越来越差
存储空间不足
用户越多 访问越大 数据库访问瓶颈
访问高峰期 应用服务器瓶颈

发展问题
  数据库读写再成功为瓶颈 （读写分离 mybatis mycat sharding-jdbc）
  地域网络环境差别大，用户访问体验（反向代理，cdn加速）
  骷髅表、复杂文本检索 
  应用庞大，迭代周期越来越快，牵一发而动全身
  数据挖掘分析 监控
~~~

## CAP理论
~~~
Consistency 一致性
   严格一致性（strict Consistency）(强)
   顺序一致性（Sequential Consitency）（弱）
   最总一致性（Eventual Consitency）(弱)
Availablity 可用性
Partition tolerance 分区容错性
不能同时满足，原因：一致性容错同步时不可用（保证一致性损失可用性）
~~~

