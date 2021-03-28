FROM adoptopenjdk/openjdk14:alpine

MAINTAINER Luis Brienze <lfbrienze@gmail.com>

ENV ENV_NAME local
ENV BOOTAPP_JAVA_OPTS -Xms256m -Xmx512m
ENV LOG_PATH "/var/log/casa"
ENV SERVER_PORT 8080

EXPOSE $SERVER_PORT

RUN apk update && apk add bash

COPY target/*.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=${ENV_NAME}","-jar","/app.jar", "--server.port=${SERVER_PORT}"]
