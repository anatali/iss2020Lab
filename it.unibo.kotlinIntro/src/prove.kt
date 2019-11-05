import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun curThread() : String {
    return "thread=${Thread.currentThread().name}"
}

fun runBlockThread(){
    //Calls the specified function block and returns its result.
    run {
        println("Out start: ${curThread()}")
        Thread.sleep(1500)
        println("Out ended: ${curThread()}")
    }
}

suspend fun ioBoundFun(){
    val timeElapsed = measureTimeMillis {
        println("IO operation ${curThread()}")
        delay(500)
    }
    println("IO operation done, time=$timeElapsed")
}

suspend fun activate(){
    val job1 = GlobalScope.async{
        ioBoundFun()
    }
    val job2 = GlobalScope.async{
        ioBoundFun()
    }
    if(! job1.isCompleted || ! job2.isCompleted)
        println("Waiting for completion")
    val end1 = job1.await()
    val end2 = job2.await()
    println("All jobs done end1=$end1 end2=$end2" )
}
/*
fun main() = runBlocking{
    //GlobalScope.launch{ runBlockThread() }
    //launch{ runBlockThread() }
    //println("BYE")
    println("BEGINS")
    activate()
    println("ENDS")
}

 */
fun main() = runBlocking {
    println("BEGINS ${curThread()}")
    launch { //context of the parent runBlocking
        println("1) thread=${Thread.currentThread().name}")
    }
    launch( Dispatchers.Unconfined) { //in main thread
        println("2) thread=${Thread.currentThread().name}")
    }
    launch( Dispatchers.Default ) { //DefaultDispatcher
        println("3) thread=${Thread.currentThread().name}")
    }
    launch( newSingleThreadContext("MyThr")) { //new thread
        println("4) thread=${Thread.currentThread().name}")
    }
    println("ENDS ${curThread()}")
}

