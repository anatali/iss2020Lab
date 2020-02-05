package tester

import consolegui.consoleGuiTcp
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import consolegui.consoleGuiBase

lateinit var console : consoleGuiBase

suspend fun testTcp(){
	console = consoleGuiTcp()
	console.createNoGui("localhost", "8018", "basicrobot")
	interact()
}

suspend fun interact(){
	delay(1000)
	console.forward("r")
	delay(1000)
	console.emit("alarm")
	delay(2000)
	console.request("p")
}

fun main() = runBlocking {
	testTcp()
	delay(1000)
	println("BYE")
}