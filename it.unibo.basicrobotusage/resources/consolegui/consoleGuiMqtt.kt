package consolegui
 
//import it.unibo.kactor.MqttUtils
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.ApplMessage
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
 
class consoleGuiMqtt() :  consoleGuiBase(), MqttCallback {
	
 	lateinit var client  : MqttClient
 	val clientid         = "guimqtt"
	val answerTopic      = "unibo/qak/$clientid"

	    override fun messageArrived(topic: String, msg: MqttMessage) {
        //sysUtil.traceprintln("$tt ActorBasic $name |  MQTT messageArrived on "+ topic + ": "+msg.toString());
        val m = ApplMessage( msg.toString() )
        println("       %%% consoleGuiMqtt |  ARRIVED on $topic  m=$m  " )
     }
	
    override fun connectionLost(cause: Throwable?) {
        println("       %%% consoleGuiMqtt |  MQTT connectionLost $cause " )
    }
    override fun deliveryComplete(token: IMqttDeliveryToken?) {
//		println("       %%% consoleGuiMqtt | deliveryComplete token= "+ token );
    }
	
	override  fun createConnection(hostIP: String, port: String){
		val brokerAddr = "tcp://$hostIP:$port"
		try {
  			//println("     %%% consoleGuiMqtt | doing connect for $clientid to $brokerAddr "  );
			client = MqttClient(brokerAddr , clientid )
			val options = MqttConnectOptions()
			options.setKeepAliveInterval(480)
			options.setWill("unibo/clienterrors", "crashed".toByteArray(), 2, true)
			client.connect(options)
			println("       %%% consoleGuiMqtt | connect DONE $clientid to $brokerAddr " )//+ " " + client
//Prepare to acquire answer to request
			client.setCallback(this)
			client.subscribe(answerTopic)
			
		} catch (e: Exception) {
			println("       %%% consoleGuiMqtt | for $clientid connect ERROR for: $brokerAddr" )
 		}
//		mqtt.connect( "gui", "tcp://$hostIP:$port"  )
 	}
	fun publish( msg: String, topic: String, qos: Int = 1, retain: Boolean = false) {
		val message = MqttMessage()
		message.setRetained(retain)
		if (qos == 0 || qos == 1 || qos == 2) {
			//qos=0 fire and forget; qos=1 at least once(default);qos=2 exactly once
			message.setQos(0)
		}
		message.setPayload(msg.toByteArray())
		try {
			println("mqtt publish $msg on $topic")
			client.publish(topic, message)
		} catch (e:Exception) {
			println("       %%% consoleGuiMqtt | publish ERROR $e topic=$topic msg=$msg"  )
 		}
	}
       
    override fun forward( move : String ){
    	val msg = MsgUtil.buildDispatch(clientid,"cmd","cmd($move)", destName)
		publish(msg.toString(), "unibo/qak/$destName")
	}
	override fun request(move: String) {
		val msg = MsgUtil.buildRequest(clientid, move,"$move(600)", destName)
		publish(msg.toString(), "unibo/qak/$destName")
		//The answer should be in unibo/qak/gui
	}
	override fun emit( ev : String ){
    	val msg = MsgUtil.buildEvent("gui",ev,"$ev(0)" )
		publish(msg.toString(), "unibo/qak/events")
	}	
}

 
fun main(){ 
	//consoleGuiMqtt().create( "localhost", "1883", "basicrobot"  )
	consoleGuiBase.createGui( GuiType.MQTT, "localhost", "1883", "basicrobot")
}
 