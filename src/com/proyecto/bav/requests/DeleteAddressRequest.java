package com.proyecto.bav.requests;

import java.io.IOException;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpResponse;
import com.proyecto.bav.results.AddressResult;


public class DeleteAddressRequest extends DeleteSpiceRequest<AddressResult> {

	public DeleteAddressRequest(int userID, int direccionID, String token) {
		super(AddressResult.class);
		this.setPath("/usuarios/" + userID + "/direcciones/" + direccionID + ".json?authToken=" + token);
		
		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", "");
		this.setHttpContent(requestContent);		
	}
	
	protected AddressResult parseResponse(final HttpResponse response) throws IOException {
		
//		StringBuilder sb = new StringBuilder();
//		InputStream inputStream = null;
//		
//		try {
//			
//			inputStream = response.getContent();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//			String line;
//			
//			while ((line = bufferedReader.readLine()) != null) {
//				sb.append(line);
//			}
//			
//			inputStream.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Address address = null;
//		String json =  sb.toString();
//		Gson gson = new Gson();
//
//		Type addressType = new TypeToken<Address>() {}.getType();
//
//		try {
//			address = gson.fromJson(json, addressType);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
		
		AddressResult addressResult = new AddressResult(null);
		
		return addressResult;
	}

}
