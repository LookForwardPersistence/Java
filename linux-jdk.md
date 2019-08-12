### 收藏
- https://dolyw.com/
- https://my.oschina.net/xianggao

### linux下载jdk8
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u121-b13/e9e7ea248e2c4826b92b3f075a80e441/jdk-8u121-linux-i586.tar.gz
- vim /etc/profile
- export JAVA_HOME=/usr/local/jdk export PATH=$PATH:$JAVA_HOME/bin
- source /etc/profile


### linux 卸载 jdk
- 查看jdk版本 rpm -qa|grep jdk
- 卸载  rpm -e --nodeps jdk版本（jdk-1.7.0_80-fcs.x86_64）
