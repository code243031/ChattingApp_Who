package controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.OpenChatRoom;
import application.PersonalChatRoom;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class PersonalChatRoomController implements Initializable {

	@FXML private TextArea list;
	@FXML private TextArea msg;
	@FXML private Button send;
	
	private Thread pressMsg;
	
	int index = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		pressMsg = new Thread() {	// 수신된 메시지를 가져오는 스레드
			@Override
			public void run() {
				while(true) {
					try {
						sleep(1000);
						String tmp = PersonalChatRoom.getRecv_msgToTarget();
						if(tmp == null) {
							continue;
						}
						else {
							list.insertText(index, tmp); // indexoutofboundsException 발생중, 해결법은?
							index += (tmp).length();
						}
						
					} catch (InterruptedException e) {
						if(OpenChatRoom.connect.getSocket().isClosed()) {
							System.out.println(e);
							break;
						}
					}
				}
			}
		};
		pressMsg.setDaemon(true);
		pressMsg.start();
		
		msg.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().toString().equals("ENTER")) {
					sendBtnAction(arg0);
				}
			}
		});
		
		send.setOnAction(event->sendBtnAction(event));
	}

	public void sendBtnAction(Event e) {
		Date d = new Date();
		String now = d.toString();
		String msg = "[" + OpenChatRoom.name + "]" + this.msg.getText()+"\n";
		
		// 처리 미등록
//		try {
//			OutputStream output = OpenChatRoom.connect.getSocket().getOutputStream();
//
//			byte[] w_data = msg.getBytes("UTF-8");
//			output.write(w_data);
//			output.flush();
//		} catch (IOException e1) {
//			if(e1.getMessage().equals("Connection reset by peer") || e1.getMessage().equals("Socket is closed")) {	//	서버가 다운된 경우의 처리
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setTitle("오류!");
//				alert.setHeaderText("서버와 연결할 수 없습니다.");
//				alert.setContentText("ok를 누르면 종료합니다.");
//				
//				Optional<ButtonType> result = alert.showAndWait();
//				if (result.get() == ButtonType.OK) {
//					OpenChatRoom.connect.closeSocket();
//					Platform.exit();
//					System.exit(1);
//				}
//			}
//			else {
//				e1.printStackTrace();
//			}
//		} catch (IndexOutOfBoundsException e2) {
//			e2.printStackTrace();
//		}
		PersonalChatRoom.setRecv_msgToTarget(msg); // 테스트용 임시 조치
		
		System.out.println("["+now+"]: Sucess send message for target. >>> " + msg);	// 디버깅을 위한 로그
		this.msg.clear();
	}
}
