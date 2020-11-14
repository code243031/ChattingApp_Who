package background;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Listener extends Thread{
	private Socket sock;

	public Listener() {
		
	}
	
	@Override
	public void run() {
		/*
		try {
			sock = new Socket("localhost", 5066);
			BufferedReader b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			while(true) {
				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
