package itunibo.robot
/*
 -------------------------------------------------------------------------------------------------
 A factory that creates the proper support for each specific robot type
 
 NOV 2019:
 The operation create accept as last argumnt (filter) an ActorBasic to be used
 as a data-stream handler
 
 The operation subscribeToFilter subscribes to the given data-stream handler
 (fliter) another ActorBaisic that should work as a data-stream handler
 -------------------------------------------------------------------------------------------------
 */

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ActorBasicFsm
import itunibo.robotVirtual.clientWenvObjTcp
import itunibo.robot.rx.ApplActorDataStream
 
object robotSupport{
	lateinit var robotKind    :  String
	var datafilter   :  ActorBasic? = null 
	
	fun create( actor: ActorBasic, robot : String, port: String, filter: ActorBasic? = null ){
		robotKind  = robot
		datafilter =  filter 
		println( "		--- robotSupport | CREATED for $robotKind" )
		when( robotKind ){
			"virtual"    ->  { clientWenvObjTcp.initClientConn( actor, "localhost", port) }
			"realmbot"   ->  { itunibo.robotMbot.mbotSupport.create( actor, port, filter  ) }
			//port="/dev/ttyUSB0"   "COM6"
			"realnano"   ->  { it.unibo.robotRaspOnly.nanoSupport.create(actor, filter, true ) }
			else -> println( "		--- robotSupport | robot unknown" )
		}
	}
	
	fun subscribeToFilter( obj : ActorBasic )  {
		if( datafilter != null ) datafilter!!.subscribe( obj )
	}
	
	fun move( cmd : String ){ //cmd = msg(M) M=w | a | s | d | h
		//println("robotSupport move cmd=$cmd robotKind=$robotKind" )
		when( robotKind ){
			"virtual"  -> { clientWenvObjTcp.sendMsg(  cmd ) }	
			"realmbot" -> { itunibo.robotMbot.mbotSupport.move( cmd ) }
			"realnano" -> { it.unibo.robotRaspOnly.nanoSupport.move( cmd ) }
			else       -> println( "		--- robotSupport | robot unknown" )
		}
		
	}
	
}