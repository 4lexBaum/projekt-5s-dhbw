FROM ubuntu:16.04
MAINTAINER Fabian Otto

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy entrypoint script on docker and set permissions 
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

WORKDIR /jarfile

# copy simulation on docker and set permissions 
COPY TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar /jarfile
RUN chmod u+x TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar

# create directory for the temporary file
RUN mkdir -p /jarfile/temp

ENTRYPOINT /entrypoint.sh
