
- methods references are not surported at this language level
~~~
往往是maven没有指定jdk版本引起
解决：pom.xml文件指定jdk版本
 <plugins>
   <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <version>3.8.1</version>
       <!--指定java 8-->
       <configuration>
           <source>8</source>
           <target>8</target>
       </configuration>
   </plugin>
   </plugins>

~~~
