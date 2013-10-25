package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpResponse;
import com.proyecto.bav.results.CambiarPassResult;

public class PostCambiarPassRequest extends PostSpiceRequest<CambiarPassResult>{

	public PostCambiarPassRequest(int userID, String content) {
		super(CambiarPassResult.class);
		this.setPath("/usuarios/" + userID + "/password");
		
		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", content);
		this.setHttpContent(requestContent);		
	}

	protected CambiarPassResult parseResponse(final HttpResponse response) throws IOException {
		
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

//		String json =  sb.toString();
//		Gson gson = new Gson();		
		
		CambiarPassResult cambiarPassResult = new CambiarPassResult();
		
		return cambiarPassResult;
		
	}

}
