package com.proyecto.bav.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Province {

	public Integer id;

	@SerializedName("nombre")
	public String name;
	
	@SerializedName("partidos")
	public List<Match> matches;

	public Province() {
		super();
	}

	public Province(Integer id, String name, List<Match> matches) {
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
	
	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}


}
