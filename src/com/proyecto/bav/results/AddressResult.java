package com.proyecto.bav.results;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.requests.RequestResult;

public class AddressResult implements RequestResult {
	
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ( address == null ? 0 : address.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        AddressResult other = (AddressResult) obj;
        if ( id != other.id ) {
            return false;
        }
        if ( address == null ) {
            if ( other.address != null ) {
                return false;
            }
        } else if ( !address.equals( other.address ) ) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
    	return address.toString();
    }

}
