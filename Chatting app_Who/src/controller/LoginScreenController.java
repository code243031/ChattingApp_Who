package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LoginScreenController implements Initializable {
	@FXML private Button exitBtn;
	@FXML private Button loginBtn;
	@FXML private Button accountBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitBtn.setOnAction(event->exitButtonAction(event));
		loginBtn.setOnAction(event->loginButtonAction(event));
		accountBtn.setOnAction(event->accountButtonAction(event));
	}
	
	public void exitButtonAction(ActionEvent e) {
		Platform.exit();
	}
	public void loginButtonAction(ActionEvent e) {
		OpenChatRoom open = new OpenChatRoom();	
	}
	public void accountButtonAction(ActionEvent e) {
		Main.getLogin().account();
	}
}
