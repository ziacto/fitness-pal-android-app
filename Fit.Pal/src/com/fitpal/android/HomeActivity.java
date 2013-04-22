package com.fitpal.android;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.Drawable;
import android.location.Location;
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
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.FragmentManager;
import com.fitpal.android.common.LocationTracker;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.profile.dataFetcher.ProfileDataFetcher;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.profile.ui.MyFriendsFragment;
import com.fitpal.android.utils.Utils;

public class HomeActivity extends SherlockFragmentActivity {

	private Activity mActivity;
	private LocationTracker mLocationTracker;
	private Timer mtimer;
	private Profile profile;

	private class UpdateLocationTimer extends TimerTask{

		@Override
		public void run() {
			System.out.println("Updating location");
			String userName = SharedPreferenceStore.getValueFromStore(Constants.KEY_USERNAME, mActivity);
			profile = ProfileDataFetcher.fetchProfileInfo(userName);

			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Location location = mLocationTracker.getLocation();
					if(location != null){
						profile.latitude = location.getLatitude();
						profile.longitude = location.getLongitude();

						new Thread(){
							public void run(){
								SharedPreferenceStore.storeValue(Constants.KEY_LATITUDE, Double.toString(profile.latitude), mActivity);
								SharedPreferenceStore.storeValue(Constants.KEY_LONGITUDE, Double.toString(profile.longitude), mActivity);
								ProfileDataFetcher.updateProfile(profile);
								System.out.println("user updated : lat : " + profile.latitude + "  long : " + profile.longitude);
							}
						}.start();
						
					}

				}
			});
			
		}
		
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mLocationTracker = new LocationTracker(mActivity);

        if(AppInfo.loginActivity != null){
        	AppInfo.loginActivity.finish();
        }
        setContentView(R.layout.activity_launcher);
        ActionBar actionBar = getSupportActionBar();
        
        Drawable blueBackground = getResources().getDrawable(R.drawable.abs_list_activated_holo);
        actionBar.setBackgroundDrawable(blueBackground);
        actionBar.setCustomView(R.layout.actionbar_top); //load your layout
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
        findViewById(R.id.action_add_friend).setOnClickListener(addBtnActionListsner);
        findViewById(R.id.action_add_routine).setOnClickListener(addBtnActionListsner);
		TextView title = (TextView)findViewById(R.id.action_bar_title) ;
		title.setText("Planner");

        hideTopActionItems();

        String defaultFragment = "com.fitpal.android.planner.ui.PlannerCalendarFragment";
        FragmentManager.getInstance().launchFragment((FragmentActivity)mActivity, R.id.fragmentHolder, defaultFragment);
        
        mtimer = new Timer();
        mtimer.schedule(new UpdateLocationTimer(), 0, Constants.LOCATION_UPDATE_TIME);
    }

    @Override
    public void onDestroy(){
    	mtimer.purge();
    	mtimer.cancel();
    	super.onDestroy();
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
