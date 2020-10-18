package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class OpenChatRoomController implements Initializable {

	@FXML private Button send;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		send.setOnAction(event->sendButtonAction(event));
		
	}
	
	public void sendButtonAction(Event e) {
		Date d = new Date();
		String now = d.toString();
		System.out.println("["+now+"]: Sucess send message.");
		
	}

}
