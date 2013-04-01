package com.fitpal.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class Utils {
	
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
}
