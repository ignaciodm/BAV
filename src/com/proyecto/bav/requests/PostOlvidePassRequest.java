package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpResponse;
import com.proyecto.bav.results.OlvidePassResult;

public class PostOlvidePassRequest extends PostSpiceRequest<OlvidePassResult> {

	public PostOlvidePassRequest(String content) {		
		super(OlvidePassResult.class);
		this.setPath("/olvidePassword.json");
		
		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", content);
		this.setHttpContent(requestContent);		
	}
	
	protected OlvidePassResult parseResponse(final HttpResponse response) throws IOException {
		
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
		
		OlvidePassResult olvidePassResult = new OlvidePassResult();
		
		return olvidePassResult;
	}

}
