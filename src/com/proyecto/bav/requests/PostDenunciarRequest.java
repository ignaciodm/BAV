package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.proyecto.bav.results.DenunciaResult;

public class PostDenunciarRequest extends PostSpiceRequest<DenunciaResult> {

	public PostDenunciarRequest(String content) {		
		super(DenunciaResult.class);
		this.setPath("/denuncias.json");
		
		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", content);
		this.setHttpContent(requestContent);		
	}
	
	protected DenunciaResult parseResponse(final HttpResponse response) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		
		try {
			
			inputStream = response.getContent();
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
			
		DenunciaResult denunciaResult = new DenunciaResult();
		
		return denunciaResult;
	}

}
