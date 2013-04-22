package com.fitpal.android.routine.ui;

import java.util.List;

import com.fitpal.android.R;
import com.fitpal.android.routine.entity.Workout;
import com.fitpal.android.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkoutAdapter extends BaseAdapter{

	private List<Workout> mWorkoutList = null;
	private Activity mActivity = null;
	private LayoutInflater layoutInflator = null;
	private View mView;
	
	public WorkoutAdapter(Activity activity, List<Workout> workoutList){
		mActivity = activity;
		mWorkoutList = workoutList;
		layoutInflator = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return mWorkoutList.size();
	}

	
	@Override
	public Object getItem(int arg0) {

		return mWorkoutList.get(arg0);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Workout workout = mWorkoutList.get(position);

		mView = convertView;
		mView = layoutInflator.inflate(R.layout.workout_row_layout, null);
		TextView workoutName = (TextView)mView.findViewById(R.id.tv_workout_name);

		if(workout != null && !Utils.isNullOrEmptyStr(workout.exercise))
			workoutName.setText(workout.exercise);
		
		TextView bodyPartName = (TextView)mView.findViewById(R.id.tv_body_name);
		if(workout != null && !Utils.isNullOrEmptyStr(workout.workoutBody))
			bodyPartName.setText(workout.workoutBody);
		
		return mView;
	}
	
}

