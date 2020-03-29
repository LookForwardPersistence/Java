- 静态路由配置
~~~
  cloud:
    gateway:
     routes:
       - id: study
         uri: http://www.baidu.com
         predicates:
            - Path=/index
~~~

- 动态路由配置
~~~
  cloud:
    gateway:
     discovery:
       locator:
         enabled: true
~~~
