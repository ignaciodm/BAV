package com.proyecto.bav.models;

import com.google.gson.annotations.SerializedName;

public class Province {

	public Integer id;

	@SerializedName("nombre")
	public String name;

	public Province() {
		super();
	}

	public Province(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
