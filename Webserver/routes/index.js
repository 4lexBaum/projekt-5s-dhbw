var express = require('express');
var router = express.Router();
var assert = require('assert');
var manufacturingData = require('../models/manufacturingData');

router.get('/',function(req,res){

    res.render('index');

});

router.get('/Datenstruktur',function(req,res){

    manufacturingData.find({}).sort({_id:-1}).limit(1).exec(function(err, doc){
        assert.equal(err, null);
        res.render('datenstruktur', {params: {exampleObject: doc[0]}});
        //res.render('datenstruktur', {params: {exampleObject: JSON.stringify(doc[0], null, 10)}});
    });

});

module.exports = router;