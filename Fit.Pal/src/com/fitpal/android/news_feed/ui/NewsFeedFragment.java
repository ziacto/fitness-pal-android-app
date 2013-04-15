package com.fitpal.android.news_feed.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitpal.android.R;
import com.fitpal.android.common.BaseFragment;

public class NewsFeedFragment extends BaseFragment {

	private Activity mActivity;
	private Handler mHandler;
	
	public NewsFeedFragment(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.news_feed, container);
		initializeUI(view);

		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mHandler = new Handler();
	}
}
