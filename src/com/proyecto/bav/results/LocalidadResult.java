package com.proyecto.bav.results;

import java.util.List;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Comisaria;

public class LocalidadResult {
	
	@Key
    private String nombre;

    @Key
    private int id = 1;
    
    @Key
    private List<Comisaria> comisarias;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
    public String toString() {
    	return nombre;
    }

	public List<Comisaria> getComisarias() {
		return comisarias;
	}

	public void setComisarias(List<Comisaria> comisarias) {
		this.comisarias = comisarias;
	}   

}
