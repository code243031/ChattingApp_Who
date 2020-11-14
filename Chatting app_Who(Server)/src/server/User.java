package server;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class User {

	String hostname;
	InetAddress ip;
	int port;
	
	public User(InetSocketAddress info) {
		hostname = info.getHostName();
		ip = info.getAddress();
		port = info.getPort();
	}

}
