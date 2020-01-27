var coap        = require('coap')
 
function sendToServer( ){
	var req = coap.request('coap://192.168.1.8/time' )
	//console.log("sendToServer | " + msg)
	req.on('response' , function(res){
			//console.log( "listen | response=" + res.payload.toString('utf8') )
	        res.pipe(process.stdout)
		})
	req.end()
}

 

sendToServer( )
 