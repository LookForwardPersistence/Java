### maven conf/seeting.xml配置
~~~
指定本地仓
  <localRepository>本地仓路径</localRepository>

指向阿里镜像
  <mirror>
          <id>alimaven</id>
          <name>aliyun maven</name>
          <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
          <mirrorOf>central</mirrorOf>        
    </mirror>
~~~
### /ect/profile环境变量配置
~~~
export MAVEN_HOME=maven路径
export PATH=${PATH}:${MAVEN_HOME}\bin
~~~
### 使/ect/profile配置生效
~~~
source /ect/profile
~~~

### 本地jar导入maven

~~~
mvn install:install-file -DgroupId=包名  -DartifactId=artifactId -Dversion=版本 -Dpackaging=jar -Dfile=jar包文件
~~~

### 导出所有依赖jar
~~~
mvn dependency:copy-dependencies -DoutputDirectory=lib   -DincludeScope=compile
~~~
