FROM node:argon

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY nodeapp/package.json /usr/src/app/
RUN npm install

COPY nodeapp/. /usr/src/app
EXPOSE 3000
CMD [ "npm", "start" ]
