package com.proyecto.bav.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.results.AddressesResult;

public class GetAddressesRequest extends GetSpiceRequest<AddressesResult>{

	public GetAddressesRequest(int userID, String token) {
		super(AddressesResult.class);
		this.setPath("/usuarios/" + userID + ".json?authToken=" + token);
	}
	
	protected AddressesResult parseResponse(final HttpResponse response) throws IOException {
		
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
		
		AddressesResult addressesResult = new AddressesResult(null);

		// GET DIRECCIONES
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			return addressesResult;
		}
		
		String direcciones = "";
		try {
			direcciones = jObj.getString("direcciones");
		} catch (JSONException e) {
			return addressesResult;
		}		
		
		List<Address> addresses = null;
		Type addressesType = new TypeToken<List<Address>>() {}.getType();
		
		try {
			addresses = gson.fromJson(direcciones, addressesType);
		} catch (Exception e) {
			return addressesResult;
		}
		
		addressesResult.setAddresses(addresses);
		
		return addressesResult;
	}

}
