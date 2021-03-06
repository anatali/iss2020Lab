/* Generated by AN DISI Unibo */ 
package it.unibo.blscontrol

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Blscontrol ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="turnOn",cond=whenEvent("local_buttonCmd"))
				}	 
				state("turnOn") { //this:State
					action { //it:State
						forward("ledCmd", "ledCmd(on)" ,"led" ) 
					}
					 transition(edgeName="t11",targetState="turnOff",cond=whenEvent("local_buttonCmd"))
				}	 
				state("turnOff") { //this:State
					action { //it:State
						forward("ledCmd", "ledCmd(off)" ,"led" ) 
					}
					 transition(edgeName="t12",targetState="s0",cond=whenEvent("local_buttonCmd"))
				}	 
			}
		}
}
