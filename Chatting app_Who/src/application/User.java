package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class User{
	// ���̾�α� ����
	private Stage dialog;
	
	// �̸� �� ��Ÿ ������(����� �̸���.)
	private final String customName;
	
	
	public User(String name) {
		customName = name;
	}

	public String getCustomName() {
		return customName;
	}
	
	public void userDialog(Boolean flag) throws IOException{
		dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(OpenChatRoom.homeStage);
		dialog.setTitle("����� ����-" + customName);
	
		Parent parent = FXMLLoader.load(getClass().getResource("UserInfo.fxml"));
		Label name = (Label) parent.lookup("#name");
		name.setText(customName);
		Button request = (Button) parent.lookup("#chatRequest");
		request.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new PersonalChatRoom(new Stage(), customName);	// ��Stage, ���� �÷����� ����, ��� ����
			}
		});
		Button cancel = (Button) parent.lookup("#cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dialog.close();
			}
		});
		
		Scene scene = new Scene(parent);

		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
	}
}
