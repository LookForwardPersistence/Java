### 批量drop table（mysql）
~~~
SELECT concat('DROP TABLE IF EXISTS `', table_name, '`;')
FROM information_schema.tables
WHERE Table_name LIKE '%user_20%';
~~~
### check the manual that corresponds to your MySQL server version for the right syntax to use  异常解决方案
- table name是否用了数据库的关键字
- 方言检查
~~~
如mysql8 对应 org.hibernate.dialect.MySQL8Dialect
mysql5.7 对应 org.hibernate.dialect.MySQL5InnoDBDialect
driver-class-name 检查
com.mysql.cj.jdbc.Driver
~~~
