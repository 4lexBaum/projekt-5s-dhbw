FROM  ubuntu:latest
MAINTAINER Philip Riecks

# Import MongoDB public GPG key AND create a MongoDB list file
RUN \
 apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10 && \
 echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' > /etc/apt/sources.list.d/mongodb.list && \
 apt-get update && \
 apt-get install -y mongodb-org && \
 rm -rf /var/lib/apt/lists/*

# Creating the MongoDB data directory
RUN mkdir -p /data/db

# Expose port 27017 from the container to the host
EXPOSE 27017

# Define mountable directorys
VOLUME ["/data/db"]

# Set entry-point application
ENTRYPOINT ["/usr/bin/mongod"]
