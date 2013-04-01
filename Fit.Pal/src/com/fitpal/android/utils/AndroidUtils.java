package com.fitpal.android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidUtils {

	public static void showToastNotification(final String msg, final Activity activity){
		if(activity == null)
			return;
		
		activity.runOnUiThread(
				new Runnable(){
					public void run(){
						Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
					}
				});
		
	}
	
	public static void addFragment(Fragment fragment, FragmentActivity activity, int fragHolderLayout){
		ViewGroup view = (ViewGroup) activity.findViewById(fragHolderLayout);
		view.removeAllViews();
		FragmentTransaction fragTransaction = activity.getSupportFragmentManager().beginTransaction();
		fragTransaction.replace(fragHolderLayout, fragment);
		fragTransaction.commit();
	}
	
	public static void addFragmentWithHistory(Fragment fragment, FragmentActivity activity, int fragHolderLayout){
		FragmentTransaction fragTransaction = activity.getSupportFragmentManager().beginTransaction();
		fragTransaction.replace(fragHolderLayout, fragment);
		fragTransaction.addToBackStack(null);
		fragTransaction.commit();
	}
	
	public static void removeFragment(Fragment fragment, FragmentActivity activity){
		if(fragment != null){
			FragmentTransaction fragTransaction = activity.getSupportFragmentManager().beginTransaction();
			fragTransaction.remove(fragment);
			fragTransaction.commit();
		}
	}
	
	public static void setTextInView(int viewId, String val, Activity activity){
		TextView textView = (TextView)activity.findViewById(viewId);
		if(textView != null)
			textView.setText(val);
	}
	
	public static void startIntentWithMessage(Activity activity, Class intentClass, String msgKey, String msgVal){
		Intent intent = new Intent(activity, intentClass);
		intent.putExtra(msgKey, msgVal);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}
	
	public static void startIntentWithoutHistory(Activity activity, Class intentClass){
		Intent intent = new Intent(activity, intentClass);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}
	
	/*public static void launchMaps(final Activity activity, final String address){
		String uri = String.format(Constants.MAPS_INTENT_QUERY, address);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		activity.startActivity(intent);
	}
	*/

	public static Drawable loadImageFromURL(String url) {
		Drawable drawable = null;
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			drawable = Drawable.createFromStream(is, "srcname");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}
}
