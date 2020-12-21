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
	public void run(){ // �޼����� ������, ���ŵ� �޼����� �ٸ� Ŭ���̾�Ʈ�鿡�� �߽��ϴ� ����
		InputStream input;
		try {
			while(!userSocket.isClosed()) {
				sleep(1);
				// �޼��� ����
				byte[] data = new byte[1024];
				input = userSocket.getInputStream();
				int readByte = input.read(data);
				String msg = new String(data, 0, readByte, "UTF-8");
				System.out.print("["+ServerTime.getDate()+ServerTime.getTime()+"]"+msg);
				
				// �ٸ� ����ڵ鿡�� �޼��� �߽�
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
				System.out.println("["+ServerTime.getDate()+ServerTime.getTime()+"]"+"["+ userName +"] �� ������ �Ҿ����մϴ�. ���� shutdown ��ġ.");
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
