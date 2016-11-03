#!/bin/bash

# start zeppelin
/zeppelin/bin/zeppelin-daemon.sh start &

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
