/* Generated by AN DISI Unibo */ 
package it.unibo.ctxRobotAppl0
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "sprint3analysis.pl", "sysRules.pl"
	)
}

