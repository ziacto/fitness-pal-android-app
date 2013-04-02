package com.fitpal.android.planner.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fitpal.android.R;
import com.fitpal.android.common.Constants;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.utils.AndroidUtils;

public class DailyTaskActivity extends SherlockFragmentActivity {

	private Activity mActivity;
	private Handler mHandler;
	private String mDate;
	private ListView mLvDailyTasks;
	private List<Task> mTaskList;
	private DailyTaskAdapter mTaskAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_planner_page);
		mActivity = this;
		mHandler = new Handler();
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			mDate = extras.getString(Constants.KEY_DATE);
		}
		mLvDailyTasks = (ListView)findViewById(R.id.lv_daily_planner);
		TextView date = (TextView)findViewById(R.id.tv_date);
		date.setText(mDate);

		new Thread(new GetDailyTaskListThread()).start();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.menu_daily_task, menu);

		menu.getItem(0).setOnMenuItemClickListener(new AddListener());
		return super.onCreateOptionsMenu(menu);
	}
	
    private class AddListener implements MenuItem.OnMenuItemClickListener{

		public boolean onMenuItemClick(MenuItem item) {
			AndroidUtils.showToastNotification("Add Button added", mActivity);
			return false;
		}
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
