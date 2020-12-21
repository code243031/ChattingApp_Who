package data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import server.MainServer;
import server.ServerTime;
import server.User;

public class MessageBox extends Thread{
	private Socket userSocket;
	String userName;
	
	public MessageBox(Socket userSocket, String name) {
		this.userSocket = userSocket;
		userName = name;
	}

	@Override
	public void run(){ // 메세지를 받으면, 수신된 메세지를 다른 클라이언트들에게 발신하는 구조
		InputStream input;
		try {
			while(!userSocket.isClosed()) {
				sleep(1);
				// 메세지 수신
				byte[] data = new byte[1024];
				input = userSocket.getInputStream();
				int readByte = input.read(data);
				String msg = new String(data, 0, readByte, "UTF-8");
				System.out.print("["+ServerTime.getDate()+ServerTime.getTime()+"]"+msg);
				
				// 다른 사용자들에게 메세지 발신
				for(int i = 0; i < MainServer.list.getUsers().size(); i++) {
					User user = MainServer.list.getUsers().get(i);
					OutputStream output = user.getRecvAddress().getOutputStream();
					data = msg.getBytes("UTF-8");
					output.write(data);
					output.flush();
				}
			}
		} catch (InterruptedException | StringIndexOutOfBoundsException e) {
			
		} catch (IOException e) {
			if (e.getMessage().equals("Connection reset by peer")) {
				//MainServer.list.
				System.out.println("["+ServerTime.getDate()+ServerTime.getTime()+"]"+"["+ userName +"] 의 연결이 불안정합니다. 강제 shutdown 조치.");
			}
		}
		finally {
			try {
				MainServer.list.disconnectUser(userName);
			} catch (IOException e) {
				System.out.println(userName+"error: "+e);
			}
		}
	}
}
