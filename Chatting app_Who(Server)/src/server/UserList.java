package server;

import java.util.ArrayList;

public class UserList {
	private ArrayList<User> Users;
	
	public UserList() {
		Users = new ArrayList<>();
	}

	public ArrayList<User> getUsers() {
		return Users;
	}

	public void addUser(User user) {
		Users.add(user);
	}

	
}
