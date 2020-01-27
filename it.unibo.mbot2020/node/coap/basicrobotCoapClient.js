var coap        = require('coap')
  , server      = coap.createServer()
var SerialPort  = require("serialport");
var arduinoPort = "/dev/ttyUSB0";	//COM6


var arduinoSerialPort = new SerialPort(arduinoPort, { baudRate: 115200  });
arduinoSerialPort.on('open',function() {
  console.log('Serial Port ' + arduinoPort + ' is opened.');
});


server.on('request', function(req, res) {
  console.log( "server | url=" + req.url )
  var move = req.url.split('/')[1]
  arduinoSerialPort.write( move );
  res.end('Hello from Coap server to robot move:' + move + '\n')
})

// the default CoAP port is 5683
server.listen(function() {
	console.log('server started')
})

 
function sendToServer( msg ){
	var req = coap.request('coap://localhost/'+msg)
	//console.log("sendToServer | " + msg)
	req.on('response' , function(res){
			//console.log( "listen | response=" + res.payload.toString('utf8') )
	        res.pipe(process.stdout)
		})
	req.end()
}

function w( ){ sendToServer("w") }
function r( ){ sendToServer("r") }
function l( ){ sendToServer("l") }
function h( ){ sendToServer("h") }
function q( ){ 
	var req = coap.request('coap://localhost/q') 
	req.on('response' , function(res){
	        res.pipe(process.stdout)
		    res.on('end', function() {
		      	console.log( "q | end") 
		      	process.exit(0)
		    })
		})
	req.end()
}

setTimeout( function(){ 
	console.log('working ...'); 
	l()
	r()
	//q()
},1000);


 