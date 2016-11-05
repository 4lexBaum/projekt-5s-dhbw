#!/bin/bash

# start simulation
java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://activemq:61616 -d 15000 -kafka kafka:9092 -o /temp -topic prodData &

# start backend application (consumer)
java -jar Backend-Application-0.0.1-SNAPSHOT.jar

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
