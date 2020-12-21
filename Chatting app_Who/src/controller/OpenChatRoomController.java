package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.OpenChatRoom;
import application.User;
import connectServer.ServerConnector;

import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OpenChatRoomController implements Initializable {

	@FXML private Button send;
	@FXML private Button fileOut;
	
	@FXML private TextArea chatLog;
	@FXML private TextArea typeMsg;
	@FXML private ListView<User> userList;
	
	@FXML private MenuItem menuUser;
	@FXML private MenuItem menuExit;
	@FXML private MenuItem menuLogout;
	@FXML private MenuItem menuSetting;
	@FXML private MenuItem menuAbout;
	
	int index = 0;
	private Thread pressMsg;
	private Thread list_refresh;
	
	FileChooser choose = new FileChooser();
	
	// ContextMenus
	ContextMenu userListCon = new ContextMenu();
	MenuItem u_menu1 = new MenuItem("회원 정보..");
	MenuItem u_menu2 = new MenuItem("1대1 채팅 신청");
	
	ContextMenu chatListCon = new ContextMenu();
	MenuItem c_menu1 = new MenuItem("서버시간");
	
	ObservableList<User> now;
	private String[] add_now;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Thread isClose = new Thread() {
			@Override
			public void run() {
				while (true) {
					if(ServerConnector.isServerConnect == false) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("오류!");
						alert.setHeaderText("서버와 연결할 수 없습니다.");
						alert.setContentText("ok를 누르면 종료합니다.");
				
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							OpenChatRoom.connect.closeSocket();
							Platform.exit();
							System.exit(1);
						}
					}
					else {
						continue;
					}
				}
				
			}
		};
		isClose.setDaemon(true);
		isClose.start();
		// 우클릭 메뉴
		userListCon.getItems().add(u_menu1);
		userListCon.getItems().add(u_menu2);
		u_menu1.setOnAction(event->userRightClickAction(event));
		u_menu2.setOnAction(event->userRightClickAction(event));

		chatListCon.getItems().add(c_menu1);
		
		// 접속한 사용자들 실시간 갱신
		list_refresh = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						sleep(1000);
						userList.refresh();
						
					} catch (InterruptedException e) {
						if(OpenChatRoom.connect.getSocket().isClosed()) {
							System.out.println("서버다운");
							break;
						}
					}
				}
				System.exit(0);
			}
		};
		list_refresh.setDaemon(true);
		list_refresh.start();
		
		chatLog.setContextMenu(chatListCon);
		
		pressMsg = new Thread() {	// 수신된 메시지를 가져오는 스레드
			@Override
			public void run() {
				while(true) {
					try {
						sleep(1000);
						String tmp = OpenChatRoom.getRecv_msg();
						if(tmp == null) {
							continue;
						}
						else {
							chatLog.insertText(index, tmp); // indexoutofboundsException 발생중, 해결법은?
							index += (tmp).length();
						}
						
					} catch (InterruptedException e) {
						if(OpenChatRoom.connect.getSocket().isClosed()) {
							System.out.println(e);
							break;
						}
					}
				}
				System.exit(0);
			}
		};
		pressMsg.setDaemon(true);
		pressMsg.start();
		
		send.setOnAction(event->sendButtonAction(event));
		//입력 된 상태에서 엔터키 누르면 메세지 전송 기능 실행
		typeMsg.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if (typeMsg.getText().equals("")) { // 아무것도 입력되지 않은 경우는 리턴
					return;
				}
				
				if(arg0.getCode().toString().equals("ENTER")) {
					sendButtonAction(arg0);
				}
			}
		});
		
		//menu Items
		menuUser.setOnAction(event->menuUserAction(event));
		menuLogout.setOnAction(event->menuLogoutAction(event));
		
		fileOut.setOnAction(event->sendFileAction(event));
		
		add_now = new String[10];
		for (int i = 0; i < OpenChatRoom.reciever.userList.size(); i++) {
			add_now[i] = OpenChatRoom.reciever.userList.get(i).getCustomName();
		}
		
		now = FXCollections.observableList(OpenChatRoom.reciever.userList);
		// 유저 리스트 목록 추가(이슈 발생 중.)
		userList.setItems(now);
		userList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		userList.setContextMenu(userListCon);
	}
	
	public void sendFileAction(ActionEvent e) {
		File f = null;
        try {
        	f = choose.showOpenDialog(new Stage());
			OpenChatRoom.fTrans.sendFile(f.getAbsolutePath());
		} catch (IOException e1) {
			if(e1.getMessage().equals("Connection reset by peer")) {
				e1.printStackTrace();
			}
		} catch (NullPointerException e2) {
			if(f == null) {
				// 파일 선택이 취소된 경우 -> 탈출
			}
		}
    }
	
	public void userRightClickAction(ActionEvent e) {
		User tar = null;
		ObservableList<User> name;
		name = userList.getSelectionModel().getSelectedItems();
		for (User n : name) {
            tar = n;
        }
		
		for(int i = 0; i < OpenChatRoom.reciever.userList.size(); i++) {
			if (OpenChatRoom.reciever.userList.get(i).getCustomName().equals(tar)) {
				try {
					OpenChatRoom.reciever.userList.get(0).userDialog(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else {
				continue;
			}
		}
	}
	
	public void menuUserAction(ActionEvent e) {
		System.out.println("미구현");
	}
	
	public void menuLogoutAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃?");
		alert.setHeaderText("로그아웃 하시겠습니까?");
		alert.setContentText("ok를 누르면 로그아웃합니다.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			OpenChatRoom.connect.closeSocket();
			OpenChatRoom.homeStage.close();
			
			Main.getLogin().getStage().show();
		}
		else if (result.get() == ButtonType.CANCEL) {
			return;
		}
	}
	
	public void menuExitAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("종료?");
		alert.setHeaderText("프로그램을 종료하시겠습니까?");
		alert.setContentText("ok를 누르면 종료합니다.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			OpenChatRoom.connect.closeSocket();
			Platform.exit();
		}
		else if (result.get() == ButtonType.CANCEL) {
			return;
		}
	}
	
	public void sendButtonAction(Event e) {
		Date d = new Date();
		String now = d.toString();
		String msg = "[" + OpenChatRoom.name + "]" +typeMsg.getText()+"\n";
		try {
			OutputStream output = OpenChatRoom.connect.getSocket().getOutputStream();

			byte[] w_data = msg.getBytes("UTF-8");
			output.write(w_data);
			output.flush();
		} catch (IOException e1) {
			if(e1.getMessage().equals("Connection reset by peer") || e1.getMessage().equals("Socket is closed")) {	//	서버가 다운된 경우의 처리
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("오류!");
				alert.setHeaderText("서버와 연결할 수 없습니다.");
				alert.setContentText("ok를 누르면 종료합니다.");
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					OpenChatRoom.connect.closeSocket();
					Platform.exit();
					System.exit(1);
				}
			}
			else {
				e1.printStackTrace();
			}
		} catch (IndexOutOfBoundsException e2) {
			e2.printStackTrace();
		}
		System.out.println("["+now+"]: Sucess send message. >>> " + msg);	// 디버깅을 위한 로그
		typeMsg.clear();
	}
	
}
