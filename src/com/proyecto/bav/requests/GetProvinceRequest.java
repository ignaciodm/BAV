package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.results.ProvinceResult;

public class GetProvinceRequest extends
		GetSpiceRequest<ProvinceResult> {

	public GetProvinceRequest(Provincia province) {
		super(ProvinceResult.class);
		this.setPath("/provincias/" + province.getId() + ".json" + "?auth_token=7b24afd3345007ef8e33b192ac7b475b75922749");
	}

	protected ProvinceResult parseResponse(final HttpResponse response) throws IOException {
		
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
		Provincia province = gson.fromJson(json, Provincia.class);
		
		ProvinceResult result = new ProvinceResult();
		result.setPartidos(province.getPartidos());
		result.setId(province.getId());
		result.setNombre(province.getNombre());
		
		return result;
	}
}
