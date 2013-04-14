package com.fitpal.android.routine.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.Constants;

public class AddWorkoutActivity  extends SherlockFragmentActivity {

	private Activity mActivity;
	private ListView mListView;
	private Spinner mSpinnerExercises;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_workout_page);
		mActivity = this;
		mListView = (ListView)findViewById(R.id.lv_workouts);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,long id) {
				AppInfo.exerciseName = parent.getItemAtPosition(pos).toString();
				finish();
			}

		});

		mSpinnerExercises = (Spinner) findViewById(R.id.spinnerExercises);
		mSpinnerExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				AppInfo.workoutBody = parent.getItemAtPosition(pos).toString();
				String[] exercisesList = Constants.exercisesMap.get(AppInfo.workoutBody);
				if(exercisesList != null){
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, exercisesList);
					mListView.setAdapter(adapter);
				}
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}

		});
	}
}
