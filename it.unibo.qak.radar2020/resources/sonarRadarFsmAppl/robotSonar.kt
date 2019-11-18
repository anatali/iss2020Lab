package sonarRadarFsmAppl.robotSonar

import it.unibo.supports.FactoryProtocol
import it.unibo.kactor.ActorBasicFsm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import it.unibo.`is`.interfaces.protocols.IConnInteraction
import highLevelComms.hlComm
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ApplMessage
 
fun curThread() : String { return "thread=${Thread.currentThread().name} of ${Thread.activeCount()} threads"  }

class answerreceiver( val a : ActorBasicFsm, val hlCommSupport : hlComm  ){
/*
 Waits for a msg (a string rep of ApplMessage) on the given hlCommSupport
 and sends the received msg to the given actor a
*/	
	init{
		val workTime = 600000L
		System.setProperty("inputTimeOut", "$workTime" )  //for the server socket
		GlobalScope.launch{ work() }
	}
	
	suspend fun  work(){
  		while( true ){
			//println("answerreceiver | waiting ... ")
			val msg = hlCommSupport.receive()
			println("answerreceiver |  $msg")
			val currentMsg = ApplMessage( msg )
 			MsgUtil.sendMsg( currentMsg, a )
		} 			
	}
} 

class robotSonar( name : String, scope: CoroutineScope ) : ActorBasicFsm( name, scope) {
var hlCommSupport : hlComm?      =  null
var i = 1
	
    override fun getInitialState(): String{ return "INIT"}
	
	override fun getBody() : (ActorBasicFsm.() -> Unit){
	//println("robotSonar | getBody in ${curThread()}"  )
	return {
		state("INIT") { //this:State
			action { //it:State
				println("robotSonar | START in ${curThread()}, $currentState ")
				var conn : IConnInteraction? = null
				val fp  = FactoryProtocol(null,"TCP","robotSonar")
				println("robotSonar | START in ${curThread()} , $fp, $conn")
				while( conn == null ){
					try{
						println("robotSonar | attempt connection ...")
						conn = fp.createClientProtocolSupport("localhost", 8010)
						println("robotSonar | $conn")
			 		    hlCommSupport = hlComm( conn )
						//activate an answer receiver with the connection just set
			 		    answerreceiver( myself, hlCommSupport!!  )	
					}catch( e : Exception){
						println("robotSonar |  FAIL attempt to connect in ${curThread()}... ")
						delay( 500 )
					}
				}
			}
			transition(edgeName="t0",targetState="SENDREQUEST",cond=doswitch() ) //EMPTY MOVE
 		}
		state("SENDREQUEST"){
			action{
				val v = i++*20
				println(  "robotSonar | doing request: $v")
				hlCommSupport!!.request( "robotsonar","polar", "polar($v,90)", "radargui" )
			}
			transition(edgeName="t0",targetState="ELABANSWER",cond=whenReply("answer") )  
		}	 		
		state("ELABANSWER"){
			action{
				println("$name in ${currentState.stateName} | $currentMsg")
 			}
				transition(edgeName="t0",targetState="END",cond=doswitchGuarded({i>3}) )
				transition(edgeName="t1",targetState="SENDREQUEST",cond=doswitchGuarded({i<=3}) )
 		}	 		
		state("END"){
			action{
				println(  "robotSonar | END")
			}
		}  		
	}
  }//getBody
}//class


@kotlinx.coroutines.ObsoleteCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
fun main() = runBlocking{
	val cpus = Runtime.getRuntime().availableProcessors();
    println("robotSonar  |  START CPU=$cpus ${curThread()}")
    val rb = robotSonar("robotSonar", this)
//	println( rb )
//	delay( 1000 )
// 	MsgUtil.sendMsg( "main", "a", "a", rb)	  
}


