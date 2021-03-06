/*
*  Generated by AN Unibo
*/
package it.unibo.platform.udp;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.is.interfaces.protocols.IUdpInteraction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketUdpConnSupport implements IUdpInteraction{
private DatagramSocket socket;
private int portNum;
private InetAddress ia ;
private DatagramPacket curPacket;
private DatagramPacket firstReceivedPacket;
private boolean debug = false;
private IOutputView view;
private boolean isAReceiver = false;

/*
* A receiver connection is activate with ia==null
* Each receiver connection object ha  its own firstReceivedPacket
*/
public SocketUdpConnSupport( DatagramSocket socket, int portNum, InetAddress ia, IOutputView view ){
 	this.socket 	= socket;
	this.portNum	= portNum;
 	this.ia			= ia;
	this.view   	= view ;
	isAReceiver		= ia==null;
	if( System.getProperty("udpTrace") != null ) 
		debug = System.getProperty("udpTrace").equals("set") ;
}


public void closeConnection() throws Exception {
	System.out.println( "closeConnection udp isConnected=" + socket.isConnected() + " isBound=" + socket.isBound() );
	socket.close();
}

 
public boolean isTwoWay() {
 	return false;
}
public synchronized void sendALine( String msg ) throws Exception {
 	if( isAReceiver ) sendAReplyLine( msg );
	else
 	try {
// 		println( " *** sendALine " + ia  );
		send(socket, ia, portNum, msg.getBytes());	
	} catch (Exception e) {
		println( "ERROR " + e.getMessage()  );
	}
}


public void sendALine(String msg, boolean isAnswer) throws Exception {
	if(isAnswer) sendAReplyLine(msg);
	else sendALine(msg);
	
}


public DatagramSocket getSocket() {
	return socket;
}


public String receiveALine(   ) throws Exception{
	int timeOut = 20000;
	if( System.getProperty("inputTimeOut") != null )
		timeOut = Integer.parseInt(System.getProperty("inputTimeOut"));
	DatagramPacket rp = receiveAPacket( timeOut ); //Time out after 1min
	if( firstReceivedPacket  == null ) firstReceivedPacket = rp;
	String s  = new String(rp.getData(),0,rp.getLength());
//	println("received host= " + rp.getAddress().getHostName()+ "  port=" + rp.getPort());
	return s;
}

//--------------------------------------------------------------------

public void sendAReplyLine( String msg )throws Exception{
	if( firstReceivedPacket != null )
		sendALine( firstReceivedPacket.getAddress(), firstReceivedPacket.getPort(), msg );
	else throw new Exception("SocketUdpConnSupport ERROR: reply without caller");
}

public  DatagramPacket receiveAPacket(  int timeout ) throws Exception {
	try{
		byte[] 	buffer = new byte[65507];
		curPacket = new DatagramPacket(buffer,buffer.length);
//		System.out.println("*** receiveAPacket " + socket   );
		socket.setSoTimeout(timeout);	//blocks for no more than 0.2 sec	    	
		socket.receive(curPacket);
		return curPacket;
	}catch(  Exception e ){
		println("TOUT ds  *** " + socket.getLocalPort() + " " + e.getMessage() );
//		ds.close();
		throw e;
	}			
}

protected void sendALine(InetAddress ia, int destPort, String msg)
		throws Exception {
	send(socket, ia, destPort, msg.getBytes() );
}

protected synchronized void send(
		DatagramSocket ds, InetAddress ia, int portNum, byte[] buffer) throws Exception{
	if( buffer.length > 64900) throw new IOException();
	DatagramPacket dp = new DatagramPacket(buffer, buffer.length, ia, portNum);
	ds.send(dp);
//	println("has sent a msg to " + dp.getAddress()+":"+portNum);
}

protected  void println( String msg){
	 if( debug ) 
		 doprintln("		+++ SocketUdpConnSupport|"   + " " + msg  );
}

protected  void doprintln( String msg){
	if( view != null )
		view.addOutput(msg);
	else System.out.println(msg  );		
}



}//Class


