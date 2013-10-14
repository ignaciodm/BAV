package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.UsuarioResult;

public class GetUsuarioRequest extends GetSpiceRequest<UsuarioResult>{

	public GetUsuarioRequest(int userID, String token) {
		super(UsuarioResult.class);
		this.setPath("/usuarios/" + userID + ".json?authToken=" + token);
	}
	
	protected UsuarioResult parseResponse(final HttpResponse response) throws IOException {
		
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
		User user = gson.fromJson(json, User.class);
		
		user.setAnioNacimiento(Integer.parseInt(user.getFechaDeNacimiento().substring(0,4)));
		user.setMesNacimiento(Integer.parseInt(user.getFechaDeNacimiento().substring(5,7)));
		user.setDiaNacimiento(Integer.parseInt(user.getFechaDeNacimiento().substring(8,10)));
		
		UsuarioResult usuarioResult = new UsuarioResult(user);
		
		return usuarioResult;
	}

}
