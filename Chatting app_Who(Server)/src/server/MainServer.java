package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.time.LocalDateTime;
import java.util.Scanner;

import login.ContactRequest;

public class MainServer {
	
	public static void main(String[] args) {
		UserList list = new UserList();
		Scanner sc = new Scanner(System.in);
		ContactRequest req = new ContactRequest();
		
		req.start();
		try {
			ServerSocket server = new ServerSocket();
			server.bind(new InetSocketAddress("localhost", 5006));
			Thread isExitSignal = new Thread() {
				public void run() {
					Boolean flag = false;
					while(flag != true) {
						int i = sc.nextInt();
						if (i == 0) {
							flag = true;
						}
					}
					System.out.println("Server is closed.");
					System.exit(0);
				}
			};
			isExitSignal.start();
			
			System.out.println("Connection server is ready.");
			System.out.println("Press '0(zero)' to server close.");

			while(true) {
				Socket sock = server.accept();
				byte[] data = new byte[50];
				InputStream input = sock.getInputStream();
				int readByte = input.read(data);
				String name = new String(data, 0, readByte, "UTF-8");
				
				System.out.println("["+LocalDateTime.now()+"]: User '" + name + "' Connect complete");
				
				User user = new User(sock, name);
				user.box.start();
				list.addUser(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
