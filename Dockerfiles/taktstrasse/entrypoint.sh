#!/bin/bash

# start simulation
java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://activemq:61616 -d 10000 -kafka kafka:9092 -o /tmp -topic prodData

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
