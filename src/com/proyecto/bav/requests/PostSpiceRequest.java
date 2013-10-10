package com.proyecto.bav.requests;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;

public class PostSpiceRequest<RequestResult> extends SimpleSpiceRequest<RequestResult> {

	private HttpContent httpContent;
	private String path;

	public PostSpiceRequest(Class<RequestResult> clazz) {
		super(clazz);
	}

	@Override
	protected HttpRequest getHttpRequest() throws Exception {
		return this.getPOSTHttpRequest(httpContent);
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

	public HttpContent getHttpContent() {
		return httpContent;
	}

	public void setHttpContent(HttpContent httpContent) {
		this.httpContent = httpContent;
	}

}
