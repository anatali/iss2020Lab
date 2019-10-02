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
						solve("consult('basicRobotConfig.pl')","") //set resVar	
						solve("robot(R,PORT)","") //set resVar	
						if(currentSolution.isSuccess()) { println("USING:${getCurSol("R")},port=${getCurSol("PORT")}")
						itunibo.robot.robotSupport.create(myself ,getCurSol("R").toString(), getCurSol("PORT").toString() )
						 }
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						println("robot waiting")
					}
					 transition(edgeName="t00",targetState="handleCmd",cond=whenDispatch("cmd"))
					transition(edgeName="t01",targetState="handleObstacle",cond=whenEvent("sonarRobot"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("cmd(X)"), Term.createTerm("cmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								itunibo.robot.robotSupport.move( payloadArg(0)  )
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleObstacle") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						itunibo.robot.robotSupport.move( "h"  )
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}