package com.proyecto.bav.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Localidad {
	
	@SerializedName("id")
	private Integer id;
	
	@SerializedName("nombre")
	private String nombre;
	
	public List<Comisaria> comisarias;
	
	public Localidad(Integer id, String name) {
		super();
		this.id = id;
		this.nombre = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Comisaria> getComisarias() {
		return comisarias;
	}

	public void setComisarias(List<Comisaria> comisarias) {
		this.comisarias = comisarias;
	}

}
