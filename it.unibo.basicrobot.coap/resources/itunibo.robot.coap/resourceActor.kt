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


class resourceActor( name: String,
	var robot : robotsupport = virtualRobotSupport ) : CoapResource(name), resourceactorinterface {
//The robot support is INJECTED
    private var curMove      = "h"
//    lateinit var robot : robotsupport
	
    init {
        isObservable = true
        println("Resource $name | created  ")
    }
	

    override fun handleGET(exchange: CoapExchange) {
        println("Resource " + name + " | handleGET from:" +
                exchange.sourceAddress + " arg:" + exchange.requestText)
        exchange.respond("Resource $name | curMove = $curMove")
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
        val arg = exchange.requestText
        println("Resource $name | PUT arg=$arg")
//        if (arg == "inc") counter++
//        if (arg == "dec") counter--
		curMove = arg
		robot.move( curMove ) 
        changed()    // notify all CoAp observers
        /*
    	 * Notifies all CoAP clients that have established an observe relation with
    	 * this resource that the state has changed by reprocessing their original
    	 * request that has established the relation. The notification is done by
    	 * the executor of this resource or on the executor of its parent or
    	 * transitively ancestor. If no ancestor defines its own executor, the
    	 * thread that has called this method performs the notification.
    	 */
        exchange.respond(CHANGED)
    }

    override fun handleDELETE(exchange: CoapExchange) {
        delete()
        exchange.respond(DELETED)
    }

//-----------------------------------------------------------

	override fun setRobotSupport( rs : robotsupport){
		println("Resource " + name + " | setRobotSupport " + rs )
		robot = rs
	}
	
//	override fun getName() : String{
//		return name
//	}

}

 