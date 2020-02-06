package httpserver

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
 
/**
 * The com.sun.net.httpserver package was introduced with JDK 1.6
 * and enables the implementation of embedded (very low-level and) HTTP servers.
 */

val console = consoleGuiTcp()


class InfoHandler  : HttpHandler {
    override fun handle( t : HttpExchange )  {
	   val curDir = System.getProperty("user.dir")
	   //println("httpserver | curDir=$curDir")
       val response = "Use /get to download a PDF. curdir=$curDir"
       t.sendResponseHeaders(200, response.length.toLong());
       val os = t.getResponseBody() //OutputStream
       os.write(response.toByteArray())
       os.close();
    }
  }

/*
 File file = new File("index.html");
he.sendResponseHeaders(200, file.length());
try (OutputStream os = he.getResponseBody()) {
    Files.copy(file.toPath(), os);
}
 */
class GetHandler  : HttpHandler {
    override fun handle( t : HttpExchange )  {
		val h = t.getResponseHeaders() 			//Headers
 		h.add("Content-Type", "text/html")
		// HTML
		try{
 			val file = File ("./resources/httpserver/index.html")
			val  fis = FileInputStream(file) 		//FileInputStream
			val  bis = BufferedInputStream(fis) 	//BufferedInputStream
 			val  v   = bis.readBytes() 
  			t.sendResponseHeaders(200, file.length())
	        val os = t.getResponseBody() //OutputStream
			//println("httpserver | os=$os")
	        os.write( v )
	        os.close();
		}catch( e : Exception){
			println("httpserver | ERROR=${e.message}")
		}
     }
}

class GetHandlerPdf  : HttpHandler {
    override fun handle( t : HttpExchange )  {
		val h = t.getResponseHeaders() 			//Headers
  		h.add("Content-Type", "application/pdf")
 		// a PDF
		try{
			val file = File ("./resources/httpserver/TFCE2020.pdf")
  			val  bis = BufferedInputStream( FileInputStream(file) ) 	//BufferedInputStream
 			val  v   = bis.readBytes() 
  			t.sendResponseHeaders(200, file.length())
	        val os = t.getResponseBody() //OutputStream
 	        os.write( v )
	        os.close();
		}catch( e : Exception){
			println("httpserver | ERROR=${e.message}")
		}
     }
}

class RobotMoveHandler( val move: String )  : HttpHandler {
     override fun handle( t : HttpExchange )  {
	   //Forward to the basicrobot
	   console.forward(move)
       val response = "$move done"
       t.sendResponseHeaders(200, response.length.toLong());
       val os = t.getResponseBody() //OutputStream
       os.write(response.toByteArray())
       os.close();
    }
  }

	

fun main(args: Array<String>) {
	console.createNoGui("localhost", "8018", "basicrobot")
	println("httpserver | start") 
    val server  = HttpServer.create(InetSocketAddress("localhost", 8080), 0)

	server.setExecutor(null)  						// creates a default executor

	server.createContext("/",  GetHandler())
	server.createContext("/w",  RobotMoveHandler("w"))
	server.createContext("/s",  RobotMoveHandler("s"))
	server.createContext("/r",  RobotMoveHandler("r"))
	server.createContext("/l",  RobotMoveHandler("l"))
	server.createContext("/x",  RobotMoveHandler("x"))
	server.createContext("/z",  RobotMoveHandler("z"))
	server.createContext("/h",  RobotMoveHandler("h"))
 	server.createContext("/info", InfoHandler())
 	server.createContext("/get", GetHandlerPdf())
    server.createContext("/hello") { http ->
        http.responseHeaders.add("Content-type", "text/plain")
        http.sendResponseHeaders(200, 0)
        PrintWriter(http.responseBody).use { out ->
            out.println("Hello from:${http.remoteAddress} hostName=${http.remoteAddress.hostName} ")
        }
    }
 
    server.start()
    
}