package com.fitpal.android.routine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fitpal.android.R;

public class AddRoutineActivity  extends SherlockFragmentActivity {
	
	private Activity mActivity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_routine_page);
		mActivity = this;
		
		View btnAddWorkout = findViewById(R.id.btn_add_workout);
		btnAddWorkout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, AddWorkoutActivity.class);
				mActivity.startActivity(intent);
			}
		});
	}

}
