package com.proyecto.bav.requests;

import java.io.IOException;

import roboguice.util.temp.Ln;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

public abstract class SimpleSpiceRequest<RequestResult> extends GoogleHttpClientSpiceRequest<RequestResult> {

	private String baseUrl = "http://cryptic-gorge-7789.herokuapp.com/";
	private JsonFactory sJsonFactory;
	private JsonObjectParser sJsonParser;

	public SimpleSpiceRequest(Class<RequestResult> clazz) {
		super(clazz);
	}
	
	 @Override
    public final RequestResult loadDataFromNetwork() throws Exception
    {
		final HttpRequest request = getHttpRequest();
        if (request == null) return null;
        // Do the network request
        Ln.d("Call web service " + baseUrl);
        final HttpResponse response = request.execute();
 
        if (response != null)
        {
            handleResponse(response);
            return parseResponse(response);
        }
        return null;
    }

	protected RequestResult parseResponse(final HttpResponse response)
			throws IOException {
		return response.parseAs(getResultType());
	}
	 
	protected String getBaseUrl() {
		return this.baseUrl;
	}
	 
 /**
     * Generate the request for this request to use in the loadDataFromNetwork method.
     *
     * @return a valid HttpRequest, if null this request will return a null object
     * @throws Exception
     * @see #getGETHttpRequest()
     * @see #getPOSTHttpRequest(com.google.api.client.http.HttpContent)
     */
    protected abstract HttpRequest getHttpRequest() throws Exception;
 
    /**
     * Optionally handle response data. By default you don't need to do anything, the Auth and Cookie are already pulled from the valid response headers.
     *
     * @param response the response if there is a valid one.
     */
    protected void onResponse(HttpResponse response)
    {
    }
 
    /**
     * Return the built Url for the requests
     *
     * @return End point url
     */
    protected abstract String getURL();
    
    /**
     * Get the singleton parser, we don't need to keep creating loads of these. getHttpRequest method will auto add this to the request.
     *
     * @return
     */
    protected JsonObjectParser getJsonParser()
    {
        if (sJsonParser == null)
            sJsonParser = getJsonFactory().createJsonObjectParser();
        return sJsonParser;
    }
 
    /**
     * Get the JSON factory. (For POST content normally). This is a singlton so this will not keep creating objects
     *
     * @return
     */
    protected JsonFactory getJsonFactory()
    {
        if (null == sJsonFactory)
            sJsonFactory = new GsonFactory();
        return sJsonFactory;
    }
    
    /**
     * Get the Request for the network method which you can then specific request and data etc.
     *
     * @return PrePopulated HttpRequest object
     * @throws IOException
     */
    protected HttpRequest getGETHttpRequest() throws IOException
    {
        final HttpRequest request = getHttpRequestFactory() //
                .buildGetRequest(new GenericUrl(getURL()));
        return populateHttpRequest(request);
    }
 
    /**
     * Get a POST request to make a network call with pre populated with headers etc, just make sure getURL() is set
     * and provide a content to the post request
     *
     * @param content a valid HttpContent Object, this can be anything as long as the server is expecting.
     * @return PrePopulated HttpRequest object
     * @throws IOException
     */
    protected HttpRequest getPOSTHttpRequest(HttpContent content) throws IOException
    {
        final HttpRequest request = getHttpRequestFactory() //
                .buildPostRequest(new GenericUrl(getURL()), content);
        return populatePOSTHttpRequest(request);
    }
 
    /**
     * Add common settings on the request
     *
     * @param request the request to populate
     * @return the request that was passed in
     */
    private HttpRequest populateHttpRequest(HttpRequest request)
    {
        //request.setEnableGZipContent(true);
        request.setParser(getJsonParser());
        request.setFollowRedirects(false);
 
        //Set up the headers
        HttpHeaders headers = request.getHeaders();
        if (null == headers)
            headers = new HttpHeaders();
 
        //We want to get JSON back
        headers.setAccept("application/json");
//	        //Get the user token if they have one
//	        final String token = UserSession.getInstance().getToken();
//	        if (token != null)
//	        {
//	            headers.setCookie(token);
//	        }
        request.setHeaders(headers);
 
        return request;
    }
 
    /**
     * This will call populateHttpRequest first so you do not need to call that method yourself.
     *
     * @param request
     * @return the request that was passed in
     */
    private HttpRequest populatePOSTHttpRequest(HttpRequest request)
    {
        populateHttpRequest(request);
 
        //Set up the headers
        HttpHeaders headers = request.getHeaders();
        if (null == headers)
            headers = new HttpHeaders();
 
        // Add the type of data we are sending to the server in the post request
        headers.setContentType("application/json");
        request.setHeaders(headers);
 
        return request;
    }
 
    /**
     * Pull the cookie and other needed items off the response before parsing the response object
     *
     * @param response valid response object
     */
    private void handleResponse(HttpResponse response)
    {
//	        if (response == null) return;
//	        onResponse(response);
//	        final HttpHeaders headers = response.getHeaders();
//	        if (headers == null) return;
//	        final String cookie = headers.getCookie();
//	        if (!TextUtils.isEmpty(cookie))
//	        {
//	            QLog.d("Response Cookie Nom Nom [" + cookie + "]");
//	            UserSession.getInstance().setUserToken(cookie, 0);
//	        }
    }
	 
}
