package com.fitpal.android.routine.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fitpal.android.R;
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
		mSpinnerExercises = (Spinner) findViewById(R.id.spinnerExercises);
		mSpinnerExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,long id) {
				String exercises = mSpinnerExercises.getSelectedItem().toString();
				System.out.println("Exercises : " + exercises);
				String[] exercisesList = Constants.exercisesMap.get(exercises);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, exercisesList);
				mListView.setAdapter(adapter);
			}
		});


	}

}
