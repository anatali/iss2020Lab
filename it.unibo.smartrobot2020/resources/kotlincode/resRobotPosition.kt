package kotlincode

import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.server.resources.CoapExchange
import org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import org.eclipse.californium.core.coap.CoAP.ResponseCode.CREATED;
import org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import org.eclipse.californium.core.CoapServer

enum class Direction( ){
	SUD, EAST, NORTH, WEST
}

class resRobotPosition( name : String) : CoapResource( name ){
 	var pos        = Pair<Int,Int>(0,0)
	var direction  = Direction.SUD
	
	init{
		setObservable(true)
		println("resource $name  | created  " );		
	}
	override fun handleGET( exchange : CoapExchange ) {
		println("resource $name  | GET: ${exchange.getRequestText()} robotPos=$pos")
		exchange.respond( "$pos / $direction" );
	}
//	override fun handlePOST( exchange : CoapExchange ) {
//	}
	override fun handlePUT( exchange : CoapExchange) {
		val msg = exchange.getRequestText()
		println("resource $name  | PUT: $msg")
		when( msg ){
			"w" ->  updatePos()
			"a" ->  rotateLeft( )
			"d" ->  rotateRight( )
			//else -> println("")
		}
		changed()	// notify all CoAp observers
		exchange.respond(CHANGED)
	}
	
	fun updatePos( ){
		val X = pos.first.toInt()
		val Y = pos.second.toInt()
		when( direction ){
			Direction.SUD   -> pos = Pair(X,Y+1)
			Direction.EAST  -> pos = Pair(X+1,Y)
			Direction.NORTH -> pos = Pair(X,Y-1)
			Direction.WEST  -> pos = Pair(X-1,Y)
		}
	}
	
	fun rotateLeft( ){
 		when( direction ){
			Direction.SUD    -> direction  = Direction.EAST
			Direction.EAST   -> direction =  Direction.NORTH
			Direction.NORTH  -> direction =  Direction.WEST
			Direction.WEST   -> direction =  Direction.SUD 
		}
	}
	
	fun rotateRight( ){
 		when( direction ){
			Direction.SUD    -> direction  = Direction.WEST
			Direction.EAST   -> direction =  Direction.SUD
			Direction.NORTH  -> direction =  Direction.EAST
			Direction.WEST   -> direction =  Direction.NORTH 
		}
	}
	
//	override fun handleDELETE( exchange : CoapExchange) {
//		delete();
//		exchange.respond(DELETED);
//	}
	
}