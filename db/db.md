- mysql
~~~
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://ip:3306/name?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=r
spring.datasource.password=r
spring.jpa.database=mysql
spring.jpa.show-sql= true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
~~~
- oracle
~~~
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@ ip:1521:dbname
spring.datasource.username=r
spring.datasource.password=r
spring.jpa.database=oracle
spring.jpa.show-sql= true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.Oracle10gDialect
~~~
