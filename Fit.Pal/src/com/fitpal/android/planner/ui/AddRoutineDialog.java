package com.fitpal.android.planner.ui;

import java.util.List;

import com.fitpal.android.R;
import com.fitpal.android.planner.dataFetcher.PlannerDataFetcher;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

public class AddRoutineDialog extends Dialog {

	private DailyTaskActivity mActivity;
	private List<Routine> mRoutineList;
	private String mDate;
	private int mPosition;
	private String mStartTime;
	private String mEndTime;

	public AddRoutineDialog(DailyTaskActivity activity, List<Routine> routineList, String date) {
		super(activity, com.actionbarsherlock.R.style.Theme_Sherlock_Dialog);

		mActivity = activity;
		mRoutineList = routineList;
		mDate = date;
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_routine_to_calendar_dialog);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		initData();
	}

	private void initData(){
		Spinner mSpinnerExercises = (Spinner) findViewById(R.id.spinnerRoutineList);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(mActivity, android.R.layout.simple_spinner_item);
		for(Routine routine : mRoutineList){
			adapter.add(routine.name);
		}
		mSpinnerExercises.setAdapter(adapter);
		mSpinnerExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				mPosition = pos;
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		TimePicker startTime = (TimePicker)findViewById(R.id.startTime);
		startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				String time = (hourOfDay / 12) > 1 ? "PM" : "AM";
				mStartTime = Utils.pad(hourOfDay % 12) + ":" + Utils.pad(minute) + " " + time ;
				
			}
		});

		TimePicker endTime = (TimePicker)findViewById(R.id.endTime);
		endTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				String time = (hourOfDay / 12) > 1 ? "PM" : "AM";
				mEndTime = Utils.pad(hourOfDay % 12) + ":" + Utils.pad(minute) + " " + time ;
				
			}
		});

		View btnCancel = findViewById(R.id.btn_dialog_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddRoutineDialog.this.dismiss();
			}
		});
		
		View btnOk = findViewById(R.id.btn_dialog_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Routine routine = mRoutineList.get(mPosition);
				final Task task = new Task();
				task.routine = routine;
				task.routineId = routine.id;
				task.startTime = mStartTime;
				task.endTime = mEndTime;
				task.date = mDate;

				new Thread(){
					public void run(){
						PlannerDataFetcher.addRoutineToDay(task, mDate);
						mActivity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mActivity.refresh();
								AddRoutineDialog.this.dismiss();
							}
						});
					}
				}.start();
			}
		});
		
	}
}