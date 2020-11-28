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

public class Login { // �α��� â ����
	
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
			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // �ݱ⸦ ������ �۾�ǥ���ٿ� �ִ� �������� ���ܵΰ� â�� �ݾƵδ� �̺�Ʈ �̱������� ���� ��ü �̺�Ʈ �ۼ���(���߿� ���� ���)
				public void handle(WindowEvent evt) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("����?");
					alert.setHeaderText("���α׷��� �����Ͻðڽ��ϱ�?");
					alert.setContentText("ok�� ������ �����մϴ�.");

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
			accStage.setTitle("ȸ�� ����");
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
