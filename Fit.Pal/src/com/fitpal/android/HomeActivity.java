package com.fitpal.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.common.FragmentManager;
import com.fitpal.android.profile.ui.MyFriendsFragment;
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
        ActionBar actionBar = getSupportActionBar();
        
        actionBar.setCustomView(R.layout.actionbar_top); //load your layout
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
        findViewById(R.id.action_add_friend).setOnClickListener(addBtnActionListsner);
        findViewById(R.id.action_add_routine).setOnClickListener(addBtnActionListsner);
		TextView title = (TextView)findViewById(R.id.action_bar_title) ;
		title.setText("Planner");

        hideTopActionItems();

        String defaultFragment = "com.fitpal.android.planner.ui.PlannerCalendarFragment";
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
		hideTopActionItems();
		TextView title = (TextView)findViewById(R.id.action_bar_title) ;

		String fragmentClass = null;
		switch(item.getItemId()){
			case R.id.menu_calendar:
				fragmentClass = "com.fitpal.android.planner.ui.PlannerCalendarFragment";
				title.setText("Planner");
			break;

			case R.id.menu_workout_planner:
				findViewById(R.id.action_add_routine).setVisibility(View.VISIBLE);
				fragmentClass = "com.fitpal.android.routine.ui.MyRoutinesFragment";
				title.setText("My Routines");
			break;

			case R.id.menu_contacts:
				findViewById(R.id.action_add_friend).setVisibility(View.VISIBLE);
				fragmentClass = "com.fitpal.android.profile.ui.MyFriendsFragment";
				title.setText("Friends");
			break;

			case R.id.menu_settings:
				fragmentClass = "com.fitpal.android.settings.ui.SettingsFragment";
				title.setText("Settings");
			break;
		}

		if(!Utils.isNullOrEmptyStr(fragmentClass))
			FragmentManager.getInstance().launchFragment((FragmentActivity)mActivity, R.id.fragmentHolder, fragmentClass);
		return true;
	}

	private static View.OnClickListener addBtnActionListsner = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			Fragment currentFragment = FragmentManager.getInstance().getCurrentFragment();
			
			if(currentFragment != null && currentFragment instanceof BaseFragment){
				((BaseFragment)currentFragment).addClicked();
			}
		}
	};
	
	
	private void hideTopActionItems(){
		findViewById(R.id.action_add_friend).setVisibility(View.GONE);
		findViewById(R.id.action_add_routine).setVisibility(View.GONE);
	}
	
	public void onPause(){
		FragmentManager.getInstance().cleanupData();
		super.onPause();
	}
	
	public void onStop(){
		FragmentManager.getInstance().cleanupData();
		super.onPause();
	}

	@Override
	public void onBackPressed(){
		Fragment currentFragment = FragmentManager.getInstance().getCurrentFragment();
		if(currentFragment instanceof MyFriendsFragment && AppInfo.addPanelShown){
			MyFriendsFragment friendsFragment = (MyFriendsFragment) currentFragment;
			friendsFragment.fadeOutAddFriendsPanel();
		}else{
			super.onBackPressed();
		}
	}
}
