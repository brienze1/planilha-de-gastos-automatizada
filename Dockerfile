FROM adoptopenjdk/openjdk14:alpine

MAINTAINER Luis Brienze <lfbrienze@gmail.com>

ENV ENV_NAME local
ENV BOOTAPP_JAVA_OPTS -Xms256m -Xmx512m
ENV LOG_PATH "/var/log/casa"
ENV BOOTAPP_USR="root" BOOTAPP_GROUP="root" BOOTAPP_PATH="/app.jar"
ENV SERVER_PORT 0

EXPOSE $SERVER_PORT

RUN apk update && apk add bash

COPY wrapper.sh /wrapper.sh

RUN chmod 555 /wrapper.sh

USER root
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} $BOOTAPP_PATH
RUN chmod 555 $BOOTAPP_PATH && \
            touch $BOOTAPP_PATH
RUN mkdir $LOG_PATH            
RUN chmod 777 $LOG_PATH && touch $LOG_PATH 

USER $BOOTAPP_USR

# RUN cat ./newrelic/newrelic.yml | sed -e 's/app_name:.*/app_name: docker_boot/' > ./newrelic/newrelic.yml
 
ENTRYPOINT ["/wrapper.sh"]