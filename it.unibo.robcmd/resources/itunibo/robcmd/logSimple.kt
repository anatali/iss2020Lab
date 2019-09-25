package itunibo.robcmd

import java.io.PrintWriter
import java.io.FileWriter
import java.io.File
import java.io.InputStream

object logSimple {
 	val pw  = PrintWriter( FileWriter("robLog.txt") )
	val inputStream: InputStream = File("robLog.txt").inputStream() 
 
	fun saveData(   data : String )   {		
  		pw.append( "$data\n " )
		pw.flush()
    }
	
	fun showData(){
		val lineList = mutableListOf<String>()		
		inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }
		lineList.forEach{println(">  " + it)}		
	}

	fun getFirstLine() : String {
		var line = ""
		inputStream.bufferedReader().useLines { lines -> line = lines.first() }
		//println("      ... getFirstLine line=$line")
		return line
	}
}