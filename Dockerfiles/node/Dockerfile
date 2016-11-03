FROM node:argon

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY ../../Frontend/package.json /usr/src/app/
RUN npm install
COPY ../../Frontend/. /usr/src/app
EXPOSE 3000
CMD [ "npm", "start" ]
