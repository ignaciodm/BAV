package com.proyecto.bav.results;

import java.util.List;

import com.google.api.client.util.Key;
import com.proyecto.bav.models.Partido;

public class ProvinceResult {
	
	@Key
    private String nombre;

    @Key
    private int id = 1;
    
    @Key
    private List<Partido> partidos;

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

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> matches) {
		this.partidos = matches;
	}
	
	@Override
    public String toString() {
    	return nombre;
    }    

}
