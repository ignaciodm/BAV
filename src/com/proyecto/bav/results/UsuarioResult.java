package com.proyecto.bav.results;

import com.proyecto.bav.models.User;

public class UsuarioResult {
	
	private User user;

	public UsuarioResult(User user) {
		this.setUser(user);
	}

	public UsuarioResult() {}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
