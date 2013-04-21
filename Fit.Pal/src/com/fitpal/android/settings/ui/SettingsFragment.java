package com.fitpal.android.settings.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Session;
import com.fitpal.android.LoginActivity;
import com.fitpal.android.R;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.profile.ui.ProfileActivity;

public class SettingsFragment extends BaseFragment {

	private Activity mActivity;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings_page, container);
		initializeUI(view);
		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		
		View btnViewProfile = view.findViewById(R.id.btn_view_profile);
		btnViewProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, ProfileActivity.class);
				startActivity(intent);
			}
		});
		
		View btnLogout = view.findViewById(R.id.btn_logout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Session.getActiveSession().closeAndClearTokenInformation();
				Intent intent = new Intent(mActivity, LoginActivity.class);
				startActivity(intent);
				mActivity.finish();
			}
		});
		
	}
}
