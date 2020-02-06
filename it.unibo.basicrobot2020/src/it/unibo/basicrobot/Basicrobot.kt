/* Generated by AN DISI Unibo */ 
package it.unibo.basicrobot

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Basicrobot ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						  
						//The PIPE could be completely created by the robotAdapterQaStream
						//WARNING: use myself to denote the basicrobot actor, since this refers to the state
						 
						//val filter   = itunibo.robot.rx.sonaractorfilter("filter", myself)  //generates obstacle
						//val logger   = itunibo.robot.rx.Logger("logger")
						val forradar = itunibo.robot.rx.sonarforradar("forradar", myself)  //generates polar
						itunibo.robot.robotSupport.subscribe( forradar ) 
						
						itunibo.robot.httpserver.init(consolegui.ConnectionType.TCP)
						println("	basicrobot | starts (with robotadapter in the same context)")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						println("basicrobot waiting ... ")
					}
					 transition(edgeName="t00",targetState="handleCmd",cond=whenDispatch("cmd"))
					transition(edgeName="t01",targetState="handleUserCmd",cond=whenEvent("userCmd"))
					transition(edgeName="t02",targetState="handleObstacle",cond=whenEvent("obstacle"))
					transition(edgeName="t03",targetState="handleAlarm",cond=whenEvent("alarm"))
					transition(edgeName="t04",targetState="handleRequest",cond=whenRequest("p"))
				}	 
				state("handleRequest") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("p(TIME)"), Term.createTerm("p(T)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								val Steptime = payloadArg(0).toLong()
								forward("cmd", "cmd(w)" ,"robotadapter" ) 
								delay(Steptime)
								forward("cmd", "cmd(h)" ,"robotadapter" ) 
								println("basicrobot | reply ... ")
								answer("p", "pDone", "pDone(0)"   )  
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("cmd", "cmd(r)" ,"robotadapter" ) 
						delay(800) 
						forward("cmd", "cmd(r)" ,"robotadapter" ) 
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("cmd(X)"), Term.createTerm("cmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("cmd", "cmd(${payloadArg(0)})" ,"robotadapter" ) 
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleUserCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("userCmd(X)"), Term.createTerm("userCmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("cmd", "cmd(${payloadArg(0)})" ,"robotadapter" ) 
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleObstacle") { //this:State
					action { //it:State
						forward("cmd", "cmd(h)" ,"robotadapter" ) 
						println("	basicrobot | stops (for safety) since  obstacle  ")
					}
					 transition( edgeName="goto",targetState="movefarFromObstacle", cond=doswitch() )
				}	 
				state("movefarFromObstacle") { //this:State
					action { //it:State
						println("	basicrobot | going back (to avoid event-generation) ")
						forward("cmd", "cmd(s)" ,"robotadapter" ) 
						delay(100) 
						forward("cmd", "cmd(h)" ,"robotadapter" ) 
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}
