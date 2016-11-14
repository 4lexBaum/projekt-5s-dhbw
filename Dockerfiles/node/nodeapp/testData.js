"use strict";

var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var kafka = require('kafka-node');
var Consumer = kafka.Consumer;
var Client = kafka.Client;
var Offset = kafka.Offset;

var client = new Client("kafka");
var topics = [{
    topic: "manufacturingData"
}, {
    topic: "erpData"
}, {
    topic: "prodData"
}, {
    topic: "QualityCustomer"
}, {
    topic: "QualityMaterial"
}, {
    topic: "MaterialMillingSpeed"
}, {
    topic: "MaterialDuration"
}, {
    topic: "CustomerOrderAmount"
}, {
    topic: "MaterialDrillingHeat"
}, {
    topic: "MaterialDrillingSpeed"
}, {
    topic: "MaterialMillingHeat"
}, {
    topic: "MaterialProducedAmount"
}, {
    topic: "QualityCustomerPercentage"
}, {
    topic: "QualityMaterialPercentage"
}];
var options = {
    autoCommit: false,
    fetchMaxWaitMs: 1000,
    fetchMaxBytes: 1024 * 1024,
    offset: 0
};

setTimeout(function () {

    var consumer = new Consumer(client, topics, options);
    var offset = new Offset(client);

    app.get('/', function (req, res) {
        res.sendfile('./public/index.html');
    });

    io.on('connection', function (socket) {
        console.log('a user connected');
        socket.on('disconnect', function () {
            console.log('user disconnected');
        });

        consumer.on('message', function (message) {
            switch (message.topic) {
                case "erpData":
                    socket.emit("erp", message.value);
                    return;
                case "prodData":
                    if (message.value.itemName == "MILLING_SPEED") {
                        socket.emit("MILLING_SPEED", (message.value.value / 1000).toFixed(1));
                    } else if (message.value.itemName == "MILLING_HEAT") {
                        socket.emit("MILLING_HEAT", Math.floor(message.value.value));
                    } else if (message.value.itemName == "DRILLING_SPEED") {
                        socket.emit("DRILLING_SPEED", (message.value.value / 1000).toFixed(1));
                    } else if (message.value.itemName == "DRILLING_HEAT") {
                        socket.emit("DRILLING_HEAT", Math.floor(message.value.value));
                    } else if (message.value.itemName.startsWith("L")) {
                        socket.emit("LIGHT_BARRIER", message.value.itemName);
                    } else {
                        socket.emit("machine", message.value);
                    }
                    return;
                default:
                    socket.emit(message.topic, message.value);
            }
        });
    });

    http.listen(3001, function () {
        console.log('listening on *:3001');
    });

}, 80000);
