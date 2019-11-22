- 安装前置信息
~~~
yum install -y epel-release
yum install -y conntrack ntpdate ntp ipvsadm ipset jq iptables curl sysstat libseccomp wget
/usr/sbin/modprobe ip_vs
~~~
- 关闭防火墙
~~~
systemctl stop firewalld
systemctl disable firewalld
iptables -F && iptables -X && iptables -F -t nat && iptables -X -t nat
iptables -P FORWARD ACCEPT
~~~
- 关闭swap分区
~~~
swapoff -a
sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
~~~
- 指定阿里云镜像安装
~~~
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
~~~
