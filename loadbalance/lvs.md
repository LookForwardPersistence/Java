### LVS linux虚拟服务器
Linux visual server 国内
~~~
目标：高性能高可用的服务器
      可伸缩性（scalable）、可靠性（Reliability）、可管理性（Managebility）
~~~
### osi 7层协议模型（网络7层）
nginx工作在网络层
- 应用层
- 表示层
- 会话层
- 传输层
- 网络层
- 数据链路层
- 物理层

### 网络5层 业绩非官方协议模型
- 应用层
~~~
应用层 表示层 会话层
~~~
- 传输层
- 网络层
- 数据链路层
- 物理层
~~~
tpc/ip 四层结构
应用层 传输层 网络层 网络接口层（数据链路层和物理层）
lvs工作在四层协议模型
~~~
### IP虚拟服务器软件（IPVS）的三种负载均衡技术
- Visual Server via Direct Rouding(VS/DR)
~~~
VS/DR通过改写请求报文的mac地址，将请求发送到真实服务器，而正式服务器将响应直接返回给用户。同SV/TUN技术一样，VS/DR技术可以极大提高集群系统的伸缩性。
这种方法没有ip隧道的开销，对集群的真实服务器也没有必须支持ip隧道协议的要求，但是要求调度器与真实服务器都需要有一块网卡连在同一物理网段上。
~~~
- Visual Server via Network Address Translation(VS/NAT)
~~~
通过网络地址转换，调度器重写请求报文的目标地址，根据预设的调度算法，将请求分派给后端真实服务器。真实服务服务器的响应报文经过调度器时，报文的源地址被重写
再返回给客户，完成整个负载调度的过程。
~~~
- Visual Server via IP Tunneling(VS/TUN)
~~~
采用NAT技术时，请求和响应的报文都必须经过调度器地址重写，当用户请求越来越多时候，调度器的处理能力将成为瓶颈。为解决这个问题，调用器把请求报文通过
ip隧道直接发到真实服务器，而真实服务将响应直接返回给用户，所以调度器只处理请求报文。由于网路应答比请求大许多，采用SV/TUN技术后，集群系统吞吐量可以提高10倍
~~~

### IPVS 8中负载均衡调用算法
- 轮叫 （Round Robin）
- 加权轮叫（Weighted Round Robin）
- 最少链接（Least Connections）
- 加权最少链接（Least Connections）
- 基于局部性的最少链接（Locality-Base Least Connections）
- 带赋值的基于局部性的最少链接（Locality-Base Least Connection with Replication）
- 目标地址散列（Destination Hashing）
- 源地址散列（Source Hashing）

