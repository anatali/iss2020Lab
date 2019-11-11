/* Generated by AN DISI Unibo */ 
package it.unibo.demousage

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Demousage ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 val ResultMap =   hashMapOf( "key1" to "item1" ) 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("demousage start")
					}
					 transition( edgeName="goto",targetState="workUsingDispatch", cond=doswitch() )
				}	 
				state("workUsingDispatch") { //this:State
					action { //it:State
						forward("polar", "polar(0,0)" ,"radar" ) 
						delay(500) 
						forward("polar", "polar(45,0)" ,"radar" ) 
						delay(500) 
						forward("polar", "polar(90,0)" ,"radar" ) 
						delay(500) 
						forward("polar", "polar(120,0)" ,"radar" ) 
					}
					 transition( edgeName="goto",targetState="workUsingRequest", cond=doswitch() )
				}	 
				state("workUsingRequest") { //this:State
					action { //it:State
						utils.sonarDataSimulator.sonarValFromUser( ResultMap, "sonarVal"  )
						 val Result = ResultMap.remove("sonarVal")
						request("polar", "polar($Result,0)" ,"radar" )  
						stateTimer = TimerActor("timer_workUsingRequest", 
							scope, context!!, "local_tout_demousage_workUsingRequest", 1000.toLong() )
					}
					 transition(edgeName="t00",targetState="handleAnwerTimeout",cond=whenTimeout("local_tout_demousage_workUsingRequest"))   
					transition(edgeName="t01",targetState="handleRadarReply",cond=whenReply("fromRadar"))
				}	 
				state("handleRadarReply") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="workUsingRequest", cond=doswitch() )
				}	 
				state("handleAnwerTimeout") { //this:State
					action { //it:State
						println("WARNING: the radar does not send the answer ... ")
					}
					 transition( edgeName="goto",targetState="workUsingRequest", cond=doswitch() )
				}	 
			}
		}
}