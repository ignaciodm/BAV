package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class Comisaria {
	
	@SerializedName("id")
	private Integer id;
	
	@SerializedName("nombre")
	private String nombre;
	
	public Comisaria(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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

}
