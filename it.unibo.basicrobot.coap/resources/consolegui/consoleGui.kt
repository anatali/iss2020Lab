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
 
class consoleGui : IObserver {
	
	companion object{
		val buttonLabels = arrayOf("h","w", "s", "r", "l", "z", "x", "b", "p", "h")
 		lateinit var msgId    : String
		lateinit var client   : CoapClient
		
		fun create(   ){
  			val concreteButton = ButtonAsGui.createButton( buttonLabels )
            concreteButton.addObserver( consoleGui() )
			val url = "coap://localhost:5683/ctxcoapdemo/actor0"
			client = CoapClient( url )
			client.setTimeout( 1000L )
		}
	  }
	
      override fun update(o: Observable, arg: Any) {	   
   		  var cmd = arg as String
  		  println("guiSupport update $cmd  ")
 		  sendToServer( cmd )
      }//update
	
	fun sendToServer(move: String) {
 		val d = MsgUtil.buildDispatch("external", "cmd", "cmd($move)", "actor0" )
        val respPut = client.put(d.toString(), MediaTypeRegistry.TEXT_PLAIN)
        println("PUT ${d} RESPONSE CODE=  ${respPut.code}")
    }
	
}
fun main(){
	consoleGui.create(   )
}
 