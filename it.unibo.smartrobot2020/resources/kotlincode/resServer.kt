package kotlincode

import org.eclipse.californium.core.CoapServer

object resServer{
		fun init(){
			val server = CoapServer();
			server.add( 
				 resRobot("robot").add(	//robot
					 resRobotPosition("pos"), resRobotSonar("sonar") )  //robot/pos, robot/sonar
			)
			server.start();			
		}
}
