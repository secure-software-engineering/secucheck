package de.fraunhofer.iem.secucheck.todolist.model;

import java.util.ArrayList;

import javax.validation.Valid;

public class UserList {
	
	@Valid
	private ArrayList<User> userList = new ArrayList<User>();
	
	public UserList() {}
	
	public UserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

}
