package connectServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import application.OpenChatRoom;
import application.User;

public class Reciever extends Thread {

	public ArrayList<User> userList = new ArrayList<User>();
	
	private Thread listSetting;
	
	public Reciever() {
		listSetting = new Thread() {
			private ArrayList<String> arr = null;

			@Override
			public void run() {
				Socket sock = new Socket();
				try {
					sock.connect(new InetSocketAddress("localhost", 6000));
					while (true) {
						userList.clear();
						
						InputStream in = sock.getInputStream();
						ObjectInputStream oin = new ObjectInputStream(in);
						arr = (ArrayList<String>) oin.readObject();
						// 유저객체 추가 (미완)
						for(int i = 0; i < arr.size(); i++) {
							userList.add(new User(arr.get(i)));
						}
						sleep(1000);
					}
				} catch (IOException e1) {
					if(e1.getMessage().equals("Connection reset")) {
						ServerConnector.isServerConnect = false;
					}
					
				} catch (ClassNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (InterruptedException e) { // 추가요망
					e.printStackTrace();
				}
				if (!sock.isClosed()) {
					try {
						sock.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}

	public Thread getListSetting() {
		return listSetting;
	}
}
