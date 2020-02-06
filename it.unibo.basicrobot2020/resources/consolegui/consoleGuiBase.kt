package consolegui
import it.unibo.`is`.interfaces.IObserver
import java.util.Observable
 
enum class ConnectionType {
    TCP, MQTT, COAP, HTTP
}

abstract class consoleGuiBase : IObserver {
	
 companion object{
 	  val buttonLabels = arrayOf("e","w", "s", "l", "r", "z", "x", "b", "p", "h")
  	  lateinit var destName     : String
	  lateinit var guiUsed      : consoleGuiBase

      fun createGui(  guiType: ConnectionType, hostIP : String, port : String, actorName : String ){
		  println("consoleGuiBase ! create guiType: $guiType")
		  destName = actorName
		  when( guiType ){
				 ConnectionType.MQTT ->
				 	{guiUsed = consoleGuiMqtt(); guiUsed.create("Gui-MQTT", hostIP, port )}
				 ConnectionType.TCP ->
				 	{guiUsed = consoleGuiTcp() ; guiUsed.create("Gui-TCP", hostIP, port )}
				 ConnectionType.COAP ->
				 	{guiUsed = consoleGuiCoap() ; guiUsed.create("Gui-COAP", hostIP, port )}
				 ConnectionType.HTTP ->
				 	{guiUsed = consoleGuiHttp() ; guiUsed.create("Gui-HTTP", hostIP, port )}
				 //else -> println("WARNING: protocol unknown")
		  }
		 }//createGui
 }//object
	
	  fun create(  guiName : String, hostIP : String, port : String  ){
 		  createTheGui( guiName )
		  createConnection(hostIP, port)
 	  }
	  fun createNoGui(   hostIP : String, port : String, actorName : String   ){
		  destName = actorName
 		  createConnection(hostIP, port)
 	  }
	  fun createTheGui( guiName : String ){
  			val concreteButton = ButtonAsGui.createButtons( guiName, buttonLabels )
            concreteButton.addObserver( guiUsed )		  
	  }
	
	  abstract fun createConnection(hostIP: String, port: String)     
      abstract fun forward( move : String );
      abstract fun request( move : String );
      abstract fun emit( ev : String );
	
	  override fun update(o: Observable, arg: Any) {	   
   		  var move = arg as String
		  if( move == "p" ) request("p")
		  else if( move == "e" ) emit("alarm")
 		  else forward( move ) 		  
       }//update
}

 
 
 