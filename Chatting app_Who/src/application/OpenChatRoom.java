package application;

import java.io.IOException;
import java.util.Optional;

import connectServer.ServerConnector;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class OpenChatRoom {
	public static ServerConnector connect;	// 연결 정보
	public static String name;				// 로그인한 사용자 이름
	
	public static Stage homeStage;
	
	public OpenChatRoom(ServerConnector con, String name) {
		try {
			homeStage = new Stage();
			Parent home = FXMLLoader.load(this.getClass().getResource("OpenChatRoom.fxml"));
			
			Main.getLogin().getStage().hide(); // 로그인 버튼을 누르면 로그인 창을 감춤
			Scene scene = new Scene(home);
			connect = con;
			OpenChatRoom.name = name;
			
			homeStage.setTitle("채팅방");
			homeStage.setScene(scene);
			homeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent evt) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("종료?");
					alert.setHeaderText("프로그램을 종료하시겠습니까?");
					alert.setContentText("ok를 누르면 종료합니다.");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						connect.closeSocket();
						Platform.exit();
					}
					else if (result.get() == ButtonType.CANCEL) {
						evt.consume();
					}
				}
				
			});
			homeStage.setResizable(false);
			homeStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
