package connectServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class ServerConnector {
	private Socket sock;
	String name;
	
	public ServerConnector() {
		// TODO Auto-generated constructor stub
	}
	/*
	 예외별 분기 처리가 요구됨!
	 - 서버 연결요청 후 서버에서 '그런 회원은 없음'이라는 신호가 왔을때의 예외 처리
	 - 네트워크 연결이 불안정할 때?
	 */
	
	
	public Boolean connectServer(String ip, int port, String ID, String PW) throws ConnectException { // 회원 로그인
		sock = new Socket();
		try {
			if(findUser(ID, PW)) {
				sock.connect(new InetSocketAddress("localhost", 5006));
				if (sock.isConnected()) {
					name = "name";
					OutputStream sendName = sock.getOutputStream();
					byte[] data = name.getBytes("UTF-8");
					sendName.write(data);
					sendName.flush();
					return true;
				}
				else {
					return false;
				}
			}
			
		}catch (IOException e) {
			e.printStackTrace();
			return false;	// 예외 처리 추가 요망
		}
		return false;
	}
	
	public Boolean connectServer(String ip, int port, String name) { // 게스트 로그인
		sock = new Socket();
		try {
			sock.connect(new InetSocketAddress("localhost", 5006));
			if (sock.isConnected()) {
				OutputStream sendName = sock.getOutputStream();
				byte[] data = name.getBytes("UTF-8");
				sendName.write(data);
				sendName.flush();
				return true;
			}
			else {
				return false;	// 예외 처리 추가 요망
			}
		}catch (IOException e) {
			return false;
		}
	}
	
	public Boolean findUser(String ID, String PW) {
		Socket request = new Socket();
		Date d = new Date();
		String now = null;
		
		try {
			request.connect(new InetSocketAddress("localhost", 3305));
			
			byte[] data = new byte[50];
			OutputStream output = request.getOutputStream(); // 유저가 존재하면 UserExist 전송
			String send = ID+"/"+PW;
			data = send.getBytes("UTF-8");
			output.write(data);
			output.flush();
			
			now = d.toString();
			System.out.println("["+now+"]Success Send Connect request to server.");
			
			InputStream input = request.getInputStream();
			int readByte = input.read(data);
			String userName = new String(data, 0, readByte, "UTF-8");
			// 분기문 추가
			if(userName.equals("User not exists.")) {
				throw new IOException("회원이 존재하지 않음");
			}
			else {
				name = userName;
			}
			
			now = d.toString();
			System.out.println("["+now+"]login request mounted. >> " + name);

			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void closeSocket() {
		try {
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return sock;
	}
}
