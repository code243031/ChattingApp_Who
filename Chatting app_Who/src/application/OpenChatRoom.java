package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OpenChatRoom {
	
	public OpenChatRoom() {
		try {
			Stage homeStage = new Stage();
			Parent home = FXMLLoader.load(this.getClass().getResource("OpenChatRoom.fxml"));
			
			Main.getLogin().getStage().hide(); // �α��� ��ư�� ������ �α��� â�� ����
			
			Scene scene = new Scene(home);
			homeStage.setTitle("ä�ù�");
			homeStage.setScene(scene);
			homeStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
