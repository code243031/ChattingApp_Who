package server;

import java.io.IOException;
import java.util.ArrayList;

public class UserList {
	private ArrayList<User> Users;
	
	public UserList() {
		Users = new ArrayList<>();
	}

	public ArrayList<User> getUsers() {
		return Users;
	}
	
	public void disconnectUser(String name) throws IOException { // 요청을 받으면 모든 소켓 연결 해제
		for(int i = 0; i < Users.size(); i++) {
			if(Users.get(i).getCustomName().equals(name)) {
				User tar = Users.get(i);
				tar.fbox.interrupt();
				tar.mbox.interrupt();
				tar.getSocket().close();
				tar.getRecvAddress().close();
				tar.requestSendUserList.interrupt();
				System.out.println("["+ServerTime.getDate()+ServerTime.getTime()+"]"+"'"+tar.getCustomName()+"'"+"is disconnect.");
				Users.remove(i);
			}
		}
	}

	public void addUser(User user) {
		Users.add(user);
	}

}
