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
		alert.setTitle("종료?");
		alert.setHeaderText("프로그램을 종료하시겠습니까?");
		alert.setContentText("ok를 누르면 종료합니다.");

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
			if(AccountedUser.isSelected() && (id.getText().equals("") || pw.getText().equals(""))) {	// 아무것도 입력 안 되 있으면 경고문
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("ID/PW입력 오류");
				alert2.setHeaderText("ID 또는 비밀번호를 입력해주세요.");
				alert2.setContentText("ID 또는 비밀번호를 다시 확인해주세요.");
				alert2.showAndWait();
				return;
			}
			else if(guest.isSelected() && id.getText().equals("")) {									// 아무것도 입력 안되어 있으면 or 뭐가 빠졌으면 경고문
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("이름입력 오류");
				alert2.setHeaderText("채팅에 사용할 이름을 입력해주세요.");
				alert2.setContentText("이름을 다시 확인해주세요.");
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
			alert1.setTitle("서버에 연결할 수 없습니다.");
			alert1.setHeaderText("서버에 연결할 수 없습니다.");
			alert1.setContentText("오류코드: ");
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
