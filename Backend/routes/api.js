/**
 * Created by Philip on 09.09.16.
 */
var student = require('../models/student');
var express = require('express');
var router = express.Router();

router.get('/student',function(req,res){

    student.find(function (err, data) {

        if(err) res.send(err);
        res.json(data);

    });

});
router.get('/student/:name', function (req, res, next) {
    student.findOne({"name":req.params.name}, function(err, data){
        if(err) res.send("no student found");
        res.json(data);
    });
});

module.exports = router;