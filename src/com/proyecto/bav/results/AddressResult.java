package com.proyecto.bav.results;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Address;

public class AddressResult {
	
    @Key
    private Address address;

    @Key
    private int id = 1;

    public Address getAddress() {
        return this.address;
    }

    public void setAddress( Address address ) {
        this.address = address;
    }
    
    @Override
    public String toString() {
    	return address.toString();
    }

}
