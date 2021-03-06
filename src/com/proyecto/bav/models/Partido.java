package com.proyecto.bav.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Partido {
	
	@SerializedName("id")
	private Integer id;
	
	@SerializedName("nombre")
	private String nombre;
	
	public List<Localidad> localidades;
	
	public Partido(){}
	
	public Partido(Integer id, String nombre) {
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

	public List<Localidad> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<Localidad> localidades) {
		this.localidades = localidades;
	}
}
