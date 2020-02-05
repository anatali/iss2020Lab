package consolegui

import it.unibo.supports.FactoryProtocol
import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.kactor.MsgUtil
 
class consoleGuiTcp() : consoleGuiBase() {
	
	lateinit var conn         : IConnInteraction
 	
	 override fun createConnection( hostIP: String, port: String  ){
		val fp    = FactoryProtocol(null,"TCP","gui")
	    conn      = fp.createClientProtocolSupport(hostIP, port.toInt() )    
	 } 

	 override fun forward( move : String ){
		val msg = MsgUtil.buildDispatch("gui","cmd","cmd($move)", destName)
		conn.sendALine( msg.toString()  )		
	}
	
	override fun request(move: String) { //move == p
		val msg = MsgUtil.buildRequest("gui", move,"$move(600)", destName)
		conn.sendALine( msg.toString()  )
		//Acquire the answer	
		val answer = conn.receiveALine()
		println("consoleGuiTcp | answer= $answer")
	}
	override fun emit( ev : String ){
		val msg = MsgUtil.buildEvent("gui",ev,"$ev(0)" )
		conn.sendALine( msg.toString()  )	
	}	
}

 
fun main(){
	consoleGuiBase.createGui( GuiType.TCP, "localhost", "8018", "basicrobot")
}
 