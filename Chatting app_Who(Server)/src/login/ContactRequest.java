package login;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactRequest extends Thread{

	public ContactRequest() {
		
	}
	@Override
	public void run() {
		ServerSocket recieve = null;
		Socket sock = null;
		Connection conn = null;
		Statement stat = null;
		
		String ID;
		String PW;
		
		try {
			recieve  = new ServerSocket();
			String url = "jdbc:mysql://localhost:3306/chattingapp_db?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
			System.out.println("Waiting for login request of client.");
			recieve.bind(new InetSocketAddress("localhost", 3305));
			
			sock = recieve.accept();
			
			byte[] data = new byte[50];
			InputStream input = sock.getInputStream();
			int readByte = input.read(data);
			String msg = new String(data, 0, readByte, "UTF-8");
			String[] chk = msg.split("/") ;
			
			conn = DriverManager.getConnection(url, "AccountHost", "12345");
			stat = conn.createStatement();
			ResultSet res = stat.executeQuery("Select * from User_info");
			
			String send = "User not exists.";
			while(res.next()) {
				if(res.getString("ID").equals(chk[0]) && res.getString("PassWd").equals(chk[1])) {
					send = res.getString("ChatName");
				}
			}
			
			OutputStream output = sock.getOutputStream(); // 유저가 존재하면 UserExist 전송
			
			data = send.getBytes("UTF-8");
			output.write(data);
			output.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!sock.isClosed()) {
			try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!recieve.isClosed()) {
			try {
				recieve.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
