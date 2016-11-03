FROM ubuntu:16.04
MAINTAINER Fabian Otto

# install java
RUN apt-get update
RUN apt-get install -y openjdk-8-jre

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# copy spark jar on docker and set permissions
RUN mkdir -p /jarFiles
COPY SparkTest.jar /jarFiles
RUN chmod u+x /jarFiles/SparkTest.jar


# install spark
ADD http://www-eu.apache.org/dist/spark/spark-2.0.1/spark-2.0.1-bin-hadoop2.7.tgz /spark.tgz
RUN tar -xzf /spark.tgz && mv /spark-2.0.1-bin-hadoop2.7 /spark

# expose spark webui and master port
EXPOSE 8080
EXPOSE 7077

ENTRYPOINT /entrypoint.sh
