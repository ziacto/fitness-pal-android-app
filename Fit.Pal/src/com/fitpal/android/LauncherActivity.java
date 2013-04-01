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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_launcher);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("News_Feed")
			.setIcon(R.drawable.ic_news_feed)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Workout Plan")
			.setIcon(R.drawable.ic_menu_planner)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		menu.add("Favorites")
			.setIcon(R.drawable.ic_menu_favorites)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Contacts")
			.setIcon(R.drawable.ic_menu_contacts)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		
		menu.add("Settings")
			.setIcon(R.drawable.ic_menu_settings)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String searchItem = item.getTitle().toString();
		
		String fragmentClass = null;
		
		if("News_Feed".equals(searchItem)){
			fragmentClass = "com.fitpal.android.news_feed.ui.NewsFeedFragment";
		}else if("Status".equals(searchItem)){
			fragmentClass = "com.fitpal.android.profile.ui.ProfileFragment";
		}else if("Favorites".equals(searchItem)){
			fragmentClass = "com.fitpal.android.profile.ui.ProfileFragment";
		}else if("Settings".equals(searchItem)){
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
