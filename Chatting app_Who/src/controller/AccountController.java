package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class AccountController implements Initializable {
	@FXML private TextField accountID;
	@FXML private PasswordField accountPW;
	@FXML private TextField accountName;
	@FXML private PasswordField PwAgain;	// 바인딩 처리 요구
	
	@FXML private Label checker;
	@FXML private Label PwEquals;
	
	@FXML private ToggleGroup isAgree;
	@FXML private RadioButton agree;
	@FXML private RadioButton disagree;
	
	@FXML private Button request;
	@FXML private Button cancel;
	@FXML private Button chkEquals;
	
	boolean flag = false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		PwAgain.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2.equals(accountPW.getText())) {
					checker.setText("일치함.");
					flag = true;
				}
				else {
					checker.setText("비밀번호가 다릅니다.");
					flag = false;
				}
				
				isAllDone();
			}
		});
		accountPW.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2.equals(PwAgain.getText())) {
					checker.setText("일치함.");
					flag = true;
				}
				else {
					checker.setText("비밀번호가 다릅니다.");
					flag = false;
				}
				
				isAllDone();
			}
		});
		
		isAgree.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				isAllDone();
			}
		});
		
		chkEquals.setOnAction(event->checkEqualItemAction(event));
		
		request.setOnAction(event->requestAccountAction(event));
		cancel.setOnAction(event->cancelAction(event));
	}
	
	public void checkEqualItemAction(ActionEvent e) {
		
	}
	
	public void requestAccountAction(ActionEvent e) {
		if(accountID.getText().equals("") || accountPW.getText().equals("") || accountName.getText().equals("") || PwAgain.getText().equals("")) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("양식 다시작성");
			alert2.setHeaderText("작성하지 않은 곳이 있습니다.");
			alert2.setContentText("다시 확인해주세요.");
			alert2.showAndWait();
			return;
		}
		else {
			if(accountPW.getText().equals(PwAgain.getText())) {
				Boolean res = Main.getAcc().AccountRequest(accountID.getText(), accountPW.getText(), accountName.getText());
				if(res == false) {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle("실패");
					alert2.setHeaderText("회원가입에 실패했습니다.");
					alert2.setContentText("잠시 후 다시 시도해주세요.");
					alert2.showAndWait();
				}
				else {
					Alert alert3 = new Alert(AlertType.INFORMATION);
					alert3.setTitle("성공");
					alert3.setHeaderText("회원가입을 완료했습니다.");
					alert3.setContentText("이제 회원으로 로그인할 수 있습니다.");
					alert3.showAndWait();
					
					Optional<ButtonType> result = alert3.showAndWait();
					if (result.get() == ButtonType.OK) {
						Main.getLogin().getAccountStage().close();
					}
				}
				
			}
			else { // 처리 구문 요구
			}
		}
	}

	public void cancelAction(ActionEvent e) {
		Main.getLogin().getAccountStage().close();
	}
	
	// 기타 메소드
	public void isAllDone() {
		if(isAgree.getSelectedToggle().equals(agree) && flag == true) {
			request.setDisable(false);
		}
		else {
			request.setDisable(true);
		}
	}
}
