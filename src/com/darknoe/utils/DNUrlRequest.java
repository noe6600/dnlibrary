package com.darknoe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class DNUrlRequest {

	/** 
	 * Returns string GET response. Headers can be null.
	 * @author Darknoe
	 * @return responseContent
	 */
	public static String GET(String url, HashMap<String, String> headers) throws URISyntaxException, ClientProtocolException, IOException{
		String responseContent = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		request.setURI(new URI(url)); //Throws URISyntaxException
		
		//Set headers if we have
		if(headers!=null){
			Iterator it = headers.entrySet().iterator();
		    while (it.hasNext()) {
		    	HashMap.Entry pairs = (HashMap.Entry)it.next();
		        it.remove(); // avoids a ConcurrentModificationException
		        request.addHeader(pairs.getKey().toString(), pairs.getValue().toString());
		    }
		}
		
		Log.e("DNL: UrlRequest GET", "request");
		HttpResponse response = client.execute(request); //Throws ClientProtocolException
		InputStream ips = response.getEntity().getContent();
		Log.e("DNL: UrlRequest GET", "response");
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
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);

	    httppost.setEntity(new UrlEncodedFormEntity(properties)); //Throws UnsupportedEncodingException

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
	
	/** 
	 * Function that returns TRUE if the device is online
	 * @author Darknoe
	 * @param context
	 * @return boolean
	 */
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.e("Conection checker", "connectivity = null");
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
