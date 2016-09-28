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
Kafka starten: docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=192.168.99.100 --env ADVERTISED_PORT=9092 spotify/kafka
ActiveMq starten: docker run --name activemq -P webcenter/activemq

### Simulation starten
Windows: java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://192.168.99.100:32768 -d 1000 -kafka 192.168.99.100:9092 -o <path> -topic prodData
sonst:   java -jar TaktstrasseOpcServer-0.0.1-SNAPSHOT.jar -amqp tcp://localhost:32768 -d 1000 -kafka localhost:9092 -o <path> -topic prodData