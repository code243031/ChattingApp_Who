package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import server.MainServer;
import server.ServerTime;
import server.User;

public class FileBox extends Thread {
	ServerSocket fileSock; // ����
	
	Socket userSocket;
	String userName;
	
	public FileBox(Socket userSocket, String name) {
		this.userSocket = userSocket;
		userName = name;
	}
	
	@Override
	public void run() {
		try {
			fileSock = new ServerSocket();
			if(!fileSock.isBound()) {
				fileSock.bind(new InetSocketAddress("localhost", 5400));		
			}
			while(true) {
				Socket sock = fileSock.accept();
				
				InputStream in = sock.getInputStream();
				// ���ϸ� �ޱ�
				byte[] data = new byte[1024];
				in = sock.getInputStream();
				int readByte = in.read(data);
				String fName = new String(data, 0, readByte, "UTF-8");
				// �ش� ���͸��� ������ ���� ���� �� �۾�����
				File folder = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\downloadFiles\\");
				if(!folder.exists()) {
					folder.mkdir();
				}
				
				File f = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\downloadFiles\\"+fName);
		        OutputStream out = new FileOutputStream(f);
		        
		        // ������ ��ǻ�Ϳ� ��������
		        byte[] bytes = new byte[16*1024];
                int count;
                while (true) {
                    count = in.read(bytes);
                    if(count < 16380){
                        out.write(bytes, 0, count);
                        break;
                    }else{
                        out.write(bytes, 0, count);
                    }
                }
                // �����鿡�� �����й�
                for(int i = 0; i < MainServer.list.getUsers().size(); i++) {
                	String msg = "["+userName+"]"+"<��������>: "+fName;
                	
					User user = MainServer.list.getUsers().get(i);
					OutputStream output = user.getRecvAddress().getOutputStream();
					data = msg.getBytes("UTF-8");
					output.write(data);
					output.flush();
				}
                
                System.out.println("["+ServerTime.getDate()+ServerTime.getTime()+"]'"+userName+"'send new file.: "+f.getName());
                in.close();
                out.close();
    			sock.close();
			}
		} catch (IOException | NullPointerException e1) {
			if (e1.getMessage().equals("Address already in use: bind")) {
				// ��� ��
			}
			else {
				System.out.println("fileBox"+e1.getMessage());
				Thread.currentThread().interrupt();
			}
		} catch (StringIndexOutOfBoundsException e2) {
			System.out.println("fileBox"+e2.getMessage());
		}
		
		if(!fileSock.isClosed()) {
			try {
				fileSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
