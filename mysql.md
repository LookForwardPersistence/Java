### 批量drop table（mysql）
~~~
SELECT concat('DROP TABLE IF EXISTS `', table_name, '`;')
FROM information_schema.tables
WHERE Table_name LIKE '%user_20%';
~~~
