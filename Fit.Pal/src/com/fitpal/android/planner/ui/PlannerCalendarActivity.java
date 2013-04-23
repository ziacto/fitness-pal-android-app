package com.fitpal.android.planner.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.caldroid.CaldroidFragment;
import com.caldroid.CaldroidListener;
import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.charts.PieChart;
import com.fitpal.android.planner.dataFetcher.PlannerDataFetcher;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.routine.dataFetcher.RoutineDataFetcher;
import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.utils.AndroidUtils;
import com.fitpal.android.utils.Utils;

public class PlannerCalendarActivity extends SherlockFragmentActivity{

	private Activity mActivity;
	private CaldroidFragment mCaldroidFragment;
	private Profile mProfile;
	private View previousCalendarView;


	@Override
	public void onCreate(Bundle saved){
		getSupportActionBar().setBackgroundDrawable(Utils.getActionBarBackground(this));
		super.onCreate(saved);
		setContentView(R.layout.planner_friend_page);
		mActivity = this;
		mProfile = AppInfo.friendProfile;
		setTitle(mProfile.displayName + "(Planner)");
		mCaldroidFragment = new CaldroidFragment();
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt("month", cal.get(Calendar.MONTH) + 1);
		args.putInt("year", cal.get(Calendar.YEAR));
		mCaldroidFragment.setArguments(args);

		CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				if(previousCalendarView != null){
					previousCalendarView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
				}
				view.setSelected(true);
				view.setBackgroundColor(mActivity.getResources().getColor(R.color.light_gray));
				previousCalendarView = view;
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
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.menu_friend_planner, menu);
		menu.getItem(0).setOnMenuItemClickListener(new ShowChartListener());
		return super.onCreateOptionsMenu(menu);
	}
	
    private class ShowChartListener implements MenuItem.OnMenuItemClickListener{

		public boolean onMenuItemClick(MenuItem item) {
			new GetSummaryReport().execute(null, null, null);
			return false;
		}
    }

    
	private class GetDaySummary extends AsyncTask<String, Void, Void>{

		private List<Task> mTaskList;
		private String mDate;

		@Override
		protected Void doInBackground(String... date) {
			mDate = date[0];
			mTaskList = PlannerDataFetcher.fetchTaskList(mProfile.userName, mDate);
			System.out.println("Friend Task List : " +  (mTaskList == null? " null" : mTaskList.size()));
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			if(Utils.isNullOrEmptyCollection(mTaskList)){
				AndroidUtils.showToastNotification("No Tasks Available", mActivity);
				return;
			}
			Intent intent = new Intent(mActivity, DailyTaskActivity.class);
			intent.putExtra(Constants.KEY_DATE, mDate);
			intent.putExtra(Constants.KEY_USERNAME, mProfile.userName);
			mActivity.startActivity(intent);
		}

	}
	
	private class GetSummaryReport extends AsyncTask<String, Void, Void>{

		private List<Routine> mRoutineList;

		@Override
		protected Void doInBackground(String... date) {
			mRoutineList = RoutineDataFetcher.fetchRoutineReport(mProfile.userName);
			
			if(Utils.isNullOrEmptyCollection(mRoutineList))
				return null;
			
			AppInfo.chartUser = mProfile.userName;
			AppInfo.chartKeys = new String[mRoutineList.size()];
			AppInfo.chartValues = new double[mRoutineList.size()];
			
			int count = 0;
			for(Routine routine : mRoutineList){
				AppInfo.chartValues[count] = routine.timeSpent/60;
				AppInfo.chartKeys[count] = routine.name + " (" + (routine.timeSpent/60) + " mins)";
				
				count++;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			if(Utils.isNullOrEmptyCollection(mRoutineList)){
				AndroidUtils.showToastNotification("No Routines Fund for user", mActivity);
				return;
			}
			Intent intent = new PieChart().execute(mActivity);
			mActivity.startActivity(intent);
		}
	}
}
