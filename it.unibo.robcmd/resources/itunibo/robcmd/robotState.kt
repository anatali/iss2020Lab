package itunibo.robcmd

object robotState {
	val stateRepStopped     = "robot_stopped"
	val stateRepForward     = "robot_moving_forward"
	val stateRepBackward    = "robot_moving_backward"
	val stateRepRotateLeft  = "robot_rotating_left"
	val stateRepRotateRight = "robot_rotating_right"
	
	var stateVal = stateRepStopped
	
	//fun mySetValue( v : String ){ value = v }
	
	fun robotGoingForward( ){ stateVal = stateRepForward }
	 
}