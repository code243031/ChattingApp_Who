package controller;

import java.net.ConnectException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;
import connectServer.Reciever;
import connectServer.ServerConnector;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginScreenController implements Initializable {
	@FXML private Label labelID;
	@FXML private Button exitBtn;
	@FXML private Button loginBtn;
	@FXML private Button accountBtn;
	@FXML private TextField id;
	@FXML private PasswordField pw;
	
	@FXML private ToggleGroup loginMode;
	@FXML private RadioButton AccountedUser;
	@FXML private RadioButton guest;
	
	private OpenChatRoom open;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitBtn.setOnAction(event->exitButtonAction(event));
		loginBtn.setOnAction(event->loginButtonAction(event));
		accountBtn.setOnAction(event->accountButtonAction(event));
		
		loginMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if (loginMode.getSelectedToggle() != null) {
					if(loginMode.getSelectedToggle().equals(guest)) {
						pw.setDisable(true);
						labelID.setText("Name");
						id.setPromptText("name");
					}
					else if(loginMode.getSelectedToggle().equals(AccountedUser)) {
						pw.setDisable(false);
						labelID.setText("ID");
						id.setPromptText("User ID");
					}
				}
			}
			
		});
	}
	
	public void exitButtonAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("����?");
		alert.setHeaderText("���α׷��� �����Ͻðڽ��ϱ�?");
		alert.setContentText("ok�� ������ �����մϴ�.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}
		else {
			return;
		}
	}
	public void loginButtonAction(ActionEvent e)
	{
		ServerConnector con = new ServerConnector();
		try {
			if(AccountedUser.isSelected() && (id.getText().equals("") || pw.getText().equals(""))) {	// �ƹ��͵� �Է� �� �� ������ ���
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("ID/PW�Է� ����");
				alert2.setHeaderText("ID �Ǵ� ��й�ȣ�� �Է����ּ���.");
				alert2.setContentText("ID �Ǵ� ��й�ȣ�� �ٽ� Ȯ�����ּ���.");
				alert2.showAndWait();
				return;
			}
			else if(guest.isSelected() && id.getText().equals("")) {									// �ƹ��͵� �Է� �ȵǾ� ������ or ���� �������� ���
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("�̸��Է� ����");
				alert2.setHeaderText("ä�ÿ� ����� �̸��� �Է����ּ���.");
				alert2.setContentText("�̸��� �ٽ� Ȯ�����ּ���.");
				alert2.showAndWait();
				return;
			}
			else {
				if(AccountedUser.isSelected() && con.connectServer("localhost", 5006, id.getText().toString(), pw.getText().toString())) {
					open = new OpenChatRoom(con, id.getText().toString(), new Reciever());
				}
				else if(guest.isSelected() && con.connectServer("localhost", 5006, id.getText().toString())) {
					open = new OpenChatRoom(con, id.getText().toString(), new Reciever());
				}
				else {
					throw new ConnectException();
				}
			}
			
		} catch (ConnectException e1) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("������ ������ �� �����ϴ�.");
			alert1.setHeaderText("������ ������ �� �����ϴ�.");
			alert1.setContentText("�����ڵ�: ");
			alert1.showAndWait();
		}
	}
	
	public void accountButtonAction(ActionEvent e) {

		Main.getLogin().account();
	}

	public OpenChatRoom getOpenChatRoom() {
		return open;
	}
}
