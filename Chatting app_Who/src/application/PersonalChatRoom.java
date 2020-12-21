package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PersonalChatRoom {

	private Parent root;
	private Stage primaryStage;
	
	private static ArrayList<String> recv_msgToTarget = new ArrayList<String>();

	public PersonalChatRoom(Stage primaryStage, String me) {
		try {
			this.primaryStage = primaryStage;
			this.root = FXMLLoader.load(this.getClass().getResource("PersonalChatRoom.fxml"));
			Scene scene = new Scene(root);
			
			this.primaryStage.setTitle("Who? (ver 0.01)");
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // 닫기를 누르면 작업표시줄에 있는 아이콘은 남겨두고 창만 닫아두는 이벤트 미구현으로 인한 대체 이벤트 작성함(나중에 수정 요망)
				public void handle(WindowEvent evt) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("종료?");
					alert.setHeaderText("대화를 마치고 연결을 종료합니까?");
					alert.setContentText("ok를 누르면 연결을 종료합니다.");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						getStage().close();
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

	public static String getRecv_msgToTarget() {
		String res = null;
		try {
			res = recv_msgToTarget.get(0);
			recv_msgToTarget.remove(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		
		return res;
	}

	public static void setRecv_msgToTarget(String msg) {
		recv_msgToTarget.add(msg);
	}
}
