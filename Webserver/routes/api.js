/**
 * Created by Philip on 09.09.16.
 */
var manufacturingData = require('../models/manufacturingData.js');
var express = require('express');
var router = express.Router();
var assert = require('assert');

router.get('/ManufacturingData/Latest/:count',function(req,res){

    manufacturingData.find({}).sort({_id:-1}).limit(parseInt(req.params.count)).exec(function(err, docs){
        assert.equal(err, null);
        //res.json(docs);
        res.json(docs);

    });
});
module.exports = router;