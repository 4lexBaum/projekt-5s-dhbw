FROM ubuntu:16.04
MAINTAINER Fabian Otto

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# copy simulation on docker and set permissions
COPY TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar /
RUN chmod u+x /TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar

# copy backend application (consumer)
COPY Backend-Application-0.0.1-SNAPSHOT.jar /
RUN chmod u+x /Backend-Application-0.0.1-SNAPSHOT.jar

# create directory for the temporary file
RUN mkdir -p /spektralData

ENTRYPOINT /entrypoint.sh
