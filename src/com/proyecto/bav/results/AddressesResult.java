package com.proyecto.bav.results;

import java.util.List;

import com.proyecto.bav.models.Address;

public class AddressesResult {
	
	private List<Address> addresses;

	public AddressesResult(List<Address> addresses2) {
		this.setAddresses(addresses2);
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
