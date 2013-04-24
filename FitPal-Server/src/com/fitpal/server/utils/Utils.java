package com.fitpal.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Properties;

import javax.servlet.ServletInputStream;


public class Utils {
	
	private static String propertiesFilePath = "/com/karson/moojicserver/resources/";
	
	public static final boolean isInteger(String val){
		try{
			Integer.parseInt(val);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static final Properties getProperties(Object object, String filename) throws IOException
	{
		System.out.println("Utils.getProperties() :: readfile " + propertiesFilePath + filename);
		InputStream inputStream  = object.getClass().getClassLoader().getResourceAsStream(propertiesFilePath + filename);
		System.out.println("Utils.getProperties() :: Input steam is null ? " + (inputStream == null));
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}
	
	public static final boolean isNullOrEmptyStr(String str){
		return (str==null || "".equals(str.trim()));
	}
	
	public static final boolean isNullOrEmptyCollection(Collection coll){
		return (coll == null || (coll.size() == 0));
	}
	
	public static String convertStreamToString(ServletInputStream inputStream){
		if(inputStream == null)
			return "";

		String strVal = "" ; 
		try{
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(inputStream));
			String str = null;
			while((str = buffRead.readLine()) != null){
				strVal += str + "\n" ;
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
			return null;
		}
		
		return strVal;
	}
	
}
