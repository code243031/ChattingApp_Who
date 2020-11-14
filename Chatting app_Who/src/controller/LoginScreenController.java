package controller;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;
import background.Listener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreenController implements Initializable {
	@FXML private Button exitBtn;
	@FXML private Button loginBtn;
	@FXML private Button accountBtn;
	@FXML private TextField id;
	@FXML private PasswordField pw;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitBtn.setOnAction(event->exitButtonAction(event));
		loginBtn.setOnAction(event->loginButtonAction(event));
		accountBtn.setOnAction(event->accountButtonAction(event));
	}
	
	public void exitButtonAction(ActionEvent e) {
		Platform.exit();
	}
	public void loginButtonAction(ActionEvent e)
	{
		if(id.getText().equals("root")) {
			
			
			
			OpenChatRoom open = new OpenChatRoom();	
		}
		else {
			System.out.println("그런 회원 없음");
		}
		//Listener conn = new Listener();
		
	}
	public void accountButtonAction(ActionEvent e) {

		Main.getLogin().account();
	}
}
