package login;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.ServerTime;

public class AccountRequest extends Request{
	private Thread isDefault;

	public AccountRequest() {
		isDefault = new Thread() {
			@Override
			public void run() {
				
			}
		};
		isDefault.setDaemon(true);
		isDefault.start();
	}

	@Override
	public void run() {
		ServerSocket recieve = null;
		Socket sock = null;
		Connection conn = null;
		Statement stat = null;
		
		System.out.println("Waiting for Account and  request of client...");
		try {
			recieve  = new ServerSocket();
			String url = "jdbc:mysql://localhost:3306/chattingapp_db?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
			recieve.bind(new InetSocketAddress("localhost", 3500));
			
			while(true) {
				sock = recieve.accept();
				
				conn = DriverManager.getConnection(url, "AccountHost", "12345");
				stat = conn.createStatement();
				ResultSet res = stat.executeQuery("select count(0) from user_info;");
				int uNumber = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String chatName = null;
				String accountDate = "'"+sdf.format(new Date())+"'";
		        
				while(res.next()) {
					uNumber = res.getInt("count(0)") + 1; 
				}
				// 양식에 맞춰 받아온 데이터를 전달받음.
				byte[] data = new byte[50];
				InputStream input = sock.getInputStream();
				int readByte = input.read(data);
				String msg = new String(data, 0, readByte, "UTF-8");
				String[] chk = msg.split("/") ;
				chatName = "'"+chk[0]+"'";
				ID = "'"+chk[1]+"'";
				PW = "'"+chk[2]+"'";
				
				System.out.println("insert into user_info(Unumber, ChatName, ID, PassWd, AccountDate) values("+ uNumber + "," + chatName + "," + ID + "," + PW + "," + accountDate +");");
				int res2 = stat.executeUpdate("insert into user_info(Unumber, ChatName, ID, PassWd, AcccountDate) values("+ uNumber + "," + chatName + "," + ID + "," + PW + "," + accountDate +");");
				
				if(res2 == 0) {
					System.out.println("Account Failed");
					return;
				}
				else {
					OutputStream output = sock.getOutputStream(); 		// 가입 성공 여부 전송
					String send = "Account complete.";
					data = send.getBytes("UTF-8");
					output.write(data);
					output.flush();
					System.out.println("["+ServerTime.getDate()+ServerTime.getTime()+"]"+"New account user:"+chatName);	
				}
			}
		} catch (IOException | SQLException e) {				// 예외 처리 추가요망
			System.out.println("account"+"error(return 'not Exist'): "+e);
			String send = "Account not exist.";
			if(e.getMessage().equals("Cannot invoke \"java.net.Socket.getOutputStream()\" because \"sock\" is null")) {
				Thread.currentThread().interrupt();
			}
			else {
				byte[] data;
				try {
					data = send.getBytes("UTF-8");
					OutputStream output = sock.getOutputStream(); 	// 가입 실패 여부 전송
					output.write(data);
					output.flush();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					System.out.println("account"+"error: "+e1);
				} catch (IOException e2) {
					if (e2.getMessage().equals("Address already in use: bind")) {
						System.out.println("서버가 이미 동작 중입니다. 프로그램 종료.");
						System.exit(0);
					}
					else {
						System.out.println("account"+"error: "+e2);
					}
				}
			}
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
