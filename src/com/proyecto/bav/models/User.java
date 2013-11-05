package com.proyecto.bav.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class User {
	
	private static final String FILE_NAME = "DatosPersonales";
	
	@SerializedName("email")
	private String email;
	
	private String password;
	
	@SerializedName("id")
	private int id;
	
	@SerializedName("authToken")
	private String authToken;
	
	@SerializedName("dni")
	private int dni;

	@SerializedName("nombre")
	private String nombre;
	
	@SerializedName("apellido")
	private String apellido;
	
	@SerializedName("telefono")
	private long telefono;
	
	@SerializedName("diaNacimiento")
	private int diaNacimiento;
	
	@SerializedName("mesNacimiento")
	private int mesNacimiento;
	
	@SerializedName("anioNacimiento")
	private int anioNacimiento;
	
	@SerializedName("fechaDeNacimiento")
	private String fechaDeNacimiento;
	
	@SerializedName("bloqueado")
	private String bloqueado;
	
	@SerializedName("isComisaria")
	private boolean isComisaria;
	
	@SerializedName("bloqueadoComisaria")
	private boolean bloqueadoComisaria;	

	public User(String et_email, String password, String et_dni, String et_nombre, String et_apellido, String et_telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento) {
		
		this.setPassword(password);		
		this.userConstructor(et_email, et_dni, et_nombre, et_apellido, et_telefono, diaNacimiento, mesNacimiento, anioNacimiento);		
	}
	
	public User(String et_email, String et_dni, String et_nombre, String et_apellido, String et_telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento) {
		
		this.userConstructor(et_email, et_dni, et_nombre, et_apellido, et_telefono, diaNacimiento, mesNacimiento, anioNacimiento);
	}
	
	private void userConstructor(String et_email, String et_dni, String et_nombre, String et_apellido, String et_telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento){
		this.setEmail(et_email);
		this.setNombre(et_nombre);
		this.setApellido(et_apellido);
		this.setDiaNacimiento(diaNacimiento);
		this.setMesNacimiento(mesNacimiento);
		this.setAnioNacimiento(anioNacimiento);
		this.setFechaNacConFormato(anioNacimiento, mesNacimiento, diaNacimiento);
		
		try {
			this.setDni(Integer.parseInt(et_dni));
		} catch (Exception e) {
			// Nada. Si tira excepción no se colocó DNI
		}
		
		try {
			this.setTelefono(Long.parseLong(et_telefono));
		} catch (Exception e) {
			// Nada. Si tira excepción no se colocó teléfono
		}
	}

	private void setFechaNacConFormato(int anioNacimiento, int mesNacimiento, int diaNacimiento) {
		
		String anioString = String.format("%04d", anioNacimiento);
		String mesString = String.format("%02d", mesNacimiento);
		String diaString = String.format("%02d", diaNacimiento);
		
		this.setFechaDeNacimiento(anioString + "-" + mesString + "-" + diaString);
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombres) {
		this.nombre = nombres;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellidos) {
		this.apellido = apellidos;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public void save(Context context) {
		
		Gson gson = new Gson();
		Type userType = new TypeToken<User>() {}.getType();
		String json = gson.toJson(this, userType);
		writeFile(json, FILE_NAME, context);
		
	}
	
	public static void destroy(Context context) {
		writeFile("", FILE_NAME, context);
	}
	
	public static String getTokenUser(Context context){		
		User user = User.getUser(context);		
		return user.getAuthToken();
	}
	
	public static int getUserId(Context context) {
		User user = User.getUser(context);		
		return user.getId();
	}
	
	public static User getUser(Context context) {
		
		User user = null;

		String json = readFile(FILE_NAME, context);
		Gson gson = new Gson();
		Type userType = new TypeToken<User>() {}.getType();

		try {
			user = gson.fromJson(json, userType);
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
		
		long telefono = getTelefono(); 
		
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String auth_token) {
		this.authToken = auth_token;
	}

	public String getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(String fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public String getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	public boolean isComisaria() {
		return isComisaria;
	}

	public void setComisaria(boolean isComisaria) {
		this.isComisaria = isComisaria;
	}

	public boolean isBloqueadoComisaria() {
		return bloqueadoComisaria;
	}

	public void setBloqueadoComisaria(boolean bloqueadoComisaria) {
		this.bloqueadoComisaria = bloqueadoComisaria;
	}
	
}
