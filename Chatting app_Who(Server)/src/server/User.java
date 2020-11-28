package server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import message.MessageBox;

public class User {
	Socket userSocket;
	String hostName;
	String customName;
	InetAddress ip;
	int port;
	MessageBox box;
	
	public User(Socket info, String name) {
		InetSocketAddress s = (InetSocketAddress) info.getLocalSocketAddress();
		
		userSocket = info;
		hostName = s.getHostName();
		customName = name;
		ip = s.getAddress();
		port = s.getPort();
		box = new MessageBox(userSocket, name);
	}
}
