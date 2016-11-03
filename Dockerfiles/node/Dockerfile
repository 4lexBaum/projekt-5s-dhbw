FROM ubuntu:16.04
MAINTAINER Fabian Otto

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# copy js programm on docker and set permissions
RUN mkdir -p /SetupFiles
COPY that_js_file /SetupFiles
RUN chmod u+x /jsFiles/that_js_file

# copy package.json on docker
COPY package.json /SetupFiles

# install nodejs and npm
RUN apt-get update; apt-get install nodejs;
apt-get install nodejs-legacy; apt-get install npm

RUN cd /SetupFiles; npm install

# expose nodejs port
EXPOSE 8080

ENTRYPOINT /entrypoint.sh
