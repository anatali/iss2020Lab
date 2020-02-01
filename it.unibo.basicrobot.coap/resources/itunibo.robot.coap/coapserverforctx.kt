package itunibo.robot.coap

import org.eclipse.californium.core.CoapServer
import it.unibo.kactor.ActorBasic
import itunibo.robotMbot.mbotSupport
import itunibo.robotsupport.interfaces.resourceactorinterface
import org.eclipse.californium.core.CoapResource

object coapserverforctx{

private val server         =  CoapServer()
private val ctxResource    =  resourceCtx("ctxcoapdemo")

fun start( ) {
	server.add( ctxResource )
    server.start()
	print("coapserverforctx | STARTED")
}

fun add( owner: ActorBasic ){
	ctxResource.addActorReource(owner)
}

fun getCtxResource(   ) : CoapResource{
	print("coapserverforctx | getCtxResource "   )
	return ctxResource
}
	
fun getResource( name : String ) : resourceactorinterface?{
 	return ctxResource.getActorResource( name )
}

}