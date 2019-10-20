package it.unibo.kactor

import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.supports.FactoryProtocol
import kotlinx.coroutines.*

/*
Works at node level
*/

class QakContextServer(val ctx: QakContext, scope: CoroutineScope,
                       name:String, val protocol: Protocol ) : ActorBasic( name, scope) {
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol?

    //val connMap : MutableMap<Int, IConnInteraction> = mutableMapOf<Int, IConnInteraction>() //Oct2019


    init {
        System.setProperty("inputTimeOut", QakContext.workTime.toString() )  //10 minuti
        factoryProtocol = MsgUtil.getFactoryProtocol(protocol)
        scope.launch(Dispatchers.IO) {
            autoMsg( "start", "startQakContextServer" )
        }
    }


    override suspend fun actorBody(msg : ApplMessage){
        //println("      QakContextServer $name receives $msg  ")
        waitForConnection()
    }

    suspend protected fun waitForConnection() {
        //We could handle several connections
        GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    //println("       QakContextServer $name | WAIT FOR CONNECTION")
                    val conn = factoryProtocol!!.createServerProtocolSupport(ctx.portNum) //BLOCKS
                    sysUtil.connActive.add(conn)
                    handleConnection(conn, ctx.portNum)
                }
            } catch (e: Exception) {
                 println("      QakContextServer $name | WARNING: ${e.message}")
            }
        }
    }
/*
EACH CONNECTION WORKS IN ITS OWN COROUTINE
 */
    suspend protected fun handleConnection(conn: IConnInteraction, ctxport : Int) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                sysUtil.traceprintln("               %%% QakContextServer $name | new conn:$conn")
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    sysUtil.traceprintln("               %%% QakContextServer  $name | receives:$msg in ${sysUtil.curThread()}")
                    if( msg != null ) {
                        val inputmsg = ApplMessage(msg)
                        if (inputmsg.msgType() == ApplMessageType.event.toString()) {
                            propagateEvent(inputmsg)
                            continue
                        }
                        val dest = inputmsg.msgReceiver()
                        val actor = ctx.hasActor(dest)
                        if (actor is ActorBasic) {
                            try {
                                if (inputmsg.msgType() == ApplMessageType.request.toString()) { //Oct2019
                                    //set conn in the msg to the actor
                                    inputmsg.conn = conn
                                }
                                MsgUtil.sendMsg(inputmsg, actor)
                            } catch (e1: Exception) {
                                println("               %%% QakContextServer $name |  ${e1.message}")
                            }
                        } else println("               %%% QakContextServer $name | WARNING!! no local actor ${dest} in ${ctx.name}")
                    }// msg != null
                    else{
                        conn.closeConnection()
                        sysUtil.connActive.remove(conn)
                        break
                    }
                }
            } catch (e: Exception) {
                println("               %%% QakContextServer $name | handleConnection: ${e.message}")
                sysUtil.connActive.remove(conn)
            }
        }
    }//handleConnection

    suspend fun propagateEvent(event : ApplMessage){
         sysUtil.traceprintln("               %%% QakContextServer $name | propagateEvent ctx.proxyMap.size=${ctx.proxyMap.size}")
         ctx.actorMap.forEach{
             //sysUtil.traceprintln("       QakContextServer $name | in ${ctx.name} propag $event to ${it.key} in ${it.value.context.name}")
             val a = it.value
             try{
                 a.actor.send(event)
             }catch( e1 : Exception) {
                println("               %%% QakContextServer $name | propagateEvent WARNING: ${e1.message}")
             }
             //val ctx = sysUtil.getActorContext(a.name)
             //if(  )
         }
        //an event coming from outside is PROPAGATED TO CALLERS ????
        /*
         sysUtil.connActive.forEach{
             println("               %%% QakContextServer $name | propagateEvent on conn ${it}")
             it.sendALine(event.toString())
         }
        */
         /*
          * If the event is generated by an external caller, the actors working
          * in external contexts are not reached
          */
        /*
        for (proxy in ctx.proxyMap) {
            println("               %%% QakContextServer $name | propagateEvent to ${proxy} , ${proxy is ActorBasic}  ")
            if( proxy is ActorBasic ) {
                println("               %%% QakContextServer $name | propagateEvent ${proxy.name}")
                proxy.actor.send( event  )
            }
        }
        */
         /*
         ctx.actorMap.values.forEach{
             val ctx = sysUtil.getActorContext(it.name)
             if( ctx == null ){
                 println( "found  external ${it.name} }
             }
         }
        */
    }
}

