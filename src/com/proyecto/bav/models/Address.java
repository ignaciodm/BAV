package com.proyecto.bav.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class Address {

	private static final String FILE_NAME = "direcciones";

	@SerializedName("descripcion")
	private String description;
	
	@SerializedName("calle")
	private String street;

	@SerializedName("numero")
	private Integer number;

	public Address(String description, String street, Integer number) {
		this.description = description;
		this.street = street;
		this.number = number;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public static void save(Address address, Context context) {
		Gson gson = new Gson();
		Type addressType = new TypeToken<List<Address>>() {}.getType();

		List<Address> addresses = getAddresses(context);
		addresses.add(address);

		String json = gson.toJson(addresses, addressType);

		writeFile(json, FILE_NAME, context);
	}

	public static List<Address> getAddresses(Context context) {
		List<Address> addresses = new ArrayList<Address>();

		String json = readFile(FILE_NAME, context);
		Gson gson = new Gson();
		Type addressType = new TypeToken<List<Address>>() {}.getType();

		try {
			addresses = gson.fromJson(json, addressType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addresses;
	}

	private static String readFile(String filename, Context context) {
		StringBuilder sb = new StringBuilder();
		FileInputStream inputStream;

		try {
			inputStream = context.openFileInput(filename);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	private static void writeFile(String json, String filename, Context context) {
		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			outputStream.write(json.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
