package com.fitpal.android.routine.ui;

import android.app.Activity;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fitpal.android.R;

public class AddRoutineActivity  extends SherlockFragmentActivity {
	
	private Activity mActivity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_routine_page);
		mActivity = this;
		
	}

}
