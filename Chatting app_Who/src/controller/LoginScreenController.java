package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginScreenController implements Initializable {
	@FXML private Button exitButton;
	@FXML private Button loginButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitButton.setOnAction(event->exitButtonAction(event));
		loginButton.setOnAction(event->loginButtonAction(event));

	}
	
	public void exitButtonAction(ActionEvent e) {
		Platform.exit();
	}
	public void loginButtonAction(ActionEvent e) {
		OpenChatRoom open = new OpenChatRoom();
		
	}

}
