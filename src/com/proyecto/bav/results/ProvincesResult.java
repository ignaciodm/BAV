package com.proyecto.bav.results;

import java.util.List;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.requests.RequestResult;

public class ProvincesResult implements RequestResult {
	
    @Key
    private List<Provincia> provincias;

    @Key
    private int id = 1;

    public List<Provincia> getProvincias() {
        return this.provincias;
    }

    public void setProvincias( List<Provincia> province ) {
        this.provincias = province;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ( provincias == null ? 0 : provincias.hashCode() );
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
        ProvincesResult other = (ProvincesResult) obj;
        if ( id != other.id ) {
            return false;
        }
        if ( provincias == null ) {
            if ( other.provincias != null ) {
                return false;
            }
        } else if ( !provincias.equals( other.provincias ) ) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
    	return provincias.toString();
    }

}
