/*
*  Generated by AN Unibo
*/
package it.unibo.platform.tcp;
import it.unibo.is.interfaces.IBasicUniboEnv;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.is.interfaces.protocols.ITcpConnection;
import it.unibo.system.SituatedPlainObject;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SocketTcpSupport extends SituatedPlainObject implements ITcpConnection{
private String logo = "sys";
private static HashMap<String,ServerSocket> activePorts = 
									new HashMap<String,ServerSocket>();
 
	public SocketTcpSupport(String logo,IBasicUniboEnv env) {
		super(env);
		if( logo != null ) this.logo = logo ;
 		if( System.getProperty("tcpLowTrace") != null ) 
			debug = System.getProperty("tcpLowTrace").equals("set") ;
	}
	public SocketTcpSupport(String logo,IOutputView outView) {
		super(outView);
		if( logo != null ) this.logo = logo ;
 		if( System.getProperty("tcpLowTrace") != null ) 
			debug = System.getProperty("tcpLowTrace").equals("set") ;
 	}

	
	/**
	 * To be used by a receiver to start a connection.
	 */
	public ServerSocket connectAsReceiver(int portNum) throws Exception{
//		println( "connectAsReceiver on port " + portNum );
		if( activePorts.get(""+portNum) != null ){ //port already active
			println(" +++ INPUT PORT " + portNum +" ALREADY ACTIVE");
			return activePorts.get(""+portNum);
		}
		ServerSocket serverSocket = new ServerSocket( portNum );	
		activePorts.put(""+portNum, serverSocket);
		return serverSocket;
	}
	public ServerSocket connectAsReceiverNoMemo(int portNum) throws Exception{
//		println( "connectAsReceiverNoMemo on port " + portNum );
		if( activePorts.get(""+portNum) != null ){ //port already active
			println(" +++ INPUT PORT " + portNum +" ALREADY ACTIVE");
			activePorts.get(""+portNum).close();
 		}
 		ServerSocket serverSocket = new ServerSocket( portNum );	
		activePorts.put(""+portNum, serverSocket);
 		return serverSocket;
	}
	
	public Socket acceptAConnection(ServerSocket serverSocket) throws Exception {
		int timeOut = 20000;
		if( System.getProperty("inputTimeOut") != null )
			timeOut = Integer.parseInt(
					System.getProperty("inputTimeOut"));
		//System.out.println( " ***acceptAConnection timeOut = " + timeOut + " serverSocket=" + serverSocket.getInetAddress());
		serverSocket.setSoTimeout(timeOut); //wait for timeOut sec
		Socket socket = serverSocket.accept();
		//println( " *** has accepted a connection ... " + socket.getRemoteSocketAddress());
		return  socket;
	}
	
	public Socket connectAsClient(String hostName, int port) throws Exception{
//		println( "SocketTcpSupport connecting as client to " + hostName + " " + port);
		try{
			Socket socket = new Socket( hostName, port );	//bloccante
//			println( "connected a client to " + hostName );
			return  socket;
		}catch(Exception e){
//			println( "SocketTcpSupport connectAsClient ERROR " + e.getMessage() );
			throw e;
		}
	}
	@Override
	public  void closeConnection(ServerSocket serverSocket) throws Exception{
		serverSocket.close();
	}

 
	protected  void println( String msg){
		showMsg("		+++ SocketTcpSupport|" + logo + " " + msg  );
	}
	 
	public static void freeThePort(int portNum) throws Exception {
 		ServerSocket sock = activePorts.get(""+portNum);
		if( sock != null ){ //port  active
			activePorts.remove(sock);
 		}		
	}
	
	public static void forget(ServerSocket sock) throws Exception {
 		if( sock != null ){ //port  active
			activePorts.remove(sock);
 		}		
	}
	
 }
