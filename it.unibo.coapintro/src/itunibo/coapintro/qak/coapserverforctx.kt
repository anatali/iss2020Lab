package itunibo.coapintro.qak

import org.eclipse.californium.core.CoapServer
import it.unibo.kactor.ActorBasic

object coapserverforctx{

private val server       = CoapServer()
private  val ctxResource =  resourceCtx("ctxcoapdemo")

fun start( ) {
	server.add( ctxResource )
    server.start()
}

fun add(owner: ActorBasic){
	ctxResource.add( resourceActor( owner.name, owner) )
}

}