/* Generated by AN DISI Unibo */ 
package it.unibo.console

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Console ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("hello from console")
						forward("cmd", "cmd(w)" ,"robot" ) 
						delay(1000) 
						forward("cmd", "cmd(h)" ,"robot" ) 
					}
				}	 
			}
		}
}
