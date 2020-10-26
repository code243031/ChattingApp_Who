package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login { // 로그인 창 생성
	
	private Parent root;
	private Stage primaryStage;

	public Login(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.root = FXMLLoader.load(this.getClass().getResource("LoginScreen.fxml"));
			Scene scene = new Scene(root);
			
			this.primaryStage.setTitle("Who? (ver 0.01)");
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public void account() {
		try {
			Stage accStage = new Stage(StageStyle.UTILITY);
			Parent home = FXMLLoader.load(this.getClass().getResource("Account.fxml"));
			
			Scene scene = new Scene(home);
			
			accStage.initModality(Modality.WINDOW_MODAL);
			accStage.initOwner(primaryStage);
			accStage.setTitle("회원 가입");
			accStage.setScene(scene);
			accStage.setResizable(false);
			accStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
