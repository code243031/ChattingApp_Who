package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OpenChatRoomController implements Initializable {

	@FXML private Button send;
	
	@FXML private MenuItem file;
	@FXML private MenuItem delete;
	@FXML private MenuItem about;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		send.setOnAction(event->sendButtonAction(event));
		about.setOnAction(event->aboutItemAction(event));
	}
	
	public void sendButtonAction(Event e) {
		Date d = new Date();
		String now = d.toString();
		System.out.println("["+now+"]: Sucess send message.");
		
	}
	
	public void aboutItemAction(Event e) {
		Stage about = new Stage();
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,400,400);
		about.setScene(scene);
		about.setResizable(false);
		about.initModality(Modality.APPLICATION_MODAL);
		about.setTitle("About WHO");
		about.show();
	}

}
