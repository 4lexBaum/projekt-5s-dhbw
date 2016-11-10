# Start mongoDB Server
/usr/bin/mongod &

# Import the existing objects to mongoDB
/usr/bin/mongoimport -d oip_taktstrasse -c manufacturingData /data/historicData.json

# run endless loop to keep alive
/usr/bin/tail -f /dev/null
