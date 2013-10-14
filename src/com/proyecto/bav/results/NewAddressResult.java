package com.proyecto.bav.results;

import com.proyecto.bav.models.Address;

public class NewAddressResult {
	
	public Address address;
	
	public NewAddressResult(Address address) {
		this.setAddress(address);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
