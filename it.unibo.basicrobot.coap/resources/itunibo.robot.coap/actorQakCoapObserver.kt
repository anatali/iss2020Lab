package itunibo.robot.coap

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.MediaTypeRegistry
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.ApplMessage
import java.util.Scanner
import org.eclipse.californium.core.CoapHandler 
 
object actortQakCoapObserver {

    private val client = CoapClient()
	
	private val ipaddr      = "localhost:5683"
	private val context     = "ctxcoapdemo"
 	private val destactor   = "actor1"
	private val msgId       = "cmd"

	fun init(){
       val uriStr = "coap://$ipaddr/$context/$destactor"
	  println("actortQakCoapObserver START uriStr: $uriStr")
       client.uri = uriStr
       client.observe(object : CoapHandler {
            override fun onLoad(response: CoapResponse) {
                println("ASYNCH GET RESP-CODE= " + response.code + " content:" + response.responseText)
            }
            override fun onError() {
                println("FAILED")
            }
        })		
	}

 }

 
 fun main( ) {
		actortQakCoapObserver.init()
		System.`in`.read()   //to avoid exit
 }

