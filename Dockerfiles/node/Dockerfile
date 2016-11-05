FROM node:argon

# copy entrypoint script on docker and set permissions
COPY entrypoint.sh /
RUN chmod u+x /entrypoint.sh

#include package.json
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY nodeapp/package.json /usr/src/app/
RUN npm install

#build node
COPY nodeapp/. /usr/src/app

# expose port for web UI
EXPOSE 3000

CMD [ "npm", "start" ]
