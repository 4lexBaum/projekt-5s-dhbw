#!/bin/bash

# start spark
/spark/sbin/start-master.sh
# wait to process
sleep 5
/spark/sbin/start-slave.sh spark://spark:7077

# set spark memory
 cp /spark/conf/spark-defaults.conf.template /spark/conf/spark-defaults.conf
 echo "spark.driver.memory5g" >> /spark/conf/spark-defaults.conf
sleep 10

#submit job to cluster
./spark/bin/spark-submit \
  --class SparkTest.App \
  --master spark://spark:7077 \
  /jarFiles/SparkTest.jar

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
