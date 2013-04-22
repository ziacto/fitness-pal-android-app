package com.fitpal.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class Utils {

	public static double convertStringToDouble(String str){
	
		try{
			return Double.parseDouble(str);
		}catch(Exception e){
			return 0;
		}
	}
	
	public static final boolean isNullOrEmptyStr(String str){
		return (str==null || "".equals(str.trim()));
	}

	public static final boolean isNullOrEmptyCollection(Collection coll){
		return (coll == null || coll.size() == 0);
	}


	public static String convertStreamToString(InputStream inputStream){
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

	public static boolean isValidLong(String str){
		try{
			Long.parseLong(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static List<String> convertSetToList(Set<String> set){
		if(set == null)
			return null;

		List<String> list = new ArrayList<String>();

		for(String str : set){
			list.add(str);
		}

		return list;
	}

	public static String convertDateToString(Date date, String format){
		if(date == null)
			return null;

		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}catch(Exception e){
			return null;
		}
	}

	public  static String pad(int val) {
		return val >= 10 ? String.valueOf(val) : "0" + String.valueOf(val); 
	}
}
