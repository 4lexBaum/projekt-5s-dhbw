var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var ManufacturingDataSchema   = new Schema({
    customerNumber: String,
    materialNumber: String,
    orderNumber: String,
    timeStamp: String,
    machineData: Array,
    analysisData: Object
});

module.exports = mongoose.model('ManufacturingData', ManufacturingDataSchema, 'manufacturingData');