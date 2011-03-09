/*
 * Copyright (c) 2010 Novell Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.suse.studio.api.lib;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class HttpClient {

	private String userName = null;
	private String apiKey   = null;

	// FIXME move this to a properties file
	private String apiHost    = "susestudio.com";
	private String apiVersion = "1";

	private DefaultHttpClient httpClient = null;
	private long startTime = 0;
	
	/*
	 * Constructors.
	 */
	public HttpClient(String userName, String apiKey) {
		this.userName   = userName;
		this.apiKey     = apiKey;
		this.httpClient = getHttpClient(userName, apiKey);
	}

	public HttpClient(String userName, String apiKey, String apiHost) {
		this(userName, apiKey);
		this.apiHost = apiHost;
	}

	public HttpClient(String userName, String apiKey, String apiHost, String apiVersion) {
		this(userName, apiKey);
		this.apiHost    = apiHost;
		this.apiVersion = apiVersion;
	}

	/*
	 * HTTP GET.
	 */
	public InputStream get(String path) {
		InputStream content = null;
		try {
			startTimer();
			HttpResponse response = httpClient.execute(new HttpGet(baseUrl() + path));
			stopTimer();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				content = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	public String toString() {
		return "userName:\t" + userName + "\n" +
		       "apiKey:\t\t"   + apiKey   + "\n" +
		       "baseUrl:\t"  + baseUrl();
	}

	/*
	 * Private methods.
	 */

	private String baseUrl() {
		return "http://" + apiHost + "/api/v" + apiVersion + "/";
	}

	private DefaultHttpClient getHttpClient(final String userName, final String apiKey) {
		HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {
		    public void process(final HttpRequest request, final HttpContext context)
		    					throws HttpException, IOException {
		        AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
		        if (authState.getAuthScheme() == null) {
	                authState.setAuthScheme(new BasicScheme());
	                authState.setCredentials(new UsernamePasswordCredentials(userName, apiKey));
		        }
		    }
		};		
		DefaultHttpClient client = new DefaultHttpClient();
		client.addRequestInterceptor(preemptiveAuth, 0);
		return client;
	}
	
	private void startTimer() {
		startTime = System.currentTimeMillis();
	}
	
	private void stopTimer() {
		long elapsed = System.currentTimeMillis() - startTime;
		System.out.printf("Took %d ms\n", elapsed);
	}
}