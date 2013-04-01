package com.fitpal.android.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class DataCommunicator {

	public static final String SERVER_BASE_ADDRESS = "http://10.137.16.35:9090/WarOfTheWorlds/" ;
	//public static final String SERVER_BASE_ADDRESS = "http://192.168.56.1:8080/WarOfTheWorlds/" ;

	private static final int TIMEOUT = 120000;
	
	public static InputStream sendPostDataToServer(String serverAddress, String postPayload) throws Exception{
		InputStream inputStream = null;
		try{
			URL url = new URL(serverAddress);

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(TIMEOUT);

			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(postPayload.toString().getBytes("UTF-8"));
			outputStream.close();

			// Response....
			int responseCode = urlConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				inputStream = urlConnection.getInputStream();
			} else {
				inputStream = urlConnection.getErrorStream();
			}
		}catch(Exception e){
			Log.e("DataCommunicator:sendPostDataToServer","Exception in posting data : " + e);
		}

		return inputStream;

	}

	public static InputStream sendGetDataToServer(String serverAddress) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(serverAddress);
		HttpParams httpParams = httpclient.getParams();
		
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		
		try{
			 HttpResponse response = httpclient.execute(httpGet);
			 HttpEntity entity = response.getEntity();
			 
		        if(entity == null)
		        	return null;
		        else
		        	return entity.getContent();
		} catch (ClientProtocolException e) {
	    	Log.e("DataCommunicator:sendGetDataToServer", "ClientProtocolException in sendGetDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    } catch (IOException e) {
	    	Log.e("DataCommunicator:sendGetDataToServer", "IOException in sendGetDataToServer" + e);
	    	e.printStackTrace();
	    	throw e;
	    }catch(Exception e){
	    	Log.e("DataCommunicator:sendGetDataToServer", "Exception in sendPostDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    }

	}


}
