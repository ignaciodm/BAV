package com.proyecto.bav.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Provincia {

	public Integer id;

	@SerializedName("nombre")
	public String name;
	
	@SerializedName("partidos")
	public List<Partido> matches;

	public Provincia() {
		super();
	}

	public Provincia(Integer id, String name, List<Partido> matches) {
		super();
		this.id = id;
		this.name = name;
		this.matches = matches;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Partido> getMatches() {
		return matches;
	}

	public void setMatches(List<Partido> matches) {
		this.matches = matches;
	}


}
