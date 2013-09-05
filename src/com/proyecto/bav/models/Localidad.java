package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class Localidad {
	
	private Integer id;
	
	@SerializedName("nombre")
	private String name;
	
	@SerializedName("localidad")
	private Partido match;
	
	public Localidad(Integer id, String name, Partido match) {
		super();
		this.id = id;
		this.name = name;
		this.match = match;
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
	
	public Partido getMatch() {
		return match;
	}
	
	public void setMatch(Partido match) {
		this.match = match;
	}
	
	

}
