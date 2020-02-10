package consolegui
 
object consoleGuiMqtt{
	fun create(  hostIP : String,     port : String,     destName : String) {
		consoleGuiSimple( connQak.ConnectionType.MQTT, hostIP, port, destName)
	}
}
 
fun main(){
	consoleGuiMqtt.create( "localhost", "1883", "basicrobot")
}
 