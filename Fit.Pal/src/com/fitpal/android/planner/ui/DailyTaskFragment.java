package com.fitpal.android.planner.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fitpal.android.R;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.utils.Utils;

public class DailyTaskFragment extends BaseFragment {

	private Activity mActivity;
	private Handler mHandler;
	private Date mDate;
	private ListView mLvDailyTasks;
	private List<Task> mTaskList;
	private DailyTaskAdapter mTaskAdapter;
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	
	public DailyTaskFragment(Date date){
		mDate = date;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.daily_planner_page, container);
		initializeUI(view);

		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mHandler = new Handler();
		mLvDailyTasks = (ListView)view.findViewById(R.id.lv_daily_planner);
		TextView date = (TextView)view.findViewById(R.id.tv_date);
		date.setText(Utils.convertDateToString(mDate, DATE_FORMAT));

		new Thread(new GetDailyTaskListThread()).start();
	}
	
	private class GetDailyTaskListThread implements Runnable{
		public void run(){
			// get daily tasks from DB
			mTaskList = new ArrayList<Task>();
			Task task = new Task();
			task.name = "Running";
			task.startTime = "12:30pm";
			task.endTime = "2:30pm";
			task.duration = "2 hours";
			mTaskList.add(task);
			
			Task task2 = new Task();
			task2.name = "Push Ups";
			task2.startTime = "2:45pm";
			task2.endTime = "3:15pm";
			task2.duration = "30 minutes";
			mTaskList.add(task2);
			
			mHandler.post(new TaskCompletionHandler());
		}
	}
	
	private class TaskCompletionHandler implements Runnable{
		public void run(){
			mTaskAdapter = new DailyTaskAdapter(mActivity, mTaskList);
			mLvDailyTasks.setAdapter(mTaskAdapter);
		}
	}
}
