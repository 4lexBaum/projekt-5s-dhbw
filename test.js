var beautify = require('js-beautify').js_beautify,
    fs = require('fs');
 
fs.readFile('foo.js', 'utf8', function (err, data) {
    if (err) {
        throw err;
    }
  //  console.log(beautify(data, { indent_size: 2 }));
	var content = beautify(data, {indent_size:2});
	fs.writeFileSync('result.js',content, 'utf8');
});
