package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.util.EntityUtils;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Province;
import com.proyecto.bav.results.ProvinceResult;
import com.proyecto.bav.results.ProvincesResult;

public class GetProvinceRequest extends
		GetSpiceRequest<ProvinceResult> {

	public GetProvinceRequest(Province province) {
		super(ProvinceResult.class);
		this.setPath("/provincias/" + province.getId() + ".json");
	}

	protected ProvinceResult parseResponse(final HttpResponse response)
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
		Province province = gson.fromJson(json, Province.class);

		
		ProvinceResult result = new ProvinceResult();
		result.setPartidos(province.getMatches());
		result.setId(province.getId());
		result.setNombre(province.getName());
		
		return result;
	}
}
