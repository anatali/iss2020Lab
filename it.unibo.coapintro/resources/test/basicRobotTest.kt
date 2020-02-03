package test

import org.eclipse.californium.core.CoapClient
import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapResponse
import kotlinx.coroutines.runBlocking
import org.eclipse.californium.core.coap.MediaTypeRegistry
import it.unibo.kactor.MsgUtil

object basicRobotTest{
lateinit var client : CoapClient
lateinit var host   : String
	
	fun init( address : String ){
		host = address
 	}
	
	private fun setClientForPath( path : String ){
		val url = host + "/" + path
		println("coapSupport | setClientForPath url=$url")
		client = CoapClient( url )
		client.setTimeout( 1000L )
	}
	
	fun updateResource(  path: String, msg : String ){
		setClientForPath( path )
		println("coapSupport | updateResource $msg $client")
 		val d = MsgUtil.buildDispatch("test", "cmd", "cmd($msg)", "basicrobot" )
		val resp : CoapResponse = client.put(d.toString(), MediaTypeRegistry.TEXT_PLAIN)
		println("coapSupport | updateResource respCode=${resp.getCode()}")
	}
	
	fun readResource(   path : String ){
		setClientForPath( path )
		val respGet : CoapResponse = client.get( )
		val v = respGet.getResponseText()
		println("coapSupport | readResource v=$v  | respGet=$respGet")
	}
}

fun main() = runBlocking{
	basicRobotTest.init("coap://localhost:8018")
//	basicRobotTest.readResource("example")
 	basicRobotTest.readResource("ctxbasicrobot/basicrobot")
 	basicRobotTest.updateResource("ctxbasicrobot/basicrobot", "r")
}