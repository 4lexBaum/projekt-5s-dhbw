module.exports = {
	dummy: function ( res, callback ) {
		return new Promise( (resolve, reject) => {
			console.log( res )
			if (callback) callback( null, res )
			resolve(res)
		} )
	},
	quicker: function ( res, callback ) {
		return new Promise( (resolve, reject) => {
			if (callback) callback( null, res )
			resolve(res)
		} )
	},
	forAll: function ( promises, callback, defaultRes ) {
		return new Promise( (resolve, reject) => {
			Promise.all( promises )
			.then( (res) => {
				if (callback) callback(null, defaultRes || res)
				resolve( defaultRes || res )
			} )
			.catch( (reason) => {
				if (callback) callback(reason)
				reject( reason )
			} )
		} )
	},
	handler: function ( callback, resolve, reject ) {
		return function (err, res) {
			if ( err ) {
				callback && callback( err, null )
				return reject(err)
			}
			callback && callback( null, res )
			resolve(res)
		}
	},
	returner: function ( err, res, callback, resolve, reject ) {
		if ( err ) {
			callback && callback( err, null )
			return reject(err)
		}
		callback && callback( null, res )
		resolve(res)
	},
	resolver: function (res, callback, resolve) {
		callback && callback( null, res )
		resolve( res )
	},
	rejecter: function (err, callback, reject) {
		callback && callback( err, null )
		reject( err )
	}
}
