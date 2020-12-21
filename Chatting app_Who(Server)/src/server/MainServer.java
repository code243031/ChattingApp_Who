package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import login.AccountRequest;
import login.ContactRequest;

public class MainServer {

	public static UserList list = new UserList();
	private static ServerSocket server;
	private static ServerSocket recv_serv;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ContactRequest req = new ContactRequest();
		AccountRequest req2 = new AccountRequest();
		ServerTime time = new ServerTime();
		
		time.time_refresh.start();
		
		req.setDaemon(true);
		req.start();
		req2.setDaemon(true);
		req2.start();
		
		try {
			server = new ServerSocket();
			recv_serv = new ServerSocket();
			server.bind(new InetSocketAddress("localhost", 5006));
			recv_serv.bind(new InetSocketAddress("localhost", 5007));
			Thread isExitSignal = new Thread() {
				public void run() {
					Boolean flag = false;
					while(flag != true) {
						int i = sc.nextInt();
						if (i == 0) {
							flag = true;
						}
						else if (i == 1) {
							Map<Thread, StackTraceElement[]> tmp = Thread.getAllStackTraces();
							System.out.println(tmp);
							System.out.println("All Thread printed.");
						}
					}
					System.out.println("Server is closed.");
					System.exit(0);
				}
			};
			isExitSignal.setName("WaitingExitSignal");
			isExitSignal.setDaemon(true);
			isExitSignal.start();
			
			System.out.println("Connection server is ready.");
			System.out.println("Press '0(zero)' to server close.");
			while(true) {
				Socket sock = server.accept();
				Socket sock2 = recv_serv.accept();
				
				byte[] data = new byte[50];
				InputStream input = sock.getInputStream();
				int readByte = input.read(data);
				String name = new String(data, 0, readByte, "UTF-8");
				
				System.out.println("["+ServerTime.getDate()+ServerTime.getTime()+"]: User '" + name + "' Connect complete");
				
				User user = new User(sock, sock2, name);
				user.mbox.start();
				user.fbox.start();
				user.requestSendUserList.start();
				list.addUser(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}

}
