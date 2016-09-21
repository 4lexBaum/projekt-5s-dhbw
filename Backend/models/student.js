var mongoose = require('mongoose');

var studentSchema = mongoose.Schema({

    name:String,
    alter: Number,
    jahrgang: Number

});

var Student = mongoose.model('wwi14sea', studentSchema);

module.exports = Student;