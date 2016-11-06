proback.js - dependency-free very minimal helper for APIs with Promise/callback dual design

[![NPM](https://nodei.co/npm/assign.js.png)](https://nodei.co/npm/proback.js/)

[![js-standard-style](https://cdn.rawgit.com/feross/standard/master/badge.svg)](https://github.com/feross/standard)

========

[proback.js](https://github.com/imrefazekas/proback.js) is an extremely tiny helper library to help the creation of APIs where functions are aimed to enable Promise-based and callback-based usage as well.

[Usage](#usage)
[Rules](#rules)


## Usage

Command line:

	npm install proback.js --save

In JS code:

```javascript
	var Proback = require('proback.js');
	...
	// your service. If callback is present, works as expected, If not, promise will be provided.
	function yourService( data, callback ){
		new Promise( function (resolve, reject) {
			//some callback is needed?
			object.fnCallWithCallback( data, Proback.handler( null, resolve, reject ) )
		}).then( function (res) {
			expect(res).to.equal('Done.')
			done()
		})
	}
```

```javascript
	var Proback = require('proback.js');
	...
	// your service. If callback is present, works as expected, If not, promise will be provided.
	function yourService( data, callback ){
		return new Promise( function (resolve, reject) {
			//some callback is needed?
			object.fnCallWithCallback( data, function (err, res) {
				if (err) return Proback.rejecter(err, null, reject)
				// some operation
				return Proback.resolver(res, null, resolve)
			} )
		}).then( function (res) {
			expect(res).to.equal('Done.')
			done()
		})
	}
```
