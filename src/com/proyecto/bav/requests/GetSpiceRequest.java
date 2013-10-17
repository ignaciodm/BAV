package com.proyecto.bav.requests;

import com.google.api.client.http.HttpRequest;

public class GetSpiceRequest<RequestResult> extends SimpleSpiceRequest<RequestResult> {

	private String path;

	public GetSpiceRequest(Class<RequestResult> clazz) {
		super(clazz);
	}

	@Override
	protected HttpRequest getHttpRequest() throws Exception {
		return this.getGETHttpRequest("GET");
	}

	@Override
	protected String getURL() {
		return this.getBaseUrl() + this.getPath();
	}
	
	protected String getPath() {
		return this.path;
	}
	
	protected void setPath(String path) {
		this.path = path;
	}

}
