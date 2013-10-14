package com.proyecto.bav.results;

import java.util.List;

import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.User;

public class LoginResult {
	
	private User user;
	private List<Address> addresses;

	public LoginResult(User user, List<Address> addresses) {
		this.setUser(user);
		this.setAddresses(addresses);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
