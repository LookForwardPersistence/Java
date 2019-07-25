#!/bin/sh
export JAVA_OPTS="-Dtomcat.server.port=8006" 
export CATALINA_OPTS="-Dtomcat.server.port=8006" 
export CATALINA_BASE="/usr/local/jenkins/tomcat/apps/dawn" 
export CATALINA_PID="/usr/local/jenkins/tomcat/apps/dawn/tomcat-dawn.pid" 
/usr/local/jenkins/tomcat/apache-tomcat-8.5.42/bin/catalina.sh stop 0 -force 

