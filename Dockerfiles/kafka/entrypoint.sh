#!/bin/bash

echo "advertised.listeners=PLAINTEXT://kafka:9092" >> /kafka/config/server.properties
#echo "advertised.host.name=0.0.0.0" >> /kafka/config/server.properties

# start kafka
/kafka/bin/zookeeper-server-start.sh /kafka/config/zookeeper.properties &
# wait to process
SLEEP 2
/kafka/bin/kafka-server-start.sh /kafka/config/server.properties &
# SLEEP 2

# create topic
/kafka/bin/kafka-topics.sh --create --zookeeper kafka:2181 --replication-factor 1 --partitions 1 --topic prodData &
#/kafka/bin/kafka-topics.sh --list --zookeeper kafka:2181 &

/kafka/bin/kafka-topics.sh --create --zookeeper kafka:2181 --replication-factor 1 --partitions 1 --topic kafkatest &
#/kafka/bin/kafka-topics.sh --list --zookeeper kafka:2181 &

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
