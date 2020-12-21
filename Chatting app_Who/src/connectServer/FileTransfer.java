package connectServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class FileTransfer extends ServerConnector {
	private Socket sendAddress;
	
	public FileTransfer() {
		Thread recv = new Thread() {
			public void run() {
				// �̿�
			}
		};
		recv.start();
	}
	
	public void sendFile(String fileName) throws IOException {
		Date d = new Date();
		String now = d.toString();
		
        //�����͸� ����� ���ؼ� ������ ��Ʈ�� ���.
		sendAddress = new Socket();
		sendAddress.connect(new InetSocketAddress("localhost", 5400));
		File dir = new File(fileName);
        InputStream in = new FileInputStream(dir);
        OutputStream out = sendAddress.getOutputStream();

        // ���ϸ� ����
        String fName = dir.getName();
		byte[] data = fName.getBytes("UTF-8");
		out.write(data);
		out.flush();
		
        // ���� ����
        int count;
        byte[] bytes = new byte[16 * 1024];
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }
        
        System.out.println("["+now+"]: Sucess send file. >>> " + dir.getName());
        
        out.close();
        in.close();
        sendAddress.close();
        sendAddress = null;
	}
}
