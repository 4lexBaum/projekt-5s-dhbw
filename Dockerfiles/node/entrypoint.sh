#!/bin/bash

# SLEEP 30

cd /usr/src/app

node testData.js &
npm run start

/usr/bin/tail -f /dev/null
