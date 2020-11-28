package application;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Login { // 로그인 창 생성
	
	private Parent root;
	private Stage primaryStage;
	private Stage accStage;

	public Login(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.root = FXMLLoader.load(this.getClass().getResource("LoginScreen.fxml"));
			Scene scene = new Scene(root);
			
			this.primaryStage.setTitle("Who? (ver 0.01)");
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // 닫기를 누르면 작업표시줄에 있는 아이콘은 남겨두고 창만 닫아두는 이벤트 미구현으로 인한 대체 이벤트 작성함(나중에 수정 요망)
				public void handle(WindowEvent evt) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("종료?");
					alert.setHeaderText("프로그램을 종료하시겠습니까?");
					alert.setContentText("ok를 누르면 종료합니다.");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						Platform.exit();
					}
					else if (result.get() == ButtonType.CANCEL) {
						evt.consume();
					}
				}
			});
			
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
			accStage = new Stage(StageStyle.UTILITY);
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
	
	public Stage getAccountStage() {
		return accStage;
	}

	public void loginFailed() {
		
	}
}
