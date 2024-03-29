package com.fitpal.android.common;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressDialogManager {

	private static ProgressDialog pd;
	
	public static void showProgressDialog(final Activity activity, final String message){
		if(activity == null)
			return;
		
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				pd = ProgressDialog.show(activity, "", message, false, true);
				
			}
		});
		
	}
	
	public static void hideProgressDialog(final Activity activity){
		if(activity == null)
			return;
		
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if(pd != null){
					pd.dismiss();
				}
			}
		});
		
	}
	
}
