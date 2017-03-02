package impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class SMRMISocket extends RMISocketFactory {

	@Override
	public Socket createSocket(String host, int port) throws IOException {
		// TODO Auto-generated method stub
		return new Socket(host,port);  
	}

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		// TODO Auto-generated method stub
		if(port == 0)  

			port = 20110; 
			return new ServerSocket(port);  
	}

}
