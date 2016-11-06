FROM ubuntu:16.04
MAINTAINER Fabian Otto

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# install activemq
ADD https://archive.apache.org/dist/activemq/5.11.3/apache-activemq-5.11.3-bin.tar.gz activemq.tgz
RUN tar -xzf /activemq.tgz && mv /apache-activemq-5.11.3 /activemq

# expose port
EXPOSE 8161
EXPOSE 61616
EXPOSE 5672
EXPOSE 61613
EXPOSE 1883
EXPOSE 61614

# Expose some folders
VOLUME ["/activemq/data/activemq"]
VOLUME ["/activemq/var/log/activemq"]
VOLUME ["/activemq/opt/activemq/conf"]

ENTRYPOINT /entrypoint.sh
