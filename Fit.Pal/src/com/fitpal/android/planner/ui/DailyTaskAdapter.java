package com.fitpal.android.planner.ui;

import java.util.List;

import com.fitpal.android.R;
import com.fitpal.android.common.ProgressDialogManager;
import com.fitpal.android.planner.entity.Task;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DailyTaskAdapter extends BaseAdapter{

	private List<Task> mTaskList = null;
	private Activity mActivity = null;
	private Handler mHandler;
	private LayoutInflater layoutInflator = null;
	private View mView;
	
	public DailyTaskAdapter(Activity activity, List<Task> taskList){
		mActivity = activity;
		mHandler = new Handler();
		mTaskList = taskList;
		layoutInflator = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return mTaskList.size();
	}

	
	@Override
	public Object getItem(int arg0) {

		return mTaskList.get(arg0);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Task task = mTaskList.get(position);
		
		mView = convertView;
		mView = layoutInflator.inflate(R.layout.daily_plan_row_layout, null);
		TextView taskName = (TextView)mView.findViewById(R.id.tv_task_name);
		taskName.setText(task.routine.name);
		//TextView duration = (TextView)mView.findViewById(R.id.tv_task_duration);
		//duration.setText(task.duration);
		TextView time = (TextView)mView.findViewById(R.id.tv_task_time);
		time.setText(task.startTime + " - " + task.endTime);
		//ProgressDialogManager.hideProgressDialog(mActivity);
		return mView;
	}
	
}

