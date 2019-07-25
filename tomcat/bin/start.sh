#!/bin/sh
export LD_LIBRARY_PATH="/usr/local/jenkins/tomcat/apache-tomcat-8.5.42/bin/native" 

export CATALINA_OPTS="-Dtomcat.server.port=8006 -Dtomcat.http.port=8080 -Dtomcat.ajp.port=8010 -Dtomcat.redirect.port=8443 -Dtomcat.instance.name=dawn -Xms1024m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError" 
export CATALINA_OUT="/usr/local/jenkins/tomcat/apps/dawn/logs/catalina.$(date +'%Y-%m-%d').out" 
export CATALINA_PID="/usr/local/jenkins/tomcat/apps/dawn/tomcat-dawn.pid" 
export CATALINA_BASE="/usr/local/jenkins/tomcat/apps/dawn" 

rm -rf /usr/local/jenkins/tomcat/apps/dawn/work 
if [ "$1" = "deploy" ];then
echo "Deploy War Starting!"
unzip /usr/local/jenkins/tomcat/apps/dawn/mywar/dawn-web.war -d /usr/local/jenkins/tomcat/apps/dawn/webapps/dawn
else
echo "Skip Deploy war"
fi
/usr/local/jenkins/tomcat/apache-tomcat-7.0.68/bin/catalina.sh start 
exit $?
