package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class PoliceStation {
	
	private Integer id;
	@SerializedName("nombre")
	private String name;
	
	@SerializedName("localidad")
	private Localidad locality;
	
	public PoliceStation(Integer id, String name, Localidad locality) {
		super();
		this.id = id;
		this.name = name;
		this.locality = locality;
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

	public Localidad getLocality() {
		return locality;
	}

	public void setLocality(Localidad locality) {
		this.locality = locality;
	}
	
	
	
	
	

}
