#!/bin/bash

echo "advertised.listeners=PLAINTEXT://kafka:9092" >> /kafka/config/server.properties
#echo "advertised.host.name=0.0.0.0" >> /kafka/config/server.properties

# start kafka
/kafka/bin/zookeeper-server-start.sh /kafka/config/zookeeper.properties &
# wait to process
SLEEP 2
/kafka/bin/kafka-server-start.sh /kafka/config/server.properties &
# SLEEP 2

# create topics
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic dummyTest
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafkatest
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic prodData
#/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic manufacutringData
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic erpData
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic QualityCustomer
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic QualityMaterial
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic MaterialMillingSpeed
/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic MaterialDuration

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
