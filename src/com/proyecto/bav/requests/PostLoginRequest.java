package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.LoginResult;

public class PostLoginRequest extends PostSpiceRequest<LoginResult> {

	public PostLoginRequest(String content) {		
		super(LoginResult.class);
		this.setPath("/login");
		
		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", content);
		this.setHttpContent(requestContent);		
	}
	
	protected LoginResult parseResponse(final HttpResponse response) throws IOException {
		
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

		User user = null;
		String json =  sb.toString();
		Gson gson = new Gson();

		Type userType = new TypeToken<User>() {}.getType();

		try {
			user = gson.fromJson(json, userType);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		LoginResult loginResult = new LoginResult(user);
		
		return loginResult;
	}

}
