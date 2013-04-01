package com.fitpal.android.profile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitpal.android.R;
import com.fitpal.android.common.BaseFragment;

public class ProfileFragment extends BaseFragment {

	private Activity mActivity;
	private Handler mHandler;
	
	public ProfileFragment(){
		System.out.println("inside search constructor");
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.profile_page, container);
		initializeUI(view);

		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mHandler = new Handler();
	}
}
