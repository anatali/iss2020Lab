/* Generated by AN DISI Unibo */ 
package it.unibo.radarusagecoap

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Radarusagecoap ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 val ResultMap =   hashMapOf( "key1" to "item1" ) 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("radarusagecoap | start")
						utils.coapActorSupport.connect(myself ,"coap://localhost:5683/robot/sonar" )
						utils.coapActorSupport.observeResource(myself)
					}
					 transition( edgeName="goto",targetState="workUsingCoap", cond=doswitch() )
				}	 
				state("workUsingCoap") { //this:State
					action { //it:State
						utils.coapActorSupport.updateResource(myself ,"80" )
						delay(500) 
						utils.coapActorSupport.readResource(myself ,ResultMap, "sonarVal" )
						 val Result = ResultMap.remove("sonarVal")
						println("radarusagecoap | Data GET from sonar: $Result")
						delay(5000) 
						utils.coapActorSupport.updateResource(myself ,"10" )
						delay(5000) 
						utils.coapActorSupport.updateResource(myself ,"50" )
					}
					 transition(edgeName="t00",targetState="handleSonarData",cond=whenDispatch("sonar"))
				}	 
				state("handleSonarData") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition(edgeName="t01",targetState="handleSonarData",cond=whenDispatch("sonar"))
				}	 
				state("end") { //this:State
					action { //it:State
						println("radarusagecoap | end")
					}
				}	 
			}
		}
}
