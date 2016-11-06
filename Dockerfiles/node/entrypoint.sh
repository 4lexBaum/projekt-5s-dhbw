#!/bin/bash

# SLEEP 30

cd /usr/src/app

npm start &

/usr/bin/tail -f /dev/null
