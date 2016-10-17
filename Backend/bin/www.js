var express  = require('express');
var bodyParser = require('body-parser');

var index = require('../routes/index');
var api = require('../routes/api');

var app = express();
app.set('port', process.env.PORT || 3000);
app.set('views','./views');
app.set('view engine','pug');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended:true

}))

app.use('/',index);
app.use('/API',api);



app.use(function(req,res){

    res.type('text/plain');
    res.status(404);
    res.send('404 - Not found NEW');

});

app.use(function(err,req,res,next){


    console.log(err);
    res.type('text/plain');
    res.status(500);
    res.send('500 - Internal server error');

});

exports.start = function(){


    app.listen(app.get('port'), function(){

        console.log(" Express ready on http://127.0.0.1:" + app.get('port'));

    });
};


