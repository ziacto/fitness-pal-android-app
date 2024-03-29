package com.fitpal.android.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceStore {
	
	private static final String SALVAGERS_STORE_NAME = "SALVAGERS_STORE_NAME" ;
	
	public static void storeValue(String key, String value, Context context){
		SharedPreferences prefs = initSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getValueFromStore(String key, Context context){
		SharedPreferences prefs = initSharedPreferences(context);
		return prefs.getString(key, null);
	}
	
	private static SharedPreferences initSharedPreferences(Context context){
		return context.getSharedPreferences(SALVAGERS_STORE_NAME, Context.MODE_PRIVATE);
	}

}
