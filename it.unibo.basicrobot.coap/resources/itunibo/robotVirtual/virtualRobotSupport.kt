package itunibo.robotVirtual

import itunibo.robotsupport.interfaces.robotsupport
import it.unibo.kactor.ActorBasic
import java.io.PrintWriter
import java.io.BufferedReader
import java.net.Socket
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import org.json.JSONObject
import kotlinx.coroutines.launch
import it.unibo.kactor.MsgUtil

object virtualRobotSupport : robotsupport {
	private var odd      = true
	private var hostName = "localhost"
	private var port     = 8999
	private val sep      = ";"
	private var outToServer:  PrintWriter?    = null
	private var inFromServer: BufferedReader? = null
	
	lateinit private var myowner : ActorBasic
	
	override fun create(owner: ActorBasic) {
		myowner = owner
	}

	override fun init(arg: String) { //arg is the port (8999)
		initClientConn( "localhost", arg )
		startTheReader()
	}

	override fun move(cmd: String) {		
 			//println("virtualRobotSupport | sending Msg $v   ")
			var outS  : String
 			when( cmd ){
				"w"  -> outS = "{'type': 'moveForward',  'arg': -1  }"
    			"s"  -> outS = "{'type': 'moveBackward', 'arg': -1  }"
				"a"  -> outS = "{'type': 'turnLeft',     'arg': -1  }"
 				"d"  -> outS = "{'type': 'turnRight',    'arg': -1  }"
				"l"  -> outS = "{'type': 'turnLeft',     'arg': 400 }"
 				"r"  -> outS = "{'type': 'turnRight',    'arg': 400 }"
				"x"  -> outS = "{'type': 'turnLeft',     'arg': 400 }"
 				"z"  -> outS = "{'type': 'turnRight',    'arg': 400 }"
   			    "h"  -> outS = "{'type': 'alarm',        'arg': 0   }"
 				else -> outS = "{'type': 'remove',       'arg': \"$cmd\"  }"
 														//TODO: define the remove method
 			}
			val jsonObject = JSONObject(outS) 
			val msg= "$sep${jsonObject.toString()}$sep"
			//println("		--- virtualRobotSupport | sendMsg $msg   ")
			outToServer?.println(msg)
            outToServer?.flush()	
	}


	//------------------------------------------------------------
	
	
	private fun initClientConn(  hostName: String = "localhost", portStr: String = "8999") {
		port = Integer.parseInt(portStr)
		try {
			//System.setProperty("tcpLowTrace", "set")		//enable tracing of unibonoawtsupport
			val clientSocket = Socket(hostName, port)
			println("		--- virtualRobotSupport |  CONNECTION DONE")
			inFromServer = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
			outToServer = PrintWriter(clientSocket.getOutputStream())
 		} catch (e: Exception) {
			println("		--- virtualRobotSupport | ERROR $e")
		}
	}

	private fun startTheReader() {
		GlobalScope.launch {
			while (true) {
			 try {
				val inpuStr = inFromServer?.readLine()
				val jsonMsgStr =
					inpuStr!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
				//println("		--- virtualRobotSupport | inpuStr= $jsonMsgStr")
				val jsonObject = JSONObject(jsonMsgStr)
				//println( "type: " + jsonObject.getString("type"));
				when (jsonObject.getString("type")) {
					//"webpage-ready" -> println("webpage-ready ")
					"sonar-activated" -> {
						//println("		--- sonar-activated ")
						val jsonArg = jsonObject.getJSONObject("arg")
						val sonarName = jsonArg.getString("sonarName")
						val distance = jsonArg.getInt("distance")
						//emitLocalStreamEvent( m )
						val m1 = "sonar($sonarName, $distance)"
						//println( "		--- virtualRobotSupport EMIT $m1"   );
						myowner.emit("sonar", m1);
					}
					"collision" -> {
						val jsonArg = jsonObject.getJSONObject("arg")
						val objectName = jsonArg.getString("objectName")
 						val vobstev =  MsgUtil.buildEvent("sonarsupport", "virtualobstacle",
										"virtualobstacle($objectName)")
						println("virtualRobotSupport | emit $vobstev")
						myowner.emit(vobstev)

						var m1 = "sonar( 6 )"        //EMIT events with delta = 2
						if (odd) {
							m1 = "sonar( 3 )"
						}
						val event = MsgUtil.buildEvent("sonarsupport", "sonarRobot", m1)
						odd = !odd
						//(streaming)
						myowner.emitLocalStreamEvent(event)
						}
					}
			 } catch (e:  Exception) {
				println("		--- virtualRobotSupport | ERROR $e   ")
				System.exit(1)
			 }
			}
		}
	}//startTheReader

}

