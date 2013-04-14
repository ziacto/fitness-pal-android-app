package com.fitpal.android.routine.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.routine.entity.Workout;

public class AddRoutineActivity  extends SherlockFragmentActivity {
	
	private Activity mActivity;
	private List<Workout> mWorkoutList;
	private ListView mListView;
	private WorkoutAdapter mWorkoutAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_routine_page);
		mActivity = this;
		
		mWorkoutList = new ArrayList<Workout>();
		mListView = (ListView)findViewById(R.id.lv_workouts);
		mWorkoutAdapter = new WorkoutAdapter(mActivity, mWorkoutList);
		mListView.setAdapter(mWorkoutAdapter);

		View btnAddWorkout = findViewById(R.id.btn_add_workout);
		btnAddWorkout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, AddWorkoutActivity.class);
				mActivity.startActivity(intent);
			}
		});
	}
	
	@Override
	public void onResume(){
		Workout workout  = new Workout();
		workout.workoutBody = AppInfo.workoutBody;
		workout.exercise = AppInfo.exerciseName;
		mWorkoutList.add(workout);
		mWorkoutAdapter.notifyDataSetChanged();
		super.onResume();
	}

}
