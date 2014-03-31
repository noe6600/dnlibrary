package com.darknoe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class UrlRequest {

	public static String GET(String url) throws URISyntaxException, ClientProtocolException, IOException{
		String responseContent = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		request.setURI(new URI(url)); //Throws URISyntaxException
		HttpResponse response = client.execute(request); //Throws ClientProtocolException
		InputStream ips = response.getEntity().getContent();
		BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
		
		StringBuilder sb = new StringBuilder();
		String s;
		
		while(true){
			s = buf.readLine();
			if(s == null || s.length() == 0) break;
			sb.append(s);
		}
		
		buf.close();
		ips.close();
		responseContent = sb.toString();
		
		return responseContent;
	}
	
	public static String POST(String url, List<NameValuePair> properties) throws ClientProtocolException, IOException {
		String responseContent = null;
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);

        // Add your data
	    /*
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); //Throws UnsupportedEncodingException
        */
	    httppost.setEntity(new UrlEncodedFormEntity(properties)); //Throws UnsupportedEncodingException

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost); //Throws ClientProtocolException
        InputStream ips = response.getEntity().getContent();
		BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
		
		StringBuilder sb = new StringBuilder();
		String s;
		
		while(true){
			s = buf.readLine();
			if(s == null || s.length() == 0) break;
			sb.append(s);
		}
		
		buf.close();
		ips.close();
		responseContent = sb.toString();
	    
	    return responseContent;
	} 
}
