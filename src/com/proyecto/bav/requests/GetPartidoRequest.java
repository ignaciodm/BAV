package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.results.PartidoResult;

public class GetPartidoRequest extends
		GetSpiceRequest<PartidoResult> {

	public GetPartidoRequest(Partido partido, String authToken) {
		super(PartidoResult.class);
		this.setPath("/partidos/" + partido.getId() + ".json?authToken=" + authToken);
	}

	protected PartidoResult parseResponse(final HttpResponse response) throws IOException {
		
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
		Partido partido = gson.fromJson(json, Partido.class);
		
		PartidoResult result = new PartidoResult();
		result.setLocalidades(partido.getLocalidades());
		result.setId(partido.getId());
		result.setNombre(partido.getNombre());
		
		return result;
	}
}
