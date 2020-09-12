package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BtnController implements Initializable {
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
		try {
			Stage homeStage = new Stage();
			Parent home = FXMLLoader.load(this.getClass().getResource("ChattingMain.fxml"));
			
			Main.getLogin().getStage().hide(); // 로그인 버튼을 누르면 로그인 창을 감춤
			
			Scene scene = new Scene(home);
			homeStage.setTitle("채팅방");
			homeStage.setScene(scene);
			homeStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
