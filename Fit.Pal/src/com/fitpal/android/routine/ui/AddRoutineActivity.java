package com.fitpal.android.routine.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.routine.dataFetcher.RoutineDataFetcher;
import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.routine.entity.Workout;
import com.fitpal.android.utils.AndroidUtils;
import com.fitpal.android.utils.Utils;

public class AddRoutineActivity  extends SherlockFragmentActivity {
	
	private Activity mActivity;
	private ListView mListView;
	private WorkoutAdapter mWorkoutAdapter;
	private String mMode;
	private Routine mRoutine;
	private EditText etRoutineName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(Utils.getActionBarBackground(this));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_routine_page);
		mActivity = this;
		mListView = (ListView)findViewById(R.id.lv_workouts);
		mMode = getIntent().getStringExtra("MODE");
		etRoutineName = (EditText)mActivity.findViewById(R.id.et_routine_name);
		System.out.println("MOde : "  + mMode);
		if("ADD".equals(mMode)){
			mRoutine = new Routine();
			mRoutine.workoutList = new ArrayList<Workout>();
		}else{
			mRoutine = AppInfo.routine;
			AppInfo.routine = null;
			mRoutine.workoutList = mRoutine.workoutList;
			mWorkoutAdapter = new WorkoutAdapter(mActivity, mRoutine.workoutList);
			mListView.setAdapter(mWorkoutAdapter);
			etRoutineName.setText(mRoutine.name);
		}
		

		View btnAddWorkout = findViewById(R.id.btn_add_workout);
		btnAddWorkout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, AddWorkoutActivity.class);
				mActivity.startActivity(intent);
			}
		});

		if("VIEW".equals(mMode)){
			btnAddWorkout.setVisibility(View.INVISIBLE);
		}

	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		if(AppInfo.exerciseName == null)
			return;
		
		Workout workout  = new Workout();
		workout.workoutBody = AppInfo.workoutBody;
		workout.exercise = AppInfo.exerciseName;
		mRoutine.workoutList.add(workout);
		System.out.println(mRoutine.workoutList);
		AppInfo.workoutBody = null;
		AppInfo.exerciseName = null;
		
		if(mWorkoutAdapter == null){
			mWorkoutAdapter = new WorkoutAdapter(mActivity, mRoutine.workoutList);
			mListView.setAdapter(mWorkoutAdapter);
		}else{
			mWorkoutAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onDestroy(){
		mWorkoutAdapter = null;
		super.onStop();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.menu_add_workout, menu);
		menu.getItem(0).setOnMenuItemClickListener(new SaveListener());
		menu.getItem(1).setOnMenuItemClickListener(new DeleteListener());
		menu.getItem(2).setOnMenuItemClickListener(new ShareListener());

		if("ADD".equals(mMode)){
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
		}else if("EDIT".equals(mMode)){
			menu.getItem(1).setOnMenuItemClickListener(new DeleteListener());
		}else if("VIEW".equals(mMode)){
			menu.getItem(0).setVisible(false);
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
		}

		return super.onCreateOptionsMenu(menu);
	}
	
    private class SaveListener implements MenuItem.OnMenuItemClickListener{

		public boolean onMenuItemClick(MenuItem item) {
			mRoutine.name = etRoutineName.getText().toString();
			if(Utils.isNullOrEmptyStr(mRoutine.name)){
				AndroidUtils.showToastNotification("Enter Routine Name", mActivity);
			}else{
				new Thread(){
					public void run(){
						mRoutine.userName = SharedPreferenceStore.getValueFromStore(Constants.KEY_USERNAME, mActivity); 
						if("ADD".equals(mMode))
							RoutineDataFetcher.addRoutine(mRoutine);
						else
							RoutineDataFetcher.editRoutine(mRoutine);
						finish();
					}
				}.start();
				AndroidUtils.showToastNotification("Routine Added successfully", mActivity);
			}
			return false;
		}
    }

    private class DeleteListener implements MenuItem.OnMenuItemClickListener{	

		public boolean onMenuItemClick(MenuItem item) {

			new Thread(){
				public void run(){
					RoutineDataFetcher.deleteRoutine(mRoutine.id);
				}
			}.start();

			AndroidUtils.showToastNotification("Routine Deleted", mActivity);
			finish();
			return false;
		}
    }

    private class ShareListener implements MenuItem.OnMenuItemClickListener{

		public boolean onMenuItemClick(MenuItem item) {

			new Thread(){
				public void run(){
					RoutineDataFetcher.shareRoutine(mRoutine.id);
				}
			}.start();

			AndroidUtils.showToastNotification("Routine Shared", mActivity);
			return false;
		}
    }

}
