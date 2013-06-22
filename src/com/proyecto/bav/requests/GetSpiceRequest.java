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

public class GetSpiceRequest extends
		SimpleSpiceRequest<ProvinceResult> {

	private String path;

	public GetSpiceRequest() {
		super(ProvinceResult.class);
		this.path = "/provincias.json";
	}

	@Override
	protected HttpRequest getHttpRequest() throws Exception {
		return this.getGETHttpRequest();
	}

	@Override
	protected String getURL() {
		return this.getBaseUrl() + this.path;
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
		String string = "{\"provincias\":[";
		int i = json.indexOf(string);
		json = json.substring("{\"provincias\":[".length() -1 , json.length()-1);
		
		
		Type provincesType = new TypeToken<List<Province>>() {																																																																						}.getType();
		List<Province> provincias = null;
		try {
			provincias = gson.fromJson(json, provincesType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ProvinceResult result = new ProvinceResult();
		result.setProvincias(provincias);
		
		return result;
	}

//	private String content;

//	@SuppressWarnings("unchecked")
//	public PostAddressSpiceRequest(Address address) {
//		super(AddressResult.class, "/addresses.json");
//		Gson gson = new Gson();
//		this.content = gson.toJson(address);
//	}
//
//	@Override
//	protected AddressResult doRequest(HttpRequestFactory requestFactory) throws IOException {
//		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", content);
//		return this.doPost(requestFactory, requestContent);
//	}

}
