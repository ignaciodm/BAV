package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.results.ProvincesResult;

public class GetProvincesRequest extends
		GetSpiceRequest<ProvincesResult> {

	public GetProvincesRequest() {
		super(ProvincesResult.class);
		this.setPath("/provincias.json");
	}

	protected ProvincesResult parseResponse(final HttpResponse response)
			throws IOException {
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
		String string = "{\"provincias\":[";
		int i = json.indexOf(string);
		json = json.substring("{\"provincias\":[".length() -1 , json.length()-1);
		
		
		Type provincesType = new TypeToken<List<Provincia>>() {																																																																						}.getType();
		List<Provincia> provincias = null;
		try {
			provincias = gson.fromJson(json, provincesType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ProvincesResult result = new ProvincesResult();
		result.setProvincias(provincias);
		
		return result;
	}
}
