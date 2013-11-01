package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.ValidarPassResult;

public class GetValidarPassRequest extends GetSpiceRequest<ValidarPassResult>{

	public GetValidarPassRequest(User user, String password) {
		super(ValidarPassResult.class);
		this.setPath("/usuarios/" + user.getId() + "/validarPassword.json?password=" + password + "&authToken=" + user.getAuthToken());
	}
	
	protected ValidarPassResult parseResponse(final HttpResponse response) throws IOException {
		
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
		
		ValidarPassResult validarPassResult = new ValidarPassResult();
		
		return validarPassResult;
	}

}
