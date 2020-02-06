package itunibo.robot.httpserver

import com.sun.net.httpserver.HttpServer
import java.io.PrintWriter
import java.io.OutputStream
import java.net.InetSocketAddress
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.File
import java.io.FileInputStream
import java.io.BufferedInputStream
import consolegui.consoleGuiTcp
import com.sun.net.httpserver.Headers
import consolegui.consoleGuiBase
import consolegui.ConnectionType
import consolegui.consoleGuiCoap
import consolegui.consoleGuiMqtt
import java.net.InetAddress
 
 
/**
 * The com.sun.net.httpserver package was introduced with JDK 1.6
 * and enables the implementation of embedded (very low-level and) HTTP servers.
 */
 

lateinit var console : consoleGuiBase

fun answerWithTheGui(t : HttpExchange ){
		try{
			val h = t.getResponseHeaders() 			//Headers
			h.add("Content-Type", "text/html")
 			val file = File ("./resources/itunibo/robot/httpserver/index.html")
			val  fis = FileInputStream(file) 		//FileInputStream
			doResponse( t, BufferedInputStream(fis), file.length() )	
		}catch( e : Exception){
			println("httpserver | answerWithGui ERROR=${e.message}")
		}
}
fun doResponse( t : HttpExchange, bis : BufferedInputStream, flength : Long  ){
		try{
  			val  v   = bis.readBytes() 
  			t.sendResponseHeaders( 200, flength )
	        val os = t.getResponseBody() //OutputStream
			//println("httpserver | doResponse=$os")
	        os.write( v )
	        os.close();
		}catch( e : Exception){
			println("httpserver | doResponse ERROR=${e.message}")
		}
	
}
 
class EntryHandler  : HttpHandler {
    override fun handle( t : HttpExchange )  {
		answerWithTheGui( t )
     }
}

class GetHandlerPdf  : HttpHandler {
    override fun handle( t : HttpExchange )  {
		val h = t.getResponseHeaders() 			//Headers
  		h.add("Content-Type", "application/pdf")
 		// a PDF
		try{
			val file = File ("./resources/itunibo/robot/httpserver/TFCE2020.pdf")
			doResponse( t, BufferedInputStream( FileInputStream(file) ), file.length() )
		}catch( e : Exception){
			println("httpserver | ERROR=${e.message}")
		}
     }
}

class RobotMoveHandler( val move: String )  : HttpHandler {
     override fun handle( t : HttpExchange )  {
	   //Forward to the basicrobot
	   println("RobotMoveHandler move=$move console=$console")
	   console.forward(move)
	   answerWithTheGui( t )
     }
  }

fun init( destName : String, portNum : Int, connectionType : ConnectionType=ConnectionType.COAP ){
	when( connectionType ){
		ConnectionType.TCP  -> {
			console = consoleGuiTcp()
			console.createNoGui("localhost","$portNum",destName)
		}
		
		ConnectionType.COAP -> {
			console = consoleGuiCoap()
			console.createNoGui("localhost","$portNum",destName)
		}  
		ConnectionType.MQTT -> {
			console = consoleGuiMqtt()
			console.createNoGui("localhost","$portNum",destName)
		}
	}
	//console.createNoGui("localhost", "8018", "basicrobot")  //exclude on Rasp
	val myhost = InetAddress.getLocalHost()
	println("httpserver | available on $myhost:8080 console=$console") 
    val server  = HttpServer.create(InetSocketAddress(8080), 0)
 
	server.setExecutor(null)  						// creates a default executor

	server.createContext("/",   EntryHandler())
	server.createContext("/w",  RobotMoveHandler("w"))
	server.createContext("/s",  RobotMoveHandler("s"))
	server.createContext("/r",  RobotMoveHandler("r"))
	server.createContext("/l",  RobotMoveHandler("l"))
	server.createContext("/x",  RobotMoveHandler("x"))
	server.createContext("/z",  RobotMoveHandler("z"))
	server.createContext("/h",  RobotMoveHandler("h"))
 
 	server.createContext("/pdf", GetHandlerPdf())
    server.createContext("/hello") { http ->
        http.responseHeaders.add("Content-type", "text/plain")
        http.sendResponseHeaders(200, 0)
        PrintWriter(http.responseBody).use { out ->
            out.println("Hello from:${http.remoteAddress} hostName=${http.remoteAddress.hostName} ")
        }
    }
 
    server.start()
	
}
	

fun main( ) {
    //init( ConnectionType.TCP )
}