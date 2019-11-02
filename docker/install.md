~~~
在RHEL7上安装Docker CE
服务器版本: Red Hat Enterprise Linux Server release 7.5

在安装Docker CE的时候提示 WARNING: rhel is now only supported by Docker EE Check https://store.docker.com for information on Docker EE

解决办法如下:

Install yum-utils. 执行 yum install -y yum-utils
Install epel-release. 执行 wget http://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm和 rpm -ivh epel-release-latest-7.noarch.rpm
Add Docker CE to yum repos. yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
安装container-selinux,因为docker-ce依赖于container-selinux, yum install -y http://mirror.centos.org/centos/7/extras/x86_64/Packages/container-selinux-2.33-1.git86f33cd.el7.noarch.rpm, 如果不行尝试执行 yum -y --enablerepo=rhui-REGION-rhel-server-extras install container-selinux
安装Docker CE yum install -y docker-ce
查看是否安装成功 docker --version
重启Docker服务并加入开机启动 systemctl restart docker 和 systemctl enable docker
安装nginx容器测试下 docker run --name webserver -d -p 9090:80 nginx
安装完成后，需要把当前用户添加到用户组才能不以root身份运行 sudo usermod -aG docker ec2-user

参考地址
https://getstart.blog/2018/03/24/docker-ce-installation-on-red-hat-7/

https://www.itzgeek.com/how-tos/linux/centos-how-tos/installing-docker-on-centos-7-rhel-7-fedora-21.html

https://nickjanetakis.com/blog/docker-tip-39-installing-docker-ce-on-redhat-rhel-7x

https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-centos-7

container-selinux问题的解决方案, 参考nderzhak的回答

https://www.xuboso.com/blogs/48/how-to-install-docker-ce-on-rhel7

脚本安装Docker-CE

Docker系列教程
~~~
