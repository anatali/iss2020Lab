package it.unibo.eclipse.qak.robotMinds19

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.After
import org.junit.Before
import org.junit.Test
import alice.tuprolog.SolveInfo
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class TestRobcmd {
 	//var resource : ActorBasic? = null
	//var resource : it.unibo.robot.Robot? = null
	
	@Before
	fun systemSetUp() {
  	 		GlobalScope.launch{
 			    println(" %%%%%%% TestRobcmd starts   ")
				it.unibo.ctxRobcmd.main()
 			}
 			delay(2000)		//give the time to start
 	}
 
	@After
	fun terminate() {
		println(" %%%%%%% TestRobcmd terminate ")
	}
 
	@Test
	fun moveTest() {
		val finalRobotState = itunibo.robcmd.robotState.stateVal
		println(" %%%%%%% TestRobcmd  moveTest ${ finalRobotState }")
		assertTrue("", finalRobotState==itunibo.robcmd.robotState.stateRepForward )
 	}


	fun delay( time : Long ){
		Thread.sleep( time )
	}


}