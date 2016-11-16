FROM node:latest

MAINTAINER Philip Riecks

# Include package.json
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy the node app to the docker directory
COPY nodeapp/. /usr/src/app

# Install all dependencies and build the project
RUN npm install

# Expose port for web UI
EXPOSE 3000

# Set entrypoint on start in package.json
CMD ["npm","start"]
