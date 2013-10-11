package com.proyecto.bav.results;

import com.proyecto.bav.models.Address;

public class AddressResult {
	
	public Address address;
	
	public AddressResult(Address address) {
		this.setAddress(address);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
