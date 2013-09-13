package com.proyecto.bav.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Provincia {

	@SerializedName("id")
	public Integer id;

	@SerializedName("nombre")
	public String nombre;
	
	public List<Partido> partidos;

	public Provincia() {
		super();
	}

	public Provincia(Integer id, String nombre, List<Partido> partidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.partidos = partidos;
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

	public void setNombre(String name) {
		this.nombre = name;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}

}
