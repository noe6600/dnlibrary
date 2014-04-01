package com.darknoe.soap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class SoapObject {
	private List<SoapProperty> properties;
	private String method;
	private String action;
	private String webserviceUrl;

	public SoapObject(){
		this.properties = new ArrayList<SoapProperty>();
	}
	
	public void addProperty(SoapProperty property){
		this.properties.add(property);
	}
	
	public void addProperty(String name, String value){
		this.properties.add(new SoapProperty(name, value));
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getWebserviceUrl() {
		return webserviceUrl;
	}

	public void setWebserviceUrl(String webserviceUrl) {
		this.webserviceUrl = webserviceUrl;
	}

	public String call() throws ClientProtocolException, IOException{
		String xml = null;
		String soapCall;
		
		soapCall = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsdata=\"http://acigrup.com/\"><soapenv:Header><wsdata:AutHeader><wsdata:Username>user</wsdata:Username><wsdata:Password></wsdata:Password>pass</wsdata:AutHeader></soapenv:Header><soapenv:Body>";
		soapCall += "<wsdata:"+this.method+">";
		
		for(int i=0; i<this.properties.size(); i++){
			soapCall += "<wsdata:"+this.properties.get(i).getAttributeName()+">"+this.properties.get(i).getAttributeValue()+"</wsdata:"+this.properties.get(i).getAttributeName()+">";
		}
		
		soapCall += "</wsdata:"+this.method+"></soapenv:Body></soapenv:Envelope>";
		
		//Log.e("SoapObject", soapCall);
		
		HttpPost httppost = new HttpPost(this.webserviceUrl);          
		StringEntity se = new StringEntity(soapCall,HTTP.UTF_8);

		se.setContentType("text/xml");  
		httppost.setHeader("Content-Type","text/xml; charset=utf-8");
		httppost.setEntity(se);  

		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse httpResponse = httpclient.execute(httppost);

		InputStream ips = httpResponse.getEntity().getContent();
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
		xml = sb.toString();
		
		return xml;
	}
}
