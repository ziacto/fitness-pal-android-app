package com.fitpal.android.planner.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.caldroid.CaldroidFragment;
import com.caldroid.CaldroidListener;
import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.planner.dataFetcher.PlannerDataFetcher;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.routine.dataFetcher.RoutineDataFetcher;
import com.fitpal.android.routine.ui.AddRoutineActivity;
import com.fitpal.android.routine.ui.RoutineAdapter;
import com.fitpal.android.utils.Utils;

public class PlannerCalendarFragment extends BaseFragment {

	private Activity mActivity;
	private CaldroidFragment mCaldroidFragment;
	
	public PlannerCalendarFragment(){
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.planner_page, container);
		initializeUI(view);
		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mCaldroidFragment = new CaldroidFragment();
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt("month", cal.get(Calendar.MONTH) + 1);
		args.putInt("year", cal.get(Calendar.YEAR));
		mCaldroidFragment.setArguments(args);

		CaldroidListener listener = new CaldroidListener() {

		    @Override
		    public void onSelectDate(Date date, View view) {
		    	view.setSelected(true);
		    	String formattedDate = Utils.convertDateToString(date, Constants.SIMPLE_DATE_FORMAT);
		    	new GetDaySummary().execute(formattedDate);
		    }

		    @Override
		    public void onChangeMonth(int month, int year) {
		        //String text = "month: " + month + " year: " + year;
		        //AndroidUtils.showToastNotification(text, mActivity);
		    }

		};
		mCaldroidFragment.setCaldroidListener(listener);

		FragmentTransaction t = ((FragmentActivity)mActivity).getSupportFragmentManager().beginTransaction();
		t.add(R.id.ll_planner, mCaldroidFragment);
		t.commit();
	}

	@Override
	public void addClicked(){
		
	}
	
	private class GetDaySummary extends AsyncTask<String, Void, Void>{

		private List<Task> mTaskList;
		private String mDate;
		
		@Override
		protected Void doInBackground(String... date) {
			mDate = date[0];
			String userName = SharedPreferenceStore.getValueFromStore(Constants.KEY_USERNAME, mActivity);
			mTaskList = PlannerDataFetcher.fetchTaskList(userName, mDate);
			System.out.println("Task List : " +  (mTaskList == null? " null" : mTaskList.size()));
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			TextView summaryDate = (TextView)mActivity.findViewById(R.id.tv_summary_date);
			TextView summary = (TextView)mActivity.findViewById(R.id.tv_summary);
			TextView btnAddTask = (TextView)mActivity.findViewById(R.id.btn_add_task);
			
			if(!Utils.isNullOrEmptyCollection(mTaskList)){
				btnAddTask.setVisibility(View.GONE);
				
				summaryDate.setText("Summary : " + mDate);
				summaryDate.setVisibility(View.VISIBLE);
				
				String text = "";
				for(Task task : mTaskList){
					text += task.routine.name + "\n";
				}
				
				summary.setText(text);
				summary.setVisibility(View.VISIBLE);
			}else{
				btnAddTask.setVisibility(View.VISIBLE);
				summaryDate.setVisibility(View.GONE);
				summary.setVisibility(View.GONE);
			}
			
			View btnSelector = mActivity.findViewById(R.id.btn_selector);
			btnSelector.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mActivity, DailyTaskActivity.class);
					intent.putExtra(Constants.KEY_DATE, mDate);
					mActivity.startActivity(intent);
				}
			});
		}

	}

}
