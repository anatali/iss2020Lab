package kotlincode

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor 
import java.io.BufferedReader
import java.io.InputStreamReader
import javacode.ResourceSonarClient
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil


class sonarOnPcSimulate( val name : String, val scope: CoroutineScope = GlobalScope )  {

	val actor = scope.actor<ApplMessage>{
 			    for (msg in channel) { // iterate over incoming messages
			        when ( msg.msgId()  ) {
						"start" -> scope.launch{ readInputData() }
			            else -> throw Exception( "unknown" )
			        }
			    }
			}
	
	var resourceSupport : ResourceSonarClient	 

	
	init{
		resourceSupport = ResourceSonarClient("coap://localhost:5683", "sonardata")
	}

    suspend fun readInputData(){
        var dataCounter = 1
        while( true ){
 			 var data = readLine()
             println("data ${dataCounter++} = $data " )
             val m = MsgUtil.buildEvent(name, "sonar", "sonar($dataCounter, $data)")
             println("EMIT to CoAP: $m"  )
             resourceSupport.updateTheResource( m.toString() )
        }

    }

}

	
fun main() = runBlocking {

	val a = sonarOnPcSimulate("sonaronpcsimulated")
	val m = MsgUtil.buildEvent("", "start", "start(1)")
    a.actor.send( m )
	kotlinx.coroutines.delay( 600000 )
}