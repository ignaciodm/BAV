package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class Partido {
	
	private Integer id;
	
	@SerializedName("nombre")
	private String name;
	
	@SerializedName("provincia")
	private Provincia province;
	
	public Partido(){}
	
	public Partido(Integer id, String name, Provincia province) {
		super();
		this.id = id;
		this.name = name;
		this.province = province;
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

	public Provincia getProvince() {
		return province;
	}

	public void setProcince(Provincia province) {
		this.province = province;
	}
}
