package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class AccountController implements Initializable {
	@FXML private TextField accountID;
	@FXML private TextField accountPW;
	@FXML private TextField accountName;
	@FXML private TextField PwAgain;	// 바인딩 처리 요구
	
	@FXML private Label PwEquals;
	
	@FXML private ToggleGroup isAgree;
	@FXML private RadioButton agree;
	@FXML private RadioButton disagree;
	
	@FXML private Button request;
	@FXML private Button cancel;
	@FXML private Button chkEquals;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isAgree.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if(isAgree.getSelectedToggle().equals(agree)) {
					request.setDisable(false);
				}
				else if(isAgree.getSelectedToggle().equals(disagree)) {
					request.setDisable(true);
				}
			}
		});
		
		request.setOnAction(event->requestAccountAction(event));
		cancel.setOnAction(event->cancelAction(event));
		
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
	}

	public void cancelAction(ActionEvent e) {
		Main.getLogin().getAccountStage().close();
	}
}
