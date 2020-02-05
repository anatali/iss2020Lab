package consolegui
 
import it.unibo.kactor.MqttUtils
import it.unibo.kactor.MsgUtil
 
class consoleGuiMqtt() :  consoleGuiBase() {
	
 	lateinit var mqtt  : MqttUtils
 
	override  fun createConnection(hostIP: String, port: String){
		mqtt                  = MqttUtils()
		mqtt.connect( "gui", "tcp://$hostIP:$port"  )
 	}
       
    override fun forward( move : String ){
    	val msg = MsgUtil.buildDispatch("gui","cmd","cmd($move)", destName)
		println("mqtt forward $msg $mqtt")
		mqtt.sendMsg(msg, "unibo/qak/$destName")
	}
	override fun request(move: String) {
	}
	override fun emit( ev : String ){
    	val msg = MsgUtil.buildEvent("gui",ev,"$ev(0)" )
		mqtt.sendMsg(msg, "unibo/qak/events")
	}	
}

 
fun main(){ 
	//consoleGuiMqtt().create( "localhost", "1883", "basicrobot"  )
	consoleGuiBase.createGui( GuiType.MQTT, "localhost", "1883", "basicrobot")
}
 