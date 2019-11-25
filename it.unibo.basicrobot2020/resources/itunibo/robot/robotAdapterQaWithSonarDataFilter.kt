package itunibo.robot 
import it.unibo.kactor.*
import alice.tuprolog.*
import itunibo.robot.rx.sonaractorfilter

/*
 * transforms a cmd:cmd(M) into a command M to the
 * concrete robot specified in basicRobotConfig.pl.
 * To achieve the goal, the actor makes use of the itunibo.robot.robotSupport
 * by creating this support with a data filter of class sonaractorfilter
 * that emitLocalStreamEvent   sonarData:sonarData(V) 
 * 
 */
class robotAdapterQaWithSonarDataFilter( name : String ) : ActorBasic( name ){
    
    init{
		println("		--- robotAdapterQaWithSonarDataFilter | start")
		val sol1 = pengine.solve( "consult('basicRobotConfig.pl')." )
		if( ! sol1.isSuccess() ){
			println("		--- robotAdapterQaWithSonarDataFilter | ERROR: basicRobotConfig.pl not found or wrong")
		}else{
	 		val sol2 = pengine.solve( "robot(R, PORT)." )
	 		if( sol2.isSuccess() ){
				val host =  sol2.getVarValue("R").toString()
				val port =  sol2.getVarValue("PORT").toString()
				println("		--- robotAdapterQaWithSonarDataFilter | USING $host : $port")
				val filter = sonaractorfilter("sonardatafilter")
				itunibo.robot.robotSupport.create( this,host,port, filter )
			}
		}		  		      
    }
	
 
    override suspend fun actorBody(msg : ApplMessage){
        println("		--- robotAdapterQaWithSonarDataFilter | received  msg= $msg "  ) //msg.msgContent()=cmd(X)
		if( msg.isEvent() ) return	//robotAdapterQaWithSonarDataFilter receives the events raised by the actors in its context		
		val move = (Term.createTerm(msg.msgContent()) as Struct).getArg(0).toString()
		itunibo.robot.robotSupport.move( move  )
    }
}