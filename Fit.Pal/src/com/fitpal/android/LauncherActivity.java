package com.fitpal.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.fitpal.android.common.FragmentManager;
import com.fitpal.android.utils.Utils;

public class LauncherActivity extends SherlockFragmentActivity {

	private Activity mActivity;
	private static final String MENU_NEWS_FEED = "News Feed";
	private static final String MENU_WORKOUT_PLAN = "Workout Plan";
	private static final String MENU_FAV = "Favorites";
	private static final String MENU_CONTACTS = "Contacts";
	private static final String MENU_SETTINGS = "Settings";
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_launcher);
        String defaultFragment = "com.fitpal.android.news_feed.ui.NewsFeedFragment";
        FragmentManager.getInstance().launchFragment((FragmentActivity)mActivity, R.id.fragmentHolder, defaultFragment);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(MENU_NEWS_FEED)
			.setIcon(R.drawable.ic_news_feed)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(MENU_WORKOUT_PLAN)
			.setIcon(R.drawable.ic_menu_planner)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		menu.add(MENU_FAV)
			.setIcon(R.drawable.ic_menu_favorites)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(MENU_CONTACTS)
			.setIcon(R.drawable.ic_menu_contacts)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		
		menu.add(MENU_SETTINGS)
			.setIcon(R.drawable.ic_menu_settings)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String searchItem = item.getTitle().toString();
		
		String fragmentClass = null;
		
		if(MENU_NEWS_FEED.equals(searchItem)){
			fragmentClass = "com.fitpal.android.news_feed.ui.NewsFeedFragment";
		}else if(MENU_WORKOUT_PLAN.equals(searchItem)){
			fragmentClass = "com.fitpal.android.planner.ui.PlannerCalendarFragment";
		}else if(MENU_FAV.equals(searchItem)){
			//fragmentClass = "com.fitpal.android.profile.ui.ProfileFragment";
		}else if(MENU_CONTACTS.equals(searchItem)){
			//fragmentClass = "com.fitpal.android.settings.ui.SettingsFragment";
		}else if(MENU_SETTINGS.equals(searchItem)){
			fragmentClass = "com.fitpal.android.settings.ui.SettingsFragment";
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
