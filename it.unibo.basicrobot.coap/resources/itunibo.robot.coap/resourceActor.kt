package itunibo.robot.coap

import org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED
import org.eclipse.californium.core.coap.CoAP.ResponseCode.CREATED
import org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.CoapServer
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.server.resources.CoapExchange
import it.unibo.kactor.ActorBasic
import itunibo.robotMbot.mbotSupport
import itunibo.robotsupport.interfaces.robotsupport
import itunibo.robotVirtual.virtualRobotSupport
import itunibo.robotsupport.interfaces.resourceactorinterface
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.launch


class resourceActor( name: String, val owner : ActorBasic,
	var robot : robotsupport = virtualRobotSupport ) : CoapResource(name), resourceactorinterface {
    
	private var curMsg      = "h"
	private var answer      = "nothing"
 	
    init {
        isObservable = true
        println("Resource $name | created  ")
    }
	

    override fun handleGET(exchange: CoapExchange) {
        println("Resource " + name + " | handleGET from:" +
                exchange.sourceAddress + " arg:" + exchange.requestText)
		
        exchange.respond("Resource $name | answer: $answer")
    }

    /*
 * POST is NOT idempotent.	Use POST when you want to add a child resource
 */
    override fun handlePOST(exchange: CoapExchange) {
        exchange.accept()
        if (exchange.requestOptions.isContentFormat(MediaTypeRegistry.TEXT_XML)) {
            val xml = exchange.requestText
            exchange.respond(CREATED, xml.toUpperCase())

        } else {
            // ...
            exchange.respond(CREATED)
        }
    }

    /*
 * PUT method is idempotent. Use PUT when you want to modify
 */

    override fun handlePUT(exchange: CoapExchange) {
        val arg = exchange.requestText  //arg = 
        println("Resource $name | handlePUT arg=$arg")
		try{
			val msg = ApplMessage( arg )
	 		curMsg = arg.toString()
			answer="$curMsg done"
			delegateToOwner( msg )  //could change answer (if msg is a request)
 		}catch( e : Exception){
			println("Resource $name | handlePUT ERROR on msg ")
		}
        exchange.respond( CHANGED )  
    }

    override fun handleDELETE(exchange: CoapExchange) {
        delete()
        exchange.respond( DELETED )
    }

//-----------------------------------------------------------

 	
	fun delegateToOwner( msg : ApplMessage ){
		owner.scope.launch{
			owner.autoMsg( msg )
			
		}
		changed()    // notify all CoAp observers
		/*
 if the msg is a request, I create a 'caller' actor that waits for the answer
 from the owner and then sets the var answer 
 that can be viewed by the coap client
 		*/
	}
 

}

 