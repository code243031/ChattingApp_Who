package connectServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import application.OpenChatRoom;

public class MessageRecieve extends Reciever{

	private Socket recieveAddress;
	
	public MessageRecieve(Socket socket) { // OpenChatRoomController의 기능을 여기로 옳길 예정
		recieveAddress = socket;
	}
	
	@Override
	public void run() {
		try {
			recieveAddress.connect(new InetSocketAddress("localhost", 5007));
			
			while(true) {
				byte[] data = new byte[1024];
				InputStream recv = recieveAddress.getInputStream();
				int readByte = recv.read(data);
				String recv_msg = new String(data, 0, readByte, "UTF-8");

				// 받은 문자열 버퍼로 전송
				OpenChatRoom.setRecv_msg(recv_msg);
			}
		} catch (IOException e1) {
			System.out.println(e1);
			if (e1.getMessage().equals("Connection reset")) {
				System.out.println(Thread.currentThread().getName()+"error: "+e1);
			}
			else {
				OpenChatRoom.connect.closeSocket();
			}
		} catch (java.lang.StringIndexOutOfBoundsException e2) {
			OpenChatRoom.connect.closeSocket();
		}
	}

	public Socket getRecieveAddress() {
		return recieveAddress;
	}
}
