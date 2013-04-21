package com.fitpal.android.profile.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.profile.dataFetcher.ProfileDataFetcher;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.routine.dataFetcher.RoutineDataFetcher;
import com.fitpal.android.routine.entity.Routine;

public class MyFriendsFragment extends BaseFragment {

	private Activity mActivity;
	private List<Profile> mFriendsList;
	private ListView mListView;
	private FriendsAdapter mFriendsAdapter;
	
	public MyFriendsFragment(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.my_friends_page, container);
		initializeUI(view);

		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mListView = (ListView)view.findViewById(R.id.lv_friends);

	}
	
	@Override
	public void addClicked(){
		
	}

	@Override
	public void onResume(){
		new GetMyFriendsTask().execute(null, null, null);
		super.onResume();
	}
	
/* End Of Action Mode class */
	
	private class GetMyFriendsTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// get daily tasks from Server
			String userName = SharedPreferenceStore.getValueFromStore(Constants.KEY_USERNAME, mActivity);
			mFriendsList = ProfileDataFetcher.getAllFriends(userName);
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			mFriendsAdapter = new FriendsAdapter(mActivity, mFriendsList);
			mListView.setAdapter(mFriendsAdapter);
			
			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
					// mFriendsList.get(position);
					//Intent intent = new Intent(mActivity, AddRoutineActivity.class);
					//intent.putExtra("MODE", "EDIT");
					//mActivity.startActivity(intent);
				}

			});
		}

	}
}
