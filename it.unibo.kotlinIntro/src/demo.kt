import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun curThread() : String { 
	return "thread=${Thread.currentThread().name}" 
}

suspend fun delayedFun(){
    println("delayedFun STARTS ${curThread()}")
    kotlinx.coroutines.delay(500)
    println("delayedFun ENDS ")
}
fun main() = runBlocking{
    println("BEGINS ${curThread()}")
    delayedFun()
    println("ENDS")
}
