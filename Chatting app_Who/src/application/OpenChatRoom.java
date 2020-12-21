package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import connectServer.FileTransfer;
import connectServer.MessageRecieve;
import connectServer.Reciever;
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
	public static FileTransfer fTrans;
	public static String name;				// 로그인한 사용자 이름
								
	public static Stage homeStage;
	public static Reciever reciever;
	
	private static ArrayList<String> recv_msg = new ArrayList<String>();

	public OpenChatRoom(ServerConnector con, String name, Reciever reciever) {
		OpenChatRoom.reciever = reciever;
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("OpenChatRoom.fxml"));
		try {
			homeStage = new Stage();
			Parent home = (Parent) loader.load();

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
						System.exit(0);
					}
					else if (result.get() == ButtonType.CANCEL) {
						evt.consume();
					}
				}
				
			});
			
			homeStage.setResizable(false);
			homeStage.show();
			
			MessageRecieve msg_rec = new MessageRecieve(connect.getRecv_sock());
			msg_rec.start();
			
			reciever.getListSetting().setName("list Setter");
			reciever.getListSetting().start();
			
			fTrans = new FileTransfer();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static String getRecv_msg() {
		String res = null;
		try {
			res = recv_msg.get(0);
			recv_msg.remove(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
		}
		
		return res;
	}
	
	public static void setRecv_msg(String str) {
		recv_msg.add(str);
	}
}
