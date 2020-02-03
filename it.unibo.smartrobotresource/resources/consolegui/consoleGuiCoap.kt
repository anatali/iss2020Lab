package consolegui
 
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import it.unibo.kactor.ActorBasic
import it.unibo.`is`.interfaces.IObserver
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.CoapResponse
import it.unibo.kactor.MsgUtil
 
class consoleGuiCoap : IObserver {
	
	companion object{
		val buttonLabels = arrayOf("h","w", "s", "r", "l", "z", "x", "b", "p", "h")
		val coapPort   = 8020
		val ctx        = "ctxSmartRobot"
		val destActor  = "smartrobot"
		val path       = "$ctx/$destActor"
 		lateinit var msgId    : String
		lateinit var client   : CoapClient
		
		fun create(   ){
			val url = "coap://localhost:$coapPort/$path"
			client = CoapClient( url )
			client.setTimeout( 1000L )
			initialCmd( )   //to make console more reactive at the first user cmd
  			val concreteButton = ButtonAsGui.createButton( buttonLabels )
            concreteButton.addObserver( consoleGuiCoap() )
		}
		fun initialCmd( ) {
		 		//val d = MsgUtil.buildDispatch("console", "cmd", "cmd(h)", destActor )
		        val respGet  = client.get( ) //CoapResponse
		        println("consoleGuiCoap activated doing  get | CODE=  ${respGet.code}")
		}
	  } 
	
      override fun update(o: Observable, arg: Any) {	   
   		  var cmd = arg as String
  		  println("guiSupport update $cmd  ")
 		  sendToServer( cmd )
      }//update
	
	fun sendToServer(move: String) {
 		val d = MsgUtil.buildDispatch("console", "cmd", "cmd($move)", destActor )
        val respPut = client.put(d.toString(), MediaTypeRegistry.TEXT_PLAIN)
        println("PUT ${d} RESPONSE CODE=  ${respPut.code}")
    }
	
}

fun main(){
	consoleGuiCoap.create(   )
}
 