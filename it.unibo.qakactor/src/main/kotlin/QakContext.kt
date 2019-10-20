package it.unibo.kactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.InetAddress

open class QakContext(name: String, val hostAddr: String, val portNum: Int, var mqttAddr : String = "",
                      val external: Boolean=false, val gui : Boolean = false   ) : ActorBasic(name){

    internal val actorMap : MutableMap<String, ActorBasic> = mutableMapOf<String, ActorBasic>()
    internal val proxyMap:  MutableMap<String, NodeProxy> = mutableMapOf<String, NodeProxy>()  //cannot be static

    companion object {
        val workTime = 600000L
        enum class CtxMsg { attach, remove }

        fun getActor( actorName : String ) : ActorBasic? {
            return sysUtil.getActor(actorName)
        }

        suspend fun createContexts(hostName: String, scope: CoroutineScope,
                           desrFilePath: String, rulesFilePath: String) {
            sysUtil.createContexts(hostName, desrFilePath, rulesFilePath)

            if( sysUtil.ctxOnHost.size == 0 ){
                val ip = InetAddress.getLocalHost().getHostAddress()
                sysUtil.traceprintln("               %%% QakContext | CREATING NO ACTORS on $hostName ip=${ip.toString()}")
            }
            else println("               %%% QakContext | CREATING THE ACTORS on $hostName ")
            sysUtil.ctxOnHost.forEach { ctx -> sysUtil.createTheActors(ctx, scope)  }
            //Avoid premature termination
            scope.launch{
                println("               %%% QakContext |  $hostName CREATED. I will terminate after $workTime msec")
                delay( workTime )
            }
        }
    }

    init{
        //OCT2019
        if( ! external ){
            sysUtil.traceprintln("               %%% QakContext |  $hostAddr:$portNum INIT ")
            QakContextServer( this, GlobalScope, "server$name", Protocol.TCP )
            if( gui ){ }
        }
    }

    override suspend fun actorBody(msg : ApplMessage){
        sysUtil.traceprintln( "               %%% QakContext $name |  receives $msg " )
    }

    fun addCtxProxy(ctxName: String, protocol: String, hostAddr: String, portNumStr: String) {
        val p = MsgUtil.strToProtocol(protocol)
        val portNum = Integer.parseInt(portNumStr)
        //sysUtil.traceprintln("               %%% QakContext $name | addCtxProxy ${ctxName}, $hostAddr, $portNum")\
        val proxy = NodeProxy("proxy${ctxName}", this, p, hostAddr, portNum)
        proxyMap.put(ctxName, proxy)
    }

    fun addActor( actor: ActorBasic ) {
        actor.context = this
        actorMap.put( actor.name, actor )
        actor.checkMqtt()
        //sysUtil.traceprintln("               %%% QakContext $name | addActor ${actor.name}")
    }

    fun hasActor( actorName: String ) : ActorBasic? {
        return actorMap.get(actorName)
    }

    fun addCtxProxy( ctx : QakContext ){
        if( ctx.mqttAddr.length > 1 ) return
        //sysUtil.traceprintln("               %%% QakContext $name | addCtxProxy ${ctx.name}")
        val proxy = NodeProxy("proxy${ctx.name}", this, Protocol.TCP, ctx.hostAddr, ctx.portNum)
        proxyMap.put( ctx.name, proxy )
    }

    fun addCtxProxy( ctxName :String, hostAddr: String, portNum : Int  ){
        val proxy = NodeProxy("proxy${ctxName}", this, Protocol.TCP, hostAddr, portNum)
        proxyMap.put( ctxName, proxy )
    }

}