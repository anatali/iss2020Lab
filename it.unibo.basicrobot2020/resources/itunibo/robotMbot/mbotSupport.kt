package itunibo.robotMbot
/*
 -------------------------------------------------------------------------------------------------
 A factory that creates the support for the nano robot
 
 NOV 2019:
 The operation create creates a serial connection and a robotDataSourceArduino
 If a non-null data-stream handler (filter) is given, it subscibes such a filter
 to the actor that will own the support
 -------------------------------------------------------------------------------------------------
 */

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil
import itunibo.robot.rx.ApplActorDataStream

object mbotSupport{
	lateinit var owner   : ActorBasic
 	lateinit var conn    : SerialPortConnSupport
	var dataSonar        : Int = 0 ; //Double = 0.0
 			
	fun create( owner: ActorBasic, port : String, filter: ActorBasic? = null   ){
		this.owner = owner
		initConn( port, filter  )
	}
	
	private fun initConn( port : String, filter: ActorBasic? ){ 
		try {
			//println("   	%%% mbotSupport | initConn starts port=$port")
			val serialConn = JSSCSerialComm()
			conn = serialConn.connect(port)	//returns a SerialPortConnSupport
			println("   	%%% mbotSupport |  initConn port=$port conn= $conn")						
 			robotDataSourceArduino("robotDataSourceArduino", owner,   conn, filter)
		}catch(  e : Exception) {
			println("   	%%% mbotSupport |  ERROR ${e }"   );
		}		
	}
	
	/*
 	 Aug 2019
     The moves l, r, z, x can be executed  
 	  by the Python application robotCmdExec that exploits GY521
    */
	fun  move( cmd : String ){
		//println("  	%%% mbotSupport | move cmd=$cmd ")
		when( cmd ){
			"msg(w)", "w" -> conn.sendALine("w")
			"msg(s)", "s" -> conn.sendALine("s")
			"msg(a)", "a" -> conn.sendALine("a")
			"msg(d)", "d" -> conn.sendALine("d")
			"msg(l)", "l" -> sendToPython("l")	//conn.sendALine("l")
			"msg(r)", "r" -> sendToPython("r")	//conn.sendALine("r")
			"msg(z)", "z" -> sendToPython("z")	//conn.sendALine("z")
			"msg(x)", "x" -> sendToPython("x")	//conn.sendALine("x")
			"msg(h)", "h" -> conn.sendALine("h")
			else -> println("   	%%% mbotSupport | command $cmd unknown")
		}
		
	}
	
	private fun sendToPython( msg : String ){
		println("mbotSupport sendToPython $msg")
		owner.scope.launch{ owner.emit("rotationCmd","rotationCmd($msg)") }
	}
	
 }