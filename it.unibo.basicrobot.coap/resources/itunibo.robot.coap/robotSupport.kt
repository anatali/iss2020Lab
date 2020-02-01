package itunibo.robot.coap

import it.unibo.kactor.ActorBasic
import itunibo.robotVirtual.virtualRobotSupport
import itunibo.robotMbot.mbotSupport
import itunibo.robotsupport.interfaces.robotsupport

object robotSupport{
lateinit private var cursupport : robotsupport
	
	fun createVirtualRobotSupport( owner: ActorBasic ){
		virtualRobotSupport.create( owner  )
		virtualRobotSupport.init( "8999"  )
		cursupport = virtualRobotSupport
	}
	
	fun createMbottSupport( owner: ActorBasic, port : String){
		mbotSupport.create( owner )
		mbotSupport.init( port )
		cursupport = mbotSupport
	}
	
	fun move( cmd: String) {
		cursupport.move( cmd )
	}
}