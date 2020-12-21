package application;
	
import connectServer.ServerConnector;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {
	Parent root;
	private static Login login;
	private static ServerConnector serv = new ServerConnector();
	
	@Override
	public void start(Stage primaryStage) {
		login = new Login(primaryStage); // �α��� â ������ ȣ��
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Login getLogin() {
		return login;
	}

	public static ServerConnector getAcc() {
		return serv;
	}
}
