package utils

//import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
 
//import it.unibo.kactor.MsgUtil
  
object sonarDataSimulator {
var dataCounter = 1	
    fun sonarValFromUser( result : MutableMap<String,String> , key : String  ){
		print("\nsonarValFromUser>" )
    	var data = readLine()
        //println("data ${dataCounter++} = $data " )
		result.put( key, "$data" )
     }

}
 