package com.proyecto.bav.results;

import java.util.List;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Provincia;

public class ProvincesResult {
	
	@Key
    private String nombre = "Provincias";
	
    @Key
    private int id = 1;
    
    @Key
    private List<Provincia> provincias;
    
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

    public List<Provincia> getProvincias() {
        return this.provincias;
    }

    public void setProvincias(List<Provincia> provincias ) {
        this.provincias = provincias;
    }
    
    @Override
    public String toString() {
    	return nombre;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
