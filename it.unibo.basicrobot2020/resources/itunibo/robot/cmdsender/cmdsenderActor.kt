package it.unibo.robot.cmdsender

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import it.unibo.supports.FactoryProtocol
import it.unibo.`is`.interfaces.protocols.IConnInteraction
 

/*
 A qak-actor written by the Application designer (without the qak software factory)
 that forward a cmd dispatch to the basicrobot
 */
class cmdsenderActor( name : String, scope: CoroutineScope) : ActorBasic(name,scope){
 var robot : ActorBasic? = null
 
	init{
		println("	--- cmdsenderActor | start in ctx=${this.context}")
		val sol1 = pengine.solve( "consult('basicrobot.pl')." )
		val sol2 = pengine.solve( "consult('sysRules.pl')." )
		if( ! sol1.isSuccess() || ! sol2.isSuccess()){
			println("	--- cmdsenderActor | ERROR in reading the knowledge")
		}else{
			 val host = pengine.solve( "getCtxHost( ctxbasicrobot, HOST).").getVarValue("HOST").toString()
			 println("	--- cmdsenderActor | basicrobot host =$host")
			 val port = pengine.solve( "getCtxPort( ctxbasicrobot, PORT).").getVarValue("PORT")
			 val portNum = port.toString().replace("'","").toInt()
			 println("	--- cmdsenderActor | basicrobot port =$port")
			 val protocol = pengine.solve( "getCtxProtocol( ctxbasicrobot, PROT).").getVarValue("PROT").toString()
			 println("	--- cmdsenderActor | basicrobot protocol =$protocol")
 			
			 //Introduce a TCP client
			 val fp    = FactoryProtocol(null,"TCP","sender")
			 val conn  = fp.createClientProtocolSupport(host, portNum )   //IConnInteraction
			 doJobTcp( protocol, conn )
  		}
    }
	
	fun doJobTcp( protocol : String , conn : IConnInteraction){
		 scope.launch{
		     println("	--- cmdsenderActor | tcp  sends ")
			 var msg = MsgUtil.buildDispatch(name,"cmd","cmd(l)","basicrobot")
			 conn.sendALine( msg.toString()  )
		     delay(2000) 
		     println("	--- cmdsenderActor | tcp  sends ")
		     msg = MsgUtil.buildDispatch(name,"cmd","cmd(r)","basicrobot")
		     conn.sendALine( msg.toString()  )
			 delay(2000) 
			 if( protocol.replace("'","") == "MQTT"){
				 val sol  = pengine.solve( "mqttBroker( MQTTHOST, MQTTADDR).")
				 if( sol.isSuccess() )	 {
					 val mqttBrokerIP    = sol.getVarValue("MQTTHOST").toString().replace("'","")
					 val mqttBrokerPort  = sol.getVarValue("MQTTADDR").toString().replace("'","")
					 val mqttaddr  = "tcp://$mqttBrokerIP:$mqttBrokerPort"
					 println("	--- cmdsenderActor |  mqtt sends to $mqttaddr")
					  mqtt.connect(name, mqttaddr)
					  mqtt.sendMsg(msg, "unibo/qak/basicrobot")
					  delay(2000)
				 }
				
			 }
		}
		
	}
	
	override suspend fun actorBody(msg: ApplMessage){}
}

fun main() = runBlocking {
    println("START   ")
    //val a =
	cmdsenderActor("cmdsender", this)   
    println("END   ")
	//System.exit(1)
}