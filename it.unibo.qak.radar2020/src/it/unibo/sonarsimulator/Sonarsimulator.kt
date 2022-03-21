/* Generated by AN DISI Unibo */ 
package it.unibo.sonarsimulator

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonarsimulator ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 val ResultMap =   hashMapOf( \"key1\" to \"item1\" )  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("demo STARTS working using dispatch on 45")
						delay(4000) 
						forward("polar", "polar(0,45)" ,"radargui" ) 
						delay(500) 
						forward("polar", "polar(45,45)" ,"radargui" ) 
						delay(500) 
						forward("polar", "polar(90,45)" ,"radargui" ) 
						delay(500) 
						forward("polar", "polar(120,45)" ,"radargui" ) 
					}
				}	 
				state("workUsingRequest") { //this:State
					action { //it:State
						utils.sonarDataSimulator.sonarValFromUser( ResultMap, "sonarVal"  )
						 val Result = ResultMap.remove(\"sonarVal\")  
						request("polar", "polar($Result,0)" ,"radargui" )  
						stateTimer = TimerActor("timer_workUsingRequest", 
							scope, context!!, "local_tout_sonarsimulator_workUsingRequest", 1000.toLong() )
					}
					 transition(edgeName="t03",targetState="handleAnwerTimeout",cond=whenTimeout("local_tout_sonarsimulator_workUsingRequest"))   
					transition(edgeName="t04",targetState="handleRadarReply",cond=whenReply("fromRadarGui"))
				}	 
				state("handleRadarReply") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="workUsingRequest", cond=doswitch() )
				}	 
				state("handleAnwerTimeout") { //this:State
					action { //it:State
						println("WARNING: the radargui does not send the answer ... ")
					}
					 transition( edgeName="goto",targetState="workUsingRequest", cond=doswitch() )
				}	 
				state("workUsingRequestNoWait") { //this:State
					action { //it:State
						request("polar", "polar(30,90)" ,"radargui" )  
						delay(1000) 
						request("polar", "polar(50,90)" ,"radargui" )  
						delay(1000) 
						request("polar", "polar(70,90)" ,"radargui" )  
					}
					 transition( edgeName="goto",targetState="handleAllRadarReply", cond=doswitch() )
				}	 
				state("handleAllRadarReply") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						stateTimer = TimerActor("timer_handleAllRadarReply", 
							scope, context!!, "local_tout_sonarsimulator_handleAllRadarReply", 1000.toLong() )
					}
					 transition(edgeName="t05",targetState="handleAnwerTimeout",cond=whenTimeout("local_tout_sonarsimulator_handleAllRadarReply"))   
					transition(edgeName="t06",targetState="handleAllRadarReply",cond=whenReply("fromRadarGui"))
				}	 
			}
		}
}
