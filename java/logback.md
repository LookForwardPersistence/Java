### 依赖
~~~
  <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.28</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
~~~
### resources下 logback.xml logback.test.mxl
~~~
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <!--定义日志文件的存储地址 不能存相对地址-->
    <property name="LOG_HOME" value="/log"/>
    <!--控制台输出-->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
        <!--格式化输出：%d表示日期 %thread线程名称 %-5level级别从左显示5个字符宽度 %msg日志消息 %n换行-->
        <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
    <!--按照每天生成日志文件-->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
             <!--日志文件输入文件名-->
            <FileNamePattern>${LOG_HOME}/log.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
       <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
           <!--格式化输出：%d表示日期 %thread线程名称 %-5level级别从左显示5个字符宽度 %msg日志消息 %n换行-->
           <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
       </encoder>
        <!--日志文件大小设置-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>10MB</MaxFileSize>
        </triggeringPolicy>
    </appender>

    <!--日志输出级别-->
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
~~~
