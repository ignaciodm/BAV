package com.proyecto.bav.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

@SuppressLint("NewApi")
public class User {
	
	private static final String FILE_NAME = "DatosPersonales";
	
	@SerializedName("email")
	private String email;
	
	@SerializedName("dni")
	private int dni;

	@SerializedName("nombres")
	private String nombres;
	
	@SerializedName("apellidos")
	private String apellidos;
	
	@SerializedName("telefono")
	private int telefono;
	
	@SerializedName("diaNacimiento")
	private int diaNacimiento;
	
	@SerializedName("mesNacimiento")
	private int mesNacimiento;
	
	@SerializedName("anioNacimiento")
	private int anioNacimiento;

	public User(String et_email, String et_dni, String et_nombre, String et_apellido, String et_telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento) {
		
		this.setEmail(et_email);
		this.setNombres(et_nombre);
		this.setApellidos(et_apellido);
		this.setDiaNacimiento(diaNacimiento);
		this.setMesNacimiento(mesNacimiento);
		this.setAnioNacimiento(anioNacimiento);
		
		if(!(et_dni.isEmpty()))
			this.setDni(Integer.parseInt(et_dni));
		
		if(!(et_telefono.isEmpty()))
			this.setTelefono(Integer.parseInt(et_telefono));
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public void save(Context context) {
		
		Gson gson = new Gson();
		Type userType = new TypeToken<User>() {}.getType();
		String json = gson.toJson(this, userType);
		writeFile(json, FILE_NAME, context);
		
	}
	
	public static User getUser(Context context) {
		
		User user = null;

		String json = readFile(FILE_NAME, context);
		Gson gson = new Gson();
		Type addressType = new TypeToken<User>() {}.getType();

		try {
			user = gson.fromJson(json, addressType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
	
	private static void writeFile(String json, String filename, Context context) {
		
		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(json.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public int getDiaNacimiento() {
		return diaNacimiento;
	}

	public void setDiaNacimiento(int diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	public int getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(int mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public int getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(int anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public String getTelefonoString() {
		
		int telefono = getTelefono(); 
		
		if(telefono == 0)
			return "";
		
		return String.valueOf(telefono);
	}

	public CharSequence getDniString() {

		int dni = getDni(); 
		
		if(dni == 0)
			return "";
		
		return String.valueOf(dni);
	}
	
	
}
