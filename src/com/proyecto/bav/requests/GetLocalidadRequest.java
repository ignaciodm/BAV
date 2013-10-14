package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.results.LocalidadResult;

public class GetLocalidadRequest extends
		GetSpiceRequest<LocalidadResult> {

	public GetLocalidadRequest(Localidad localidad, String authToken) {
		super(LocalidadResult.class);
		this.setPath("/localidades/" + localidad.getId() + ".json?authToken=" + authToken);
	}

	protected LocalidadResult parseResponse(final HttpResponse response) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		
		try {
			
			inputStream = response.getContent();;
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

		String json =  sb.toString();
		Gson gson = new Gson();
		Localidad localidad = gson.fromJson(json, Localidad.class);
		
		LocalidadResult result = new LocalidadResult();
		result.setComisarias(localidad.getComisarias());
		result.setId(localidad.getId());
		result.setNombre(localidad.getNombre());
		
		return result;
	}
}
