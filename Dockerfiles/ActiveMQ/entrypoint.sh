#!/bin/bash

# start activemq
/activemq/bin/activemq start &

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
