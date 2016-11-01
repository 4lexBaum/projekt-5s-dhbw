FROM ubuntu:16.04
MAINTAINER Fabian Otto

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# install activemq
ADD http://www.apache.org/dyn/closer.cgi?filename=/activemq/5.14.1/apache-activemq-5.14.1-bin.tar.gz&action=download /activemq.tgz
RUN tar -xzf /activemq.tgz && mv /apache-activemq-5.14.1 /activemq

# expose port
EXPOSE 8161
EXPOSE 61616
EXPOSE 5672
EXPOSE 61613
EXPOSE 1883
EXPOSE 61614

ENTRYPOINT /entrypoint.sh
