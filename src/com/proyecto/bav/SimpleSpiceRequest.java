package com.proyecto.bav;

import java.io.IOException;

import roboguice.util.temp.Ln;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.AddressResult;

public class SimpleSpiceRequest extends
		GoogleHttpClientSpiceRequest<AddressResult> {

	private String baseUrl;
	private String content;

	public SimpleSpiceRequest(Address address) {
		super(AddressResult.class);
		this.baseUrl = "http://4zyf.localtunnel.com/addresses.json";
		Gson gson = new Gson();
		this.content = gson.toJson(address);
	}

	@Override
	public AddressResult loadDataFromNetwork() throws IOException {
		Ln.d("Call web service " + baseUrl);
		HttpRequestFactory requestFactory = getHttpRequestFactory();
        ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", content);
		HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(baseUrl), requestContent);
		
        request.setParser(new JacksonFactory().createJsonObjectParser());
		request.getHeaders().setContentType("application/json");
		return request.execute().parseAs(getResultType());
	}

}
