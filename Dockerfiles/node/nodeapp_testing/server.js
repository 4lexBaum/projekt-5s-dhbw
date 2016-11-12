"use strict";

var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var kafka = require('kafka-node');
var MongoClient = require('mongodb').MongoClient;
var Consumer = kafka.Consumer;
var Client = kafka.Client;
var Offset = kafka.Offset;

var url = 'mongodb://mongodb/taktstrasse';


setTimeout(function () {

    var client = new Client("kafka");
    var topics = [{
        topic: "kafkatest"
    }, {
        topic: "erpData"
    }, {
        topic: "prodData"
    },{
        topic: "QualityCustomer"
    },{
        topic: "QualityMaterial"
    },{
        topic: "MaterialMillingSpeed"
    },{
        topic: "MaterialDuration"
    }
  ];
    var options = {
        autoCommit: false,
        fetchMaxWaitMs: 1000,
        fetchMaxBytes: 1024 * 1024,
        offset: 0
    };
    var consumer = new Consumer(client, topics, options);
    var offset = new Offset(client);

    app.get('/', function (req, res) {
        res.sendfile('./index.html');
    });

    io.on('connection', function (socket) {
        console.log('a user connected');
        socket.on('disconnect', function () {
            console.log('user disconnected');
        });
        consumer.on('message', function (message) {
            socket.emit(message.topic, message.value);
        });

    });

    http.listen(3000, function () {
        console.log('listening on *:3000');
    });


}, 30000);
