package itunibo.robotVirtual
import it.unibo.kactor.*

class virtualRobotQa( name : String ) : ActorBasic( name ){
    

    init{
		println("virtualRobotQa | start")
		val sol1 = pengine.solve( "consult('basicRobotConfig.pl')." )
		if( ! sol1.isSuccess() ){
			println("		--- virtualRobotQa | ERROR: basicRobotConfig.pl not found or wrong")
		}else{
	 		val sol2 = pengine.solve( "robot(R, PORT)." )
	 		if( sol2.isSuccess() ){
				val host =  sol2.getVarValue("R").toString()
				val port =  sol2.getVarValue("PORT").toString()
				println("		--- virtualRobotQa | USING $host : $ port")
				itunibo.robot.robotSupport.create( this,host,port )
			}
		}		  		      
    }

    override suspend fun actorBody(msg : ApplMessage){
        println("		--- virtualRobotQa | received  msg= $msg "  )
//        when( msg.msgContent() ){
//        	else -> println("   virtualRobotQak $name | UNKNOWN $msg")
//        }
    }
}