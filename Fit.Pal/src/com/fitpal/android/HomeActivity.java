package com.fitpal.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.FragmentManager;
import com.fitpal.android.utils.Utils;

public class HomeActivity extends SherlockFragmentActivity {

	private Activity mActivity;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        
        if(AppInfo.loginActivity != null){
        	AppInfo.loginActivity.finish();
        }
        setContentView(R.layout.activity_launcher);
        String defaultFragment = "com.fitpal.android.news_feed.ui.NewsFeedFragment";
        FragmentManager.getInstance().launchFragment((FragmentActivity)mActivity, R.id.fragmentHolder, defaultFragment);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String searchItem = item.getTitle().toString();
		
		String fragmentClass = null;
		switch(item.getItemId()){
			case R.id.menu_news_feed:
				fragmentClass = "com.fitpal.android.news_feed.ui.NewsFeedFragment";
			break;

			case R.id.menu_workout_planner:
				fragmentClass = "com.fitpal.android.planner.ui.PlannerCalendarFragment";
			break;

			case R.id.menu_contacts:
				fragmentClass = "com.fitpal.android.routine.ui.MyRoutinesFragment";
			break;

			case R.id.menu_settings:
				fragmentClass = "com.fitpal.android.settings.ui.SettingsFragment";
			break;
		}

		if(!Utils.isNullOrEmptyStr(fragmentClass))
			FragmentManager.getInstance().launchFragment((FragmentActivity)mActivity, R.id.fragmentHolder, fragmentClass);
		return true;
	}

	
	public void onPause(){
		FragmentManager.getInstance().cleanupData();
		super.onPause();
	}
	
	public void onStop(){
		FragmentManager.getInstance().cleanupData();
		super.onPause();
	}
}
