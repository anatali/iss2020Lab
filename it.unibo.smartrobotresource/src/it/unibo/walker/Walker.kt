/* Generated by AN DISI Unibo */ 
package it.unibo.walker

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Walker ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var NumOfRotations = 0 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition(edgeName="t08",targetState="walk",cond=whenEvent("boundary"))
				}	 
				state("walk") { //this:State
					action { //it:State
						delay(1000) 
						request("onestep", "onestep(380)" ,"smartrobot" )  
					}
					 transition(edgeName="t09",targetState="walk",cond=whenReply("stepdone"))
					transition(edgeName="t010",targetState="rotate",cond=whenReply("stepfail"))
				}	 
				state("rotate") { //this:State
					action { //it:State
						println("WALKER | ==================================== rotate ")
						NumOfRotations++
						forward("cmd", "cmd(l)" ,"smartrobot" ) 
						delay(500) 
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitchGuarded({(NumOfRotations==4)}) )
					transition( edgeName="goto",targetState="walk", cond=doswitchGuarded({! (NumOfRotations==4)}) )
				}	 
			}
		}
}