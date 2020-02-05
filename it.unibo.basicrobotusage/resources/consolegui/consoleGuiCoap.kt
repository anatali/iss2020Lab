package consolegui

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.CoapResponse
import it.unibo.kactor.MsgUtil
 
class consoleGuiCoap() : consoleGuiBase() {
	
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
		    println("consoleGuiCoap activated doing  get | CODE=  ${respGet.code}")
		}
  	
	override fun forward(move: String) {
 		val d = MsgUtil.buildDispatch("gui", "cmd", "cmd($move)", destName )
        val respPut = client.put(d.toString(), MediaTypeRegistry.TEXT_PLAIN)
        println("PUT ${d} RESPONSE CODE=  ${respPut.code}")
    }
	
	override fun request(move: String) {
	}
	override fun emit( ev : String ){	//evnts are sent to the context resource
		val url = "coap://$hostIP:$port/ctxbasicrobot"
		client = CoapClient( url )
		val msg = MsgUtil.buildEvent("gui",ev,"$ev(0)" )
        val respPut = client.put(msg.toString(), MediaTypeRegistry.TEXT_PLAIN)
        println("PUT ${msg} RESPONSE CODE=  ${respPut.code}")		
	}	
}

fun main(){
	consoleGuiBase.createGui( GuiType.COAP, "localhost", "8018", "basicrobot")
}
 