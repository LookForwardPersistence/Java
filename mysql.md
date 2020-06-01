### 国内下载
~~~
http://mirrors.sohu.com/mysql/MySQL-8.0/
~~~

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
### DATE_FORMAT(date,format)
~~~
格式	描述
%a	缩写星期名
%b	缩写月名
%c	月，数值
%D	带有英文前缀的月中的天
%d	月的天，数值(00-31)
%e	月的天，数值(0-31)
%f	微秒
%H	小时 (00-23)
%h	小时 (01-12)
%I	小时 (01-12)
%i	分钟，数值(00-59)
%j	年的天 (001-366)
%k	小时 (0-23)
%l	小时 (1-12)
%M	月名
%m	月，数值(00-12)
%p	AM 或 PM
%r	时间，12-小时（hh:mm:ss AM 或 PM）
%S	秒(00-59)
%s	秒(00-59)
%T	时间, 24-小时 (hh:mm:ss)
%U	周 (00-53) 星期日是一周的第一天
%u	周 (00-53) 星期一是一周的第一天
%V	周 (01-53) 星期日是一周的第一天，与 %X 使用
%v	周 (01-53) 星期一是一周的第一天，与 %x 使用
%W	星期名
%w	周的天 （0=星期日, 6=星期六）
%X	年，其中的星期日是周的第一天，4 位，与 %V 使用
%x	年，其中的星期一是周的第一天，4 位，与 %v 使用
%Y	年，4 位
%y	年，2 位
~~~
### Mysql优化
- 查看最大连接数
~~~
show VARIABLES like 'MAX_CONNECTIONS'
~~~
- 查看最大连接数据
~~~
show global status like 'MAX_USED_CONNECTIONS';
~~~
- 结论
~~~
比较理想的设置是：
Max_used_connections / max_connections * 100% ≈ 85%
最大连接数占上限连接数的85%左右，如果发现比例在10%以下，MySQL服务器连接上限就设置得过高了。
~~~
- 通过threads-connections查看当前分配的线程数量
~~~
show status like '%thread%';
~~~
