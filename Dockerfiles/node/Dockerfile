FROM node:argon

MAINTAINER Philip Riecks

# Copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# Include package.json
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY nodeapp/package.json /usr/src/app/
RUN npm install

# Copy node application to docker directory
COPY nodeapp/. /usr/src/app

# Expose port for web UI
EXPOSE 3000

# Set entrypoint on start in package.json
CMD [ "npm", "start" ]
