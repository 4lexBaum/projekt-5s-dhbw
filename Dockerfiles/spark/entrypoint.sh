#!/bin/bash

# start spark
/spark/sbin/start-master.sh
# wait to process
sleep 5
/spark/sbin/start-slave.sh spark://0.0.0.0:7077

# set spark memory
# cp /spark/conf/spark-defaults.conf.template /spark/conf/spark-defaults.conf
# echo "spark.driver.memory5g" >> /spark/conf/spark-defaults.conf

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
