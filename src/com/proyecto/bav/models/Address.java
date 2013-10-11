package com.proyecto.bav.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class Address {

	private static final String FILE_NAME = "direcciones";

	@SerializedName("id")
	private int id;
	
	@SerializedName("descripcion")
	private String description;
	
	@SerializedName("calle")
	private String calle;

	@SerializedName("numero")
	private Integer numero;
	
	@SerializedName("piso")
	private Integer piso;
	
	@SerializedName("dpto")
	private String dpto;
	
	@SerializedName("entreCalle1")
	private String entreCalle1;
	
	@SerializedName("entreCalle2")
	private String entreCalle2;
	
	@SerializedName("provincia")
	private Provincia provincia;
	
	@SerializedName("partido")
	private Partido partido;
	
	@SerializedName("localidad")
	private Localidad localidad;
	
	@SerializedName("localidad_id")
	private int localidad_id;
	
	@SerializedName("policeStation")
	private Comisaria policeStation;
	
	@SerializedName("comisaria_id")
	private int comisaria_id;
	
	public Address(String descripcion, 
				String street,
				String numero, 
				String piso,
				String dpto, 
				String entreCalle1,
				String entreCalle2, 
				Provincia provincia, 
				Partido partido, 
				Localidad localidad, 
				Comisaria policeStation) {
		
		this.setDescription(descripcion);
		this.setCalle(street);
		this.setDpto(dpto);
		this.setEntreCalle1(entreCalle1);
		this.setEntreCalle2(entreCalle2);
		this.setProvincia(provincia);
		this.setPartido(partido);
		this.setLocalidad(localidad);
		this.setLocalidad_id(localidad.getId());
		this.setPoliceStation(policeStation);
		this.setComisaria_id(policeStation.getId());
		
		try{
			this.setNumero(Integer.parseInt(numero));
		} catch (Exception e) { 
			// Nada, el número es vacío
		}
		
		try{
			this.setPiso(Integer.parseInt(piso));
		} catch (Exception e) { 
			// Nada, el piso es vacío
		}
		
	}

	public Address(int addressID, 
			String descripcion, 
			String street,
			String numero, 
			String piso,
			String dpto, 
			String entreCalle1,
			String entreCalle2, 
			Provincia provincia, 
			Partido partido, 
			Localidad localidad, 
			Comisaria policeStation) {
		
		this.setId(addressID);
		this.setDescription(descripcion);
		this.setCalle(street);
		this.setDpto(dpto);
		this.setEntreCalle1(entreCalle1);
		this.setEntreCalle2(entreCalle2);
		this.setProvincia(provincia);
		this.setPartido(partido);
		this.setLocalidad(localidad);
		this.setLocalidad_id(localidad.getId());
		this.setPoliceStation(policeStation);
		this.setComisaria_id(policeStation.getId());
		
		try{
			this.setNumero(Integer.parseInt(numero));
		} catch (Exception e) { 
			// Nada, el número es vacío
		}
		
		try{
			this.setPiso(Integer.parseInt(piso));
		} catch (Exception e) { 
			// Nada, el piso es vacío
		}
		
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumero() {		
		return numero;
	}

	public void setNumero(Integer number) {
		this.numero = number;
	}
	
	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	public String getEntreCalle1() {
		return entreCalle1;
	}

	public void setEntreCalle1(String entreCalle1) {
		this.entreCalle1 = entreCalle1;
	}

	public String getEntreCalle2() {
		return entreCalle2;
	}

	public void setEntreCalle2(String entreCalle2) {
		this.entreCalle2 = entreCalle2;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Comisaria getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(Comisaria policeStation) {
		this.policeStation = policeStation;
	}

	public static void save(Address address, Context context) {
		
		Gson gson = new Gson();
		Type addressType = new TypeToken<List<Address>>() {}.getType();

		List<Address> addresses = getAddresses(context);
		addresses.remove(address);
		addresses.add(address);

		String json = gson.toJson(addresses, addressType);
		writeFile(json, FILE_NAME, context);
		
	}
	
	public static void delete(int position, Context context) {
		
		Gson gson = new Gson();
		Type addressType = new TypeToken<List<Address>>() {}.getType();

		List<Address> addresses = getAddresses(context);
		addresses.remove(position);

		String json = gson.toJson(addresses, addressType);

		writeFile(json, FILE_NAME, context);
		
	}
	
	public static void destroy(Context context) {
		writeFile("", FILE_NAME, context);
	}

	public static List<Address> getAddresses(Context context) {
		
		List<Address> addresses = null;

		String json = readFile(FILE_NAME, context);
		Gson gson = new Gson();
		Type addressType = new TypeToken<List<Address>>() {}.getType();

		try {
			addresses = gson.fromJson(json, addressType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (addresses == null) {
			addresses = new ArrayList<Address>();
		}		
		
		// Para ordenar por Descripción
		Collections.sort(addresses, new Comparator<Address>() {

			@Override
			public int compare(Address a1, Address a2) {
				return a1.getDescription().compareTo(a2.getDescription());
			}
			
	    });
		
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object obj){		
		Address a = (Address) obj;		
		if(this.getId() == a.getId())
			return true;		
		return false;		
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getLocalidad_id() {
		return localidad_id;
	}

	public void setLocalidad_id(int localidad_id) {
		this.localidad_id = localidad_id;
	}

	public int getComisaria_id() {
		return comisaria_id;
	}

	public void setComisaria_id(int comisaria_id) {
		this.comisaria_id = comisaria_id;
	}	

}
