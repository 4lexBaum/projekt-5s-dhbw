# projekt-5s-dhbw
Gruppenprojekt 5. Semester

[![Build Status](https://travis-ci.org/4lexBaum/projekt-5s-dhbw.svg?branch=master)](https://travis-ci.org/4lexBaum/projekt-5s-dhbw)

## Anwendung starten

### Docker installieren
* [Docker](https://docs.docker.com/engine/installation/) (normale Installation auf Linux)
* [Docker Toolbox](https://www.docker.com/products/docker-toolbox) (Installation auf Windows)

### Docker Images herunterladen
* docker pull spotify/docker ([Dokumentation](https://hub.docker.com/r/spotify/kafka/))
* docker pull webcenter/activemq ([Dokumentation](https://hub.docker.com/r/webcenter/activemq/))

### Docker Container ausführen
* Kafka starten (Windows): docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=192.168.99.100 --env ADVERTISED_PORT=9092 spotify/kafka
* Kafka starten (sonst): docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=127.0.0.1 --env ADVERTISED_PORT=9092 spotify/kafka
* ActiveMq starten: docker run --name activemq -P webcenter/activemq

### Simulation starten
* Windows: 
```shell
java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://192.168.99.100:32768 -d 1000 -kafka 192.168.99.100:9092 -o <path> -topic prodData
```
* sonst:   
```shell
java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://localhost:32768 -d 1000 -kafka localhost:9092 -o <path> -topic prodData
``

### MongoDB starten und benutzen
* MongoDB Server starten:
```shell
mongod --dbpath /Pfad/zum/Data/Ordner (e.g. /Users/Philip/Database/Data)
```
* Mit MongoDB Server über die MongoShell verbinden:
```shell
mongo
```
* Alle Datenbanken auflisten: 
```shell
> show dbs
```
* Spezielle Datenbank verwenden: 
```shell
> use db_name
```
* Alle Collections einer Datenbank anzeigen: 
```shell
> show collections
```
* Eine Collection auf einer ausgewählten Datenbank durchsuchen: 
```shell
> db.collection_name.find()
```
