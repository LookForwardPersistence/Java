### 分库分表

- maven 引入jar包
~~~
<!-- shardingsphere begin -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.16</version>
        </dependency>

        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>4.0.0-RC1</version>
        </dependency>
        <!-- shardingsphere end -->
~~~
- properies配置
~~~
spring.shardingsphere.datasource.names=message
spring.shardingsphere.datasource.message.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.message.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.message.url=jdbc:mysql://localhost:3306/dawn?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
spring.shardingsphere.datasource.message.username=dawn
spring.shardingsphere.datasource.message.password=dawn


spring.jpa.database=mysql
spring.jpa.show-sql= true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

#表分区
spring.shardingsphere.sharding.tables.user.actualDataNodes=message.user_${201909..201912}
#分片数据库字段
spring.shardingsphere.sharding.tables.user.tableStrategy.standard.shardingColumn=create_date
#自定义分片算法
spring.shardingsphere.sharding.tables.user.tableStrategy.standard.preciseAlgorithmClassName=com.dawn.messagelog.MessageShardingAlgorithm
#inline 表达式
#spring.shardingsphere.sharding.tables.user.tableStrategy.inline.shardingColumn=id
#spring.shardingsphere.sharding.tables.user.tableStrategy.inline.algorithmExpression=user_${id.longValue()%4}
~~~
- 分片策略算法
~~~
/**
 * 分片算法算法
 * Created by fanqianghua on 2019/9/9.
 */
public class MessageShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String yearMonth = format.format(preciseShardingValue.getValue());
        for(String tableName:collection){
            if(tableName.indexOf(yearMonth)>-1){
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
~~~
