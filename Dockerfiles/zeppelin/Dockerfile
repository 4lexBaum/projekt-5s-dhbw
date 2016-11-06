FROM ubuntu:16.04
MAINTAINER Fabian Otto

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# install zeppelin
ADD http://www-eu.apache.org/dist/zeppelin/zeppelin-0.6.2/zeppelin-0.6.2.tgz /zeppelin.tgz
RUN tar -xzf /zeppelin.tgz && mv /zeppelin-0.6.2 /zeppelin
COPY interpreter.json /zeppelin/conf/interpreter.json

# expose zeppelin ports
EXPOSE 8080

# save notebooks
VOLUME /zeppelin/notebooks

ENTRYPOINT /entrypoint.sh
