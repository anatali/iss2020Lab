package itunibo.robot.rx
/*
fcor each (sonar) value V, perfoms some flitering acion
and - if ok - emitLocalStreamEvent   sonarData:sonarData(V)
*/

import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Term
import alice.tuprolog.Struct
 
class sonaractorfilter (name : String,   
		var LimitDistance : Int = 12, var LastDistance : Int = 0,
		var minDistance  : Int = 2,   var maxDistance  : Int = 50,
		var maxDelta   : Int   = 1 
) : ApplActorDataStream( name ) {

	init{
		println("   $name |  STARTS")
 	}
 	
	override suspend fun elabData( data : String ){
		val Distance = Integer.parseInt( data ) 
 		val delta    = Math.abs( Distance - LastDistance )
		//println("   $name |  elabData delta = $delta isVirtualRobot = $isVirtualRobot")
		var testDelta = delta >= maxDelta  //FOR REAL ROBOT only
 		if( Distance > minDistance && Distance < maxDistance  && testDelta  ){ 
 			//println("   $name |  elabSonarData Distance = $Distance ")
			LastDistance = Distance
 			val m1 = MsgUtil.buildEvent(name, "sonarData", "sonarData($data)")
			println("   ${name} |  emit m1= $m1")
	 		emitLocalStreamEvent( m1 )  					//PROPAGATE to the pipe
    	}else{
			//println("   $name |  DISCARDS $Distance ")
 		}				
	}

}