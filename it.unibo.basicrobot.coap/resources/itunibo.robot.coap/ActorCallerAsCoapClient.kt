package itunibo.robot.coap

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.MediaTypeRegistry

object ActorCallerAsCoapClient {


    fun delay(time: Int) {
        try {
            Thread.sleep(time.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    fun usingRoot(client: CoapClient) {
        val uriStr = "coap://localhost:5683/ctxcoapdemo"
        println("USING: $uriStr")
        client.uri = uriStr
        val respGet = client.get()
        println("GET RESPONSE CODE= " + respGet.code + " content:" + respGet.responseText)

 
//        client.get(object : CoapHandler {
//            override fun onLoad(response: CoapResponse) {
//                println("ASYNCHRONOUS GET RESPONSE CODE= " + response.code + " content:" + response.responseText)
//            }
//
//            override fun onError() {
//                System.err.println("FAILED")
//            }
//        })
        //Give time to acquire asynch GET
        delay(1000)
    }

    fun useactor0(client: CoapClient) {
        val uriStr = "coap://localhost:5683/ctxcoapdemo/actor0"
        client.uri = uriStr
        println("USING: $uriStr")
        val respGet = client.get()
        println("GET RESPONSE CODE= " + respGet.code + " content:" + respGet.responseText)

        val respPut = client.put("r", MediaTypeRegistry.TEXT_PLAIN)
        println("PUT inc RESPONSE CODE=" + respPut.code)

        client.get(object : CoapHandler {
            override fun onLoad(response: CoapResponse) {
                println("ASYNCHRONOUS GET RESPONSE CODE= " + response.code +
						" content:" + response.responseText)
            }

            override fun onError() {
                System.err.println("FAILED")
            }
        })
        //Give time to acquire asynch GET
        delay(1000)
    }


}
     fun main( ) {
         val client = CoapClient()
        ActorCallerAsCoapClient.usingRoot(client)
        println("-------------------------")
        ActorCallerAsCoapClient.useactor0(client)
    }
