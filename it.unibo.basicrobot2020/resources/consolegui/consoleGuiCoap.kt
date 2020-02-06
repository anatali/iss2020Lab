package consolegui

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.CoapResponse
import it.unibo.kactor.MsgUtil
 
class consoleGuiCoap() : consoleGuiBase() {
	companion object{
		fun create(port: String = "5683", destActor: String ="basicrobot"){
			consoleGuiBase.createGui( ConnectionType.COAP, "localhost", port, destActor)
		}
	}
 		lateinit var client   : CoapClient
	    lateinit var hostIP   : String
	    lateinit var port     : String
		
		override fun createConnection( hostIP: String, port: String  ){
			Utils.showSystemInfo()
			this.hostIP = hostIP
			this.port   = port
			val url = "coap://$hostIP:$port/ctxbasicrobot/$destName"
			client = CoapClient( url )
			client.setTimeout( 1000L )
 			//initialCmd: to make console more reactive at the first user cmd
 		    val respGet  = client.get( ) //CoapResponse
		    println("consoleGuiCoap | createConnection doing  get | CODE=  ${respGet.code}")
		}
  	
	override fun forward(move: String) {
 		val d = MsgUtil.buildDispatch("gui", "cmd", "cmd($move)", destName )
        val respPut = client.put(d.toString(), MediaTypeRegistry.TEXT_PLAIN)
        println("consoleGuiCoap | PUT forward ${d} RESPONSE CODE=  ${respPut.code}")
    }
	
	override fun request(move: String) {
		val msg = MsgUtil.buildRequest("gui", move,"$move(600)", destName)
		val respPut = client.put(msg.toString(), MediaTypeRegistry.TEXT_PLAIN)
  		println("consoleGuiCoap | answer= ${respPut.getResponseText()}")		
	}
	override fun emit( ev : String ){	//evnts are sent to the context resource
		val url = "coap://$hostIP:$port/ctxbasicrobot"
		client = CoapClient( url )
        //println("PUT emit url=${url} ")		
		val msg = MsgUtil.buildEvent("gui",ev,"$ev(0)" )
        val respPut = client.put(msg.toString(), MediaTypeRegistry.TEXT_PLAIN)
        println("consoleGuiCoap | PUT emit ${msg} RESPONSE CODE=  ${respPut.code}")		
	}	
}

fun main(){
	consoleGuiCoap.create(  )
}
 