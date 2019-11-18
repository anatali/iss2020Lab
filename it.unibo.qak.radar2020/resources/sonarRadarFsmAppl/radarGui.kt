package sonarRadarFsmAppl.radarGui
import it.unibo.supports.FactoryProtocol
import it.unibo.kactor.ActorBasicFsm
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import it.unibo.`is`.interfaces.protocols.IConnInteraction
import highLevelComms.hlComm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.GlobalScope
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Term
import alice.tuprolog.Struct     

	var hlCommSupport : hlComm? =  null

fun curThread() : String { return "thread=${Thread.currentThread().name} of ${Thread.activeCount()} threads"  }

/*
 Activates a TCP server and waits for a connection
 Afterwards, it waits for a msg (a string rep of ApplMessage) on the created hlCommSupport
 and sends the received msg to the given actor a
*/	
class radarguiReceiver( val a : ActorBasicFsm   ){
	init{
		val workTime = 600000L
		System.setProperty("inputTimeOut", "$workTime" )  //for the server socket
		connectAsReceiver( "TCP", 8010 )
		GlobalScope.launch{ work() }
	}

	fun connectAsReceiver( protocol: String, port : Int  ){
		println("radarguiReceiver | START A TCP SERVER ON PORT 8010 and waits for a connection ... ")
		val fp    = FactoryProtocol(null,protocol,"support")	 
		val conn : IConnInteraction = fp.createServerProtocolSupport( port ) //waitforconnection
		hlCommSupport = hlComm( conn )
		println("radarguiReceiver | CONNECTED ")
	}
	
	suspend fun  work(){
//		connectAsReceiver( "TCP", 8010 )
 		while( true ){
			//println("receiver | waiting ... ")
			val msg = hlCommSupport!!.receive()
			println("radarguiReceiver | receives: $msg")
			val currentMsg = ApplMessage( msg )
 			MsgUtil.sendMsg( currentMsg, a )
		} 			
	}
} 
class radarGui( name : String, scope : CoroutineScope  ) : ActorBasicFsm( name, scope ) {
var msgSender = ""
/*
If the class has a superclass, the superclass constructor
is called before the class construction logic is executed.
 init block is not actually a separate method.
 Instead, all init blocks together with member property initializers
 are compiled into the code of the constructor, so they should
 rather be considered a part of the constructor.
*/	
	init{
		//receiver(  myself  )		//BLOCKS  : we put it in the intial state
	}
	    
	override fun getInitialState(): String{ return "INIT"}
	
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		//println("radarGui | getBody in ${curThread()} ")
		return {
			state("INIT") { //this:State
				action { //it:State	}
					radarPojo.radarSupport.setUpRadarGui()
					radarguiReceiver(  myself  )  //creates a (TCP) receiver
				}
 				transition(edgeName="t0",targetState="ELAB",cond=whenRequest("polar") ) 
			}
			state("ELAB"){ 
				action{
					println("radarGui | elab ${currentMsg} in ${curThread()} ")
					val args = getDistanceAndAngle(currentMsg)
					radarPojo.radarSupport.update(args.first,args.second)
					storeCurrentMessageForReply()  //SINCE a state switch chnages currentMsg
					transition(edgeName="t0",targetState="SENDANSWER",cond=doswitch() ) //EMPTY MOVE
				}
			}
			state("SENDANSWER"){
				action{
					println("radarGui | ${myself.name} SENDANSWER to ${msgToReply.msgSender()} in ${curThread()} ")
					delay( 1000 )//simulate some work to do ...
					hlCommSupport!!.answer("answer", "work(done)", "${myself.name}", "${msgToReply.msgSender()}" )
					transition(edgeName="t0",targetState="ELAB",cond=whenRequest("polar") ) 
 				}
			}
			state("END"){
				action{
					println("radarGui | END")
				}
			}
		}
	}//getBody

/*
 Utility methods
*/
	fun getDistanceAndAngle( applMsg : ApplMessage) : Pair<String,String>{ //polar(20,90)
		val tt          = Term.createTerm( applMsg.msgContent()  ) as Struct
		val DistanceStr = tt.getArg(0).toString()
		val AngleStr    = tt.getArg(1).toString()
		return Pair(DistanceStr,AngleStr)
	}
}//radarGui

 

//@kotlinx.coroutines.ObsoleteCoroutinesApi
//@kotlinx.coroutines.ExperimentalCoroutinesApi
fun main() = runBlocking  {
	//System.setProperty("debugOn", "set" ) 	//for noawtsupport
	val cpus = Runtime.getRuntime().availableProcessors();
    println("radarGui  | START CPU=$cpus ${curThread()}")
    val radar =  radarGui( "radarGui", this )
}