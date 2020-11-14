package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainServer {

	public static ArrayList<PrintWriter> outputList = new ArrayList<>();
	
	public static void main(String[] args) {
		UserList list = new UserList();

		try {
			ServerSocket server = new ServerSocket();
			server.bind(new InetSocketAddress("localhost", 5006));
			
			System.out.println("Connection server is ready.");
			System.out.print("Ctrl+C or type Exit code to exit.");
			while(true) {
				Socket sock = server.accept();
				System.out.println("["+LocalDateTime.now()+"]: User Connect complete.");
				
				User user = new User((InetSocketAddress) sock.getRemoteSocketAddress());
				list.addUser(user);
				
				outputList.add(new PrintWriter(sock.getOutputStream()));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
