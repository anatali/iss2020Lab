var coap        = require('coap')
  , server      = coap.createServer()

server.on('request', function(req, res) {
  console.log( "server | url=" + req.url )
  //res.send('Hello from Coap server to robot move:' + req.url.split('/')[1] + '\n')
  res.end('Hello from Coap server to robot move:' + req.url.split('/')[1] + '\n')
})

// the default CoAP port is 5683


function sendToServer( msg ){
  console.log( "sendToServer | msg=" + msg  )
  var req = coap.request('coap://localhost/r' )
  req.on('response', function(res) {
  	console.log( "client | response=" + res.payload.toString('utf8') )
    res.pipe( process.stdout )
    res.on('end', function() {
      console.log( "client | end") 
      //process.exit(0)
    })
  })
}

function w( ){ sendToServer("w") }
function r( ){ sendToServer("r") }
function l( ){ sendToServer("l") }
function h( ){ sendToServer("h") }


server.listen(function() {
  var req = coap.request('coap://localhost/w')

  req.on('response', function(res) {
  	console.log( "client | response=" + res.payload.toString('utf8') )
    res.pipe( process.stdout )
    res.on('end', function() {
      console.log( "client | end") 
      //process.exit(0)
    })
  })

  req.end()
})

/*
console.log( "client | sends" )
server.listen( r )
*/