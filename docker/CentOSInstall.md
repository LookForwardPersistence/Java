# Harbor 安装

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
### docker ce 安装（CentOS）
- 指定阿里云镜像安装
~~~
curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
~~~
- 如遇selinux版本低的问题
~~~
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum install epel-release
yum install container-selinux
~~~
### docker-compose安装
~~~
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
#测试
docker-compose --version
~~~


### 安装cfssl工具
~~~
sudo mkdir -p /opt/k8s/work/cert && cd /opt/k8s/work
wget https://pkg.cfssl.org/R1.2/cfssl_linux-amd64
mv cfssl_linux-amd64 /opt/k8s/bin/cfssl

wget https://pkg.cfssl.org/R1.2/cfssljson_linux-amd64
mv cfssljson_linux-amd64 /opt/k8s/bin/cfssljson

wget https://pkg.cfssl.org/R1.2/cfssl-certinfo_linux-amd64
mv cfssl-certinfo_linux-amd64 /opt/k8s/bin/cfssl-certinfo

chmod +x /opt/k8s/bin/*
export PATH=/opt/k8s/bin:$PATH
~~~
### 创建 harbor nginx 服务器使用的 x509 证书
~~~
cat > harbor-csr.json <<EOF
{
  "CN": "harbor",
  "hosts": [
    "127.0.0.1",
    "ip或域名"
  ],
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "SZ",
      "L": "SZ",
      "O": "k8s",
      "OU": "EA"
    }
  ]
}
EOF
~~~
- 生成证书及私钥
~~~
cfssl gencert -ca=/etc/kubernetes/cert/ca.pem \
  -ca-key=/etc/kubernetes/cert/ca-key.pem \
  -config=/etc/kubernetes/cert/ca-config.json \
  -profile=kubernetes harbor-csr.json | cfssljson -bare harbor

sudo mkdir -p /etc/harbor/ssl
sudo mv harbor*.pem /etc/harbor/ssl
rm harbor.csr  harbor-csr.json

~~~
