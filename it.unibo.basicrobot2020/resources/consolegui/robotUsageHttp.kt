import java.net.URL
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.io.BufferedReader
import java.io.InputStreamReader
import org.apache.http.client.CredentialsProvider
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.auth.AuthScope
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import org.apache.http.client.HttpClient
import java.io.InputStream

lateinit var client : HttpClient 
val hostAddr ="http://localhost:8080"

fun createClient(){
// val provider    =  BasicCredentialsProvider() //CredentialsProvider
// val credentials =  UsernamePasswordCredentials("webiopi", "raspberry") //UsernamePasswordCredentials
// provider.setCredentials(AuthScope.ANY, credentials)
// client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build() //HttpClient
	client = HttpClientBuilder.create().build()
    println("client created")
}

fun testGet(){
 val path = "GPIO/17/value"
 val response = client.execute(  HttpGet(hostAddr+"/$path") ) //HttpResponse
 showResponse( "GET $path", response.getEntity().getContent() )
}

fun testPost( v : String){
 println("testPost $v ")
 val response = client.execute( HttpPost(hostAddr+"/$v")) //HttpResponse
	
 showResponse( "POST $v", response.getEntity().getContent() )
}

 

fun showResponse(  msg : String, inps : InputStream ){
	println("Response msg=$msg inps=$inps")
	inps.bufferedReader().use {
		val response = StringBuffer()
		var inputLine = it.readLine()
		while (inputLine != null) {
			response.append(inputLine)
			inputLine = it.readLine()
		}
		it.close()
		println(response.toString())
	}	
}

 

 

fun main() = runBlocking {
	createClient()
    testPost("hello")
	delay(1000)
    testPost("r")
	
}


/*
fun sendGet( userName:String ="webiopi", password:String="raspberry" ) {
	
//     var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
//     reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")

	 var reqParam = URLEncoder.encode("", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
     reqParam += "&" + URLEncoder.encode("", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")

	
	 val url = URL("http://192.168.1.5:8000/GPIO/17/value?" + reqParam)
	 val conn=url.openConnection()
     with(conn as HttpURLConnection) {
		 conn.setDoOutput(true)
        requestMethod = "GET"  // optional default is GET

        println("GET to URL : $url | Response Code : $responseCode")
        if( responseCode != 401 ){
//			getResponse( conn )
        	if( responseCode == 400 ){ println("BAD REQUEST"); return }
	        inputStream.bufferedReader().use {
	            it.lines().forEach { line ->
	                println(line)
	            }
	        }
		}else{
			println("UNAUTHORIZED")
		}
    }
}



fun sendPost() {
    val url = URL("http:///192.168.1.5:8000/GPIO/17/value")

    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "GET"  // optional default is GET

        println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

        inputStream.bufferedReader().use {
            it.lines().forEach { line ->
                println(line)
            }
        }
    }
}
*/
