package com.fitpal.android.common;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class FragmentManager {

	private static FragmentManager fragManager;
	private Map<String, Fragment> mFragMap;
	private String mLastFragmentId;

	private FragmentManager(){
		mFragMap = new HashMap<String, Fragment>();
	}

	public static FragmentManager getInstance(){
		if(fragManager == null){
			synchronized (FragmentManager .class){
				if (fragManager == null) {
					fragManager = new FragmentManager();
				}
			}
		}
		return fragManager;
	}
	
	public void cleanupData(){
		mFragMap.clear();
	}

	public Fragment getCurrentFragment(){
		if(mLastFragmentId != null)
			return mFragMap.get(mLastFragmentId) ;
		else
			return null;
	}

	public void launchFragment(FragmentActivity mActivity, int containerId, String newFragmentClass){
		if(newFragmentClass.equals(mLastFragmentId)){
			return;
		}

		ViewGroup view = (ViewGroup) mActivity.findViewById(containerId);
		view.removeAllViews();
		
		// detach current fragment
		FragmentTransaction fragTransaction = mActivity.getSupportFragmentManager().beginTransaction();
		if(mLastFragmentId != null){
			Fragment lastFragment = mFragMap.get(mLastFragmentId) ;
			if(lastFragment != null){
				fragTransaction.detach(lastFragment);
			}
		}

		Fragment fragment = mFragMap.get(newFragmentClass);
		if(fragment == null) {
			fragment = Fragment.instantiate(mActivity, newFragmentClass, null);
			fragTransaction.add(containerId, fragment, "Tag");
			mFragMap.put(newFragmentClass, fragment);
        } else {
        	fragTransaction.attach(fragment);
        }
		
		mLastFragmentId = newFragmentClass;
		fragTransaction.commit();
		mActivity.getSupportFragmentManager().executePendingTransactions();
	}
	
	public void addFragmentToMap(Fragment fragment, String fragmentClass){
		mFragMap.put(fragmentClass, fragment);
	}
}