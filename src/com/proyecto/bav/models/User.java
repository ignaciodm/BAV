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
	
	@SerializedName("auth_token")
	private String auth_token;
	
	@SerializedName("dni")
	private int dni;

	@SerializedName("nombre")
	private String nombre;
	
	@SerializedName("apellido")
	private String apellido;
	
	@SerializedName("telefono")
	private int telefono;
	
	@SerializedName("diaNacimiento")
	private int diaNacimiento;
	
	@SerializedName("mesNacimiento")
	private int mesNacimiento;
	
	@SerializedName("anioNacimiento")
	private int anioNacimiento;

	public User(String et_email, String password, String et_dni, String et_nombre, String et_apellido, String et_telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento) {
		
		this.setEmail(et_email);
		this.setPassword(password);
		this.setNombre(et_nombre);
		this.setApellido(et_apellido);
		this.setDiaNacimiento(diaNacimiento);
		this.setMesNacimiento(mesNacimiento);
		this.setAnioNacimiento(anioNacimiento);
		
		try {
			this.setDni(Integer.parseInt(et_dni));
		} catch (Exception e) {
			// Nada. Si tira excepción no se colocó DNI
		}
		
		try {
			this.setTelefono(Integer.parseInt(et_telefono));
		} catch (Exception e) {
			// Nada. Si tira excepción no se colocó teléfono
		}			
		
	}
	
	public User(String et_email, String et_dni, String et_nombre, String et_apellido, String et_telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento) {
		
		this.setEmail(et_email);
		this.setNombre(et_nombre);
		this.setApellido(et_apellido);
		this.setDiaNacimiento(diaNacimiento);
		this.setMesNacimiento(mesNacimiento);
		this.setAnioNacimiento(anioNacimiento);
		
		try {
			this.setDni(Integer.parseInt(et_dni));
		} catch (Exception e) {
			// Nada. Si tira excepción no se colocó DNI
		}
		
		try {
			this.setTelefono(Integer.parseInt(et_telefono));
		} catch (Exception e) {
			// Nada. Si tira excepción no se colocó teléfono
		}			
		
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
	
	public static void destroy(Context context) {
		writeFile("", FILE_NAME, context);
	}
	
	public static String getTokenUser(Context context){		
		User user = User.getUser(context);		
		return user.getAuth_token();
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

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}	
	
}
