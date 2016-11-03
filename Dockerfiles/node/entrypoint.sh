#!/bin/bash

npm run build
npm run server

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
