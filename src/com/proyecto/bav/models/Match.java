package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class Match {
	
	private Integer id;
	
	@SerializedName("nombre")
	private String name;
	
	@SerializedName("provincia")
	private Province province;
	
	public Match(Integer id, String name, Province province) {
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

	public Province getProvince() {
		return province;
	}

	public void setProcince(Province province) {
		this.province = province;
	}
}
