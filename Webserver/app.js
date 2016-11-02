/**
 * Created by Philip on 21.09.16.
 */
var server = require("./bin/www.js");
var mongoose = require("mongoose");

mongoose.connect('mongodb://localhost/oip_taktstrasse');

server.start();