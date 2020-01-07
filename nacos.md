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
