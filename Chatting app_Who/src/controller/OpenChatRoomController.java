package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;

import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class OpenChatRoomController implements Initializable {

	@FXML private Button send;
	@FXML private TextArea chatLog;
	@FXML private TextArea typeMsg;
	@FXML private ListView<?> userList;
	
	@FXML private MenuItem menuUser;
	@FXML private MenuItem menuExit;
	@FXML private MenuItem menuLogout;
	@FXML private MenuItem menuSetting;
	@FXML private MenuItem menuAbout;
	
	int index = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		send.setOnAction(event->sendButtonAction(event));
		typeMsg.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().toString().equals("ENTER")) {
					sendButtonAction(arg0);
				}
			}
		});
		
		//menu Items
		menuUser.setOnAction(event->menuUserAction(event));
		menuLogout.setOnAction(event->menuLogoutAction(event));
	}
	
	public void menuUserAction(ActionEvent e) {
		System.out.println("미구현");
	}
	
	public void menuLogoutAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃?");
		alert.setHeaderText("로그아웃 하시겠습니까?");
		alert.setContentText("ok를 누르면 로그아웃합니다.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			OpenChatRoom.connect.closeSocket();
			OpenChatRoom.homeStage.close();
			
			Main.getLogin().getStage().show();
		}
		else if (result.get() == ButtonType.CANCEL) {
			return;
		}
	}
	
	public void menuExitAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("종료?");
		alert.setHeaderText("프로그램을 종료하시겠습니까?");
		alert.setContentText("ok를 누르면 종료합니다.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			OpenChatRoom.connect.closeSocket();
			Platform.exit();
		}
		else if (result.get() == ButtonType.CANCEL) {
			return;
		}
	}
	
	public void sendButtonAction(Event e) {
		Date d = new Date();
		String now = d.toString();
		String msg = typeMsg.getText();
		int msgLen = msg.length();

		try {
			OutputStream output = OpenChatRoom.connect.getSocket().getOutputStream();

			byte[] w_data = msg.getBytes("UTF-8");
			output.write(w_data);
			output.flush();
			
			chatLog.insertText(index, "["+ OpenChatRoom.name +"]"+msg); // indexoutofboundsException 발생 중. -> 해결법은?
			index += ("["+ OpenChatRoom.name +"]"+msg).length();
			typeMsg.clear();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IndexOutOfBoundsException e2) {
			e2.printStackTrace();
		}

		System.out.println("["+now+"]: Sucess send message. >>> " + msg);	// 디버깅을 위한 로그
	}
}
