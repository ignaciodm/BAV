package com.proyecto.bav.results;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Province;
import com.proyecto.bav.requests.RequestResult;

public class ProvinceResu  implements RequestResult{
	    @Key
	    private Province provincia;

	    public Province getAddress() {
	        return this.provincia;
	    }

	    public void setAddress( Province province ) {
	        this.provincia = province;
	    }

	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ( provincia == null ? 0 : provincia.hashCode() );
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
	        ProvinceResu other = (ProvinceResu) obj;
	        if ( provincia == null ) {
	            if ( other.provincia != null ) {
	                return false;
	            }
	        } else if ( !provincia.equals( other.provincia ) ) {
	            return false;
	        }
	        return true;
	    }
	    
	    @Override
	    public String toString() {
	    	return provincia.toString();
	    }
}
