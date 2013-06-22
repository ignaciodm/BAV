package com.proyecto.bav.requests;

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
import com.proyecto.bav.results.AddressResult;

public class PostAddressSpiceRequest extends
		SimpleSpiceRequest<AddressResult> {

	private String path;
	private Address address;

	public PostAddressSpiceRequest(Address address) {
		super(AddressResult.class);
		this.path = "/addresses.json";
		this.address = address;
	}

	@Override
	protected HttpRequest getHttpRequest() throws Exception {
		ByteArrayContent requestContent = ByteArrayContent.fromString("application/json", getRequestContent());
		return this.getPOSTHttpRequest(requestContent);
	}

	protected String getRequestContent() {
		Gson gson = new Gson();
		return gson.toJson(address);
	}

	@Override
	protected String getURL() {
		return this.getBaseUrl() + this.path;
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
