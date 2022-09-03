FROM ubuntu:latest

RUN apt-get -y update && apt-get -y upgrade


RUN apt-get -y install openjdk-8-jdk wget
RUN apt-get -y install vim
RUN apt-get -y install maven


RUN mkdir /usr/local/tomcat
RUN wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.82/bin/apache-tomcat-8.5.82.tar.gz -O /tmp/tomcat.tar.gz
RUN cd /tmp && tar xvfz tomcat.tar.gz
RUN cp -Rv /tmp/apache-tomcat-8.5.82/* /usr/local/tomcat/


ADD FitPass.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD /usr/local/tomcat/bin/catalina.sh run
WORKDIR /usr/local/tomcat