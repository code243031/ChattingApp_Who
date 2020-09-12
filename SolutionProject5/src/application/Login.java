package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login { // 로그인 창 생성
	
	private Parent root;
	private Stage primaryStage;

	public Login(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.root = FXMLLoader.load(this.getClass().getResource("Chatting.fxml"));
			Scene scene = new Scene(root);
			
			this.primaryStage.setTitle("Who? (ver 0.01)");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getStage() {
		return primaryStage;
	}

}
