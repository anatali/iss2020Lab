package highLevelComms

import it.unibo.kactor.MsgUtil
import it.unibo.`is`.interfaces.protocols.IConnInteraction  //WARNING !!!
 
 

class hlComm( val conn : IConnInteraction ) {
	
	fun forward( senderName : String, msgId : String, msgContent : String, destName : String  ){
		val m = MsgUtil.buildDispatch(senderName,msgId,msgContent,destName)
		conn.sendALine( m.toString() )
	}
	
	fun request( senderName : String, msgId : String, msgContent : String, destName : String  ){
		val m = MsgUtil.buildRequest(senderName,msgId,msgContent,destName)
		conn.sendALine( m.toString() )
	}

	fun answer( msgId : String, msgContent : String   ){
		val m = MsgUtil.buildReply("fromcalled",msgId,msgContent,"tocaller")
		conn.sendALine( m.toString() )
	}
	
	fun emit( senderName : String, msgId : String, msgContent : String  ){
		val m = MsgUtil.buildEvent(senderName,msgId,msgContent)
		conn.sendALine( m.toString() )
	}
	
	fun receive() : String{ //Blocking
		return conn.receiveALine()
	}
	
}

var hlCommSupport : hlComm?    =  null  //Visible in the package