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
 
class consoleGuiTcp : IObserver {
	
	companion object{
		val buttonLabels = arrayOf("h","w", "s", "l", "r", "z", "x", "b", "p", "h")
 		lateinit var msgId    : String
//		lateinit var client   : CoapClient
		lateinit var owner    : ActorBasic
		
		fun create(  b: ActorBasic ){
			owner = b
  			val concreteButton = ButtonAsGui.createButton( buttonLabels )
            concreteButton.addObserver( consoleGuiTcp() )
//			val url = "coap://localhost:5683/robot/pos"
//			client = CoapClient( url )
//			client.setTimeout( 1000L )
		}
	  }
	
      override fun update(o: Observable, arg: Any) {	   
   		  var cmd = arg as String
		   
//  		  println("guiSupport update $cmd  ")
//		  val resp : CoapResponse = client.put(cmd, MediaTypeRegistry.TEXT_PLAIN)
//		  println("response=${resp.getCode()}")
		  
	       GlobalScope.launch{
			   //buttonActor.emit( msgId , "$msgId($cmd)")
 			   MsgUtil.sendMsg ("cmd" , "cmd($cmd)" , owner)
	       }
		  
      }//update
}


//fun main(){ consoleGuiTcp.create(   ) }
 