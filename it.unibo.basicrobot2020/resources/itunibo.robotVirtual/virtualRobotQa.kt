package itunibo.robotVirtual
import it.unibo.kactor.*
import alice.tuprolog.*

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
        //println("		--- virtualRobotQa | received  msg= $msg "  ) //msg.msgContent()=cmd(X)
		val move = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()
		itunibo.robot.robotSupport.move( move  )
    }
}