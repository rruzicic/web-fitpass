FROM tomcat:8.0-alpine

ADD FitPass /usr/local/tomcat/webapps/FitPass

EXPOSE 8080

CMD ["catalina.sh", "run"]