package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class Locality {
	
	private Integer id;
	
	@SerializedName("nombre")
	private String name;
	
	@SerializedName("localidad")
	private Match match;
	
	public Locality(Integer id, String name, Match match) {
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
	
	public Match getMatch() {
		return match;
	}
	
	public void setMatch(Match match) {
		this.match = match;
	}
	
	

}
