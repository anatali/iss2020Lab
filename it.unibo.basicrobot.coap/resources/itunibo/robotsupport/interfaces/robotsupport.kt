package itunibo.robotsupport.interfaces
import it.unibo.kactor.ActorBasic

interface robotsupport{
	
	fun create( owner: ActorBasic)
	
	fun init( arg: String  )
	
	fun move( cmd: String)
}