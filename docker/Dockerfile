FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD bibe-crm-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Xms80m", "-Xmx120m","-jar","app.jar"]
EXPOSE 8083

#docker run -p 3302:3306 --name mysql  -v /usr/local/docker/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=123456  -d mysql:5.7.26