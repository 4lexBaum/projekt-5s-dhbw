var express = require('express');
var kafka = require('kafka-node');
var path = require('path');
var bodyParser = require('body-parser')
var MongoClient = require('mongodb').MongoClient;
var Consumer = kafka.Consumer;
var Client = kafka.Client;
var Offset = kafka.Offset;

var app = express();

var http = require('http').Server(app);
var io = require('socket.io')(http);

app.use('/', express.static(path.join(__dirname, '../../public')));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

var topics = [{
    topic: "manufacturingData"
}, {
    topic: "erpData"
}, {
    topic: "prodData"
}, {
    topic: "CustomerQuality"
}, {
    topic: "MaterialQuality"
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

    var client = new Client("kafka");
    var consumer = new Consumer(client, topics, options);
    var offset = new Offset(client);

    app.get('/', function (req, res) {
        res.sendfile('../../public/index.html');
    });

    io.on('connection', function (socket) {
        console.log('a user connected');
        socket.on('disconnect', function () {
            console.log('user disconnected');
        });

        consumer.on('message', function (message) {
            var msgVal = JSON.parse(message.value)
            switch (message.topic) {
                case "erpData":
                    socket.emit("erp", msgVal);
                    break;
                case "prodData":
                    if (msgVal.itemName == "MILLING_SPEED") {
                        socket.emit("MILLING_SPEED", (msgVal.value / 1000).toFixed(1));
                    } else if (msgVal.itemName == "MILLING_HEAT") {
                        socket.emit("MILLING_HEAT", Math.floor(msgVal.value));
                    } else if (msgVal.itemName == "DRILLING_SPEED") {
                        socket.emit("DRILLING_SPEED", (msgVal.value / 1000).toFixed(1));
                    } else if (msgVal.itemName == "DRILLING_HEAT") {
                        socket.emit("DRILLING_HEAT", Math.floor(msgVal.value));
                    } else if (msgVal.itemName.startsWith("L")) {
                        socket.emit("LIGHT_BARRIER", msgVal.itemName);
                    }
                    socket.emit("machine", msgVal);

                    break;
                default:
                    socket.emit(message.topic, msgVal);
            }
        });
    });

    http.listen(3000, function () {
        console.log('listening on *:3000');
    });

}, 70000);
