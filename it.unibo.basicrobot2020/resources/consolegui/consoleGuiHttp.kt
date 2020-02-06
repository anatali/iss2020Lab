package consolegui

import it.unibo.supports.FactoryProtocol
import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.kactor.MsgUtil
import java.net.URL
import java.net.HttpURLConnection
 
class consoleGuiHttp() : consoleGuiBase() {
	lateinit  var url : URL 

	companion object{
		fun create(port: String = "8080", destActor: String ="basicrobot"){
			consoleGuiBase.createGui( ConnectionType.HTTP, "localhost", port, destActor)
		}
	}
	

	 override fun createConnection( hostIP: String, port: String  ){
		 url = URL("http://$hostIP:$port")
		 sendGet()
 	 } 

	 override fun forward( move : String ){
		//val msg = MsgUtil.buildDispatch("gui","cmd","cmd($move)", destName)
		with(url.openConnection() as HttpURLConnection) {
	        requestMethod = "PUT"  // optional default is GET
	
	        println("\nSent 'PUT' request to URL : $url/$move; Response Code : $responseCode")
	
	        inputStream.bufferedReader().use {
	            it.lines().forEach { line ->
	                println(line)
	            }
	        }
		}
	 }
	override fun request(move: String) { //move == p
//		val msg = MsgUtil.buildRequest("gui", move,"$move(600)", destName)
//		conn.sendALine( msg.toString()  )
//		//Acquire the answer	
//		val answer = conn.receiveALine()
//		println("consoleGuiTcp | answer= $answer")
	}
	override fun emit( ev : String ){
//		val msg = MsgUtil.buildEvent("gui",ev,"$ev(0)" )
//		conn.sendALine( msg.toString()  )	
	}
	
	fun sendGet() {
		with(url.openConnection() as HttpURLConnection) {
	        requestMethod = "GET"  // optional default is GET
	        println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
	        inputStream.bufferedReader().use {
	            it.lines().forEach { line ->
	                println(line)
	            }
	        }
    	}//with
	}//sendGet
}

 
fun main(){
	consoleGuiBase.createGui( ConnectionType.HTTP, "localhost", "8080", "basicrobot")
}
 