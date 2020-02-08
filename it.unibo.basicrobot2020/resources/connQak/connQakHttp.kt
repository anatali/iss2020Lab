package connQak

import java.net.URL
import java.net.HttpURLConnection

class connQakHttp(hostIP : String,  port : String,  destName : String ) :
										           connQakBase(hostIP, port, destName){
	lateinit  var url : URL 
	
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
	
	override fun createConnection(  ){
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
	
	override fun request( move : String ){
		
	}
	
	override fun emit( ev : String ){
		
	}	
}