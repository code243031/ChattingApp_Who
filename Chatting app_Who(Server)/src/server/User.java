package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import data.FileBox;
import data.MessageBox;

public class User {
	//서버에 등록되는 유저 정보
	private final Socket userSocket;
	private final Socket recvAddress;
	private final String hostName;
	private final String customName;
	private final InetAddress ip;
	private final int port;
	// 그외 클라이언트에게 보여줄만한 유저정보?
	//
	// 해당 사용자의 파일, 메시지 관리객체
	final MessageBox mbox;
	final FileBox fbox;
	// 사용자 대상으로 유저 리스트 전송을 목적으로 하는 스레드
	public Thread requestSendUserList;
	
	public User(Socket info, Socket recv, String name) {
		InetSocketAddress s = (InetSocketAddress) info.getLocalSocketAddress();
		userSocket = info;
		recvAddress = recv;
		hostName = s.getHostName();
		customName = name;
		ip = s.getAddress();
		port = s.getPort();
		mbox = new MessageBox(userSocket, name);
		fbox = new FileBox(userSocket, name);
		
		requestSendUserList = new Thread() {	// bindException : is Already use : bind 발생중. 조치요망
			private ServerSocket request;
			
			@Override
			public void run() {
				try {
					request = new ServerSocket();
					if(!request.isBound()) {
						request.bind(new InetSocketAddress("localhost", 6000));
					}
					
					Socket sock = request.accept();
					while (!request.isClosed()) {
						ArrayList<String> names = new ArrayList<String>();
						for(int i = 0; i < MainServer.list.getUsers().size(); i++) {
							names.add(MainServer.list.getUsers().get(i).getCustomName());
						}
						OutputStream output = sock.getOutputStream();
						ObjectOutputStream oout = new ObjectOutputStream(output);
						oout.writeObject(names);
						sleep(1000);
					}
				} catch (IOException e) {
					if (e.getMessage().equals("Address already in use: bind")) {
						// 예외 되도 잘 동작하니 처리 무시
					}
				} catch (InterruptedException e1) { // 추가요망
					if(userSocket.isClosed()) {
						try {
							request.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Thread.currentThread().interrupt();
					}
					else {
						try {
							MainServer.list.disconnectUser(customName);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} finally {
					
				}
				if(!request.isClosed()) {
					try {
						request.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}

	public Socket getSocket() {
		return userSocket;
	}
	
	public InetAddress getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getHostName() {
		return hostName;
	}

	public String getCustomName() {
		return customName;
	}

	public Socket getRecvAddress() {
		return recvAddress;
	}
}
