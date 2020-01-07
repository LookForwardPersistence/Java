### 启动nacos
~~~
sh startup.sh -m standalone
~~~


### 配置
~~~
[{
    "//resource":"资源名称，限流规则作用对象",
    "resource":"/sentMessage",
    "//limitApp":"流控针对的调用来源，若为default则不区分调用来源",
    "limitApp":"default",
    "//grade":"限流阈值类型（PQS或并发线程数）0代表根据并发量来限流，1代表根据QPS来进行流量控制",
    "grade":1,
    "//count":"限流阈值",
    "count":3,
    "/strategy":"调用关系限流策略",
    "strategy":0,
    "//controlBehavior":"限流控制效果（直接拒绝、Warn Up、匀速排队）",
    "controlBehavior":0,
    "//clusterMode":"是否集群模式",
    "clusterMode":false
}]
~~~



### springboot-sentinel-nacos
~~~
spring.application.name=center-strategy
# 限流配置
#sentinel dashboard
spring.cloud.sentinel.transport.dashboard=Ip:8082
#sentinel datasource nacos
spring.cloud.sentinel.datasource.ds.nacos.serverAddr=IP:8848
#nacos  存储规则dataId
spring.cloud.sentinel.datasource.ds.nacos.dataId=${spring.application.name}-sentinel
#nacos存储规则groupId
spring.cloud.sentinel.datasource.ds.nacos..groupId=DEFAULT_GROUP

spring.cloud.sentinel.datasource.ds.nacos.ruleType=flow

~~~

### maven
~~~
<packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
         <version>2.2.0.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
         <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
         <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
         <java.version>1.8</java.version>
         <spring.cloud.version>RELEASE</spring.cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <!--nacos-->
       <!-- <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>nacos-config-spring-boot-starter</artifactId>
            <version>0.2.1</version>
        </dependency>-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>
    </dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring.cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>0.2.2.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

    <build>
       <plugins>
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
           </plugin>
       </plugins>
    </build>
~~~
