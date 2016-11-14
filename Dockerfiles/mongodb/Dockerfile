FROM ubuntu:latest
MAINTAINER Philip Riecks

# Import MongoDB public GPG key AND create a MongoDB list file
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
RUN echo "deb http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.2 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list

# Install MongoDB
RUN apt-get update && apt-get install -y mongodb-org

# Creating the MongoDB data directory
RUN mkdir -p /data/db

# Copy some existing data to ensure a reliable analysis
COPY historicData.json /data

# Copy entrypoint script and give privileges to execute
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# Expose ports
EXPOSE 27017

ENTRYPOINT /entrypoint.sh
