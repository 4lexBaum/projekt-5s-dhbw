FROM node:argon

MAINTAINER Philip Riecks

# Copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

# Include package.json
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy the node app to the docker directory
COPY nodeapp3/. /usr/src/app

# Install all dependencies and build the project
RUN npm install

# Expose port for web UI
EXPOSE 3000

# Set entrypoint on start in package.json
CMD ["npm","start"]
