#!/bin/bash

# start simulation
java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://localhost:32768 -d 1000 -kafka localhost:9092 -o /tmp -topic prodData & 
# wait for process
sleep 2 

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
