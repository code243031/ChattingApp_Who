package message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class MessageBox extends Thread{
	Socket userSocket;
	String userName;
	
	public MessageBox(Socket userSocket, String name) {
		this.userSocket = userSocket;
		userName = name;
	}

	@Override
	public void run() {

		InputStream input;
		try {
			while(!userSocket.isClosed()) {
				byte[] data = new byte[200];
				input = userSocket.getInputStream();
				int readByte = input.read(data);
				String msg = new String(data, 0, readByte, "UTF-8");
				System.out.println("["+LocalDateTime.now()+"]"+"["+userName+"]"+msg);
			}
		} catch (StringIndexOutOfBoundsException e) {
			try {
				System.out.println("["+LocalDateTime.now()+"]"+"["+userName+"]"+"is disconnect");
				userSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
