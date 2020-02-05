package kotlincode

import org.eclipse.californium.core.CoapClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil

class handler: CoapHandler{
 	
	override fun onLoad( response : CoapResponse ) {
		val content = response.getResponseText()
		println("resourceObserver  | HANDLER value=" + content)   
 	}
	override fun onError(){ println("resourceObserver  | HANDLER FAILED ")} 
}

object resourceObserver{
lateinit var client : CoapClient
lateinit var path   : String
			
	fun init( host: String, mypath : String ){   
		path    = mypath
		val url = "$host/$path"
		client =  CoapClient( url )

		observe( )
//		GlobalScope.launch{ System.`in`.read() }
 	} 
	
	fun observe( ){
		println("resourceObserver  | STARTS path=$path"  )
 		client.observe( handler() )
 	}
}

fun main(){
	resourceObserver.init("coap://localhost:8018", "ctxbasicrobot/basicrobot")
	System.`in`.read()
}