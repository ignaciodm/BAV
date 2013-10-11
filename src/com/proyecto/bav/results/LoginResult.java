package com.proyecto.bav.results;

import com.proyecto.bav.models.User;

public class LoginResult {
	
	private User user;

	public LoginResult(User user) {
		this.setUser(user);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
