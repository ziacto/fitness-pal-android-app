package com.fitpal.android.profile.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.fitpal.android.R;
import com.fitpal.android.common.AnimUtils;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.profile.dataFetcher.ProfileDataFetcher;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.utils.AndroidUtils;

public class MyFriendsFragment extends BaseFragment {

	private Activity mActivity;
	private View mMainView;
	private List<Profile> mFriendsList;
	private ListView mListView;
	private FriendsAdapter mFriendsAdapter;
	private LinearLayout mAddFriendPanel;

	public MyFriendsFragment(){
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mMainView = inflater.inflate(R.layout.my_friends_page, container);
		initializeUI(mMainView);

		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mListView = (ListView)view.findViewById(R.id.lv_friends);
		View addFacebookFriends = view.findViewById(R.id.add_facebook_frnds);
		addFacebookFriends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fadeOutAddFriendsPanel();
				final FriendPickerFragment fragment = new FriendPickerFragment();
				setFriendPickerListeners(fragment);	
				showPickerFragment(fragment);
			}
		});
		
		View searchNearbyFriends = view.findViewById(R.id.search_nearby_people);
		searchNearbyFriends.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				fadeOutAddFriendsPanel();
				Intent intent = new Intent(mActivity, FindNearbyUsersActivity.class);
				mActivity.startActivity(intent);
			}
		});
	}

	@Override
	public void addClicked(){
		mAddFriendPanel = (LinearLayout)mActivity.findViewById(R.id.add_friend_layoout);
		AnimUtils.setLayoutAnim_slidedownfromtop(mAddFriendPanel, mActivity);
		AppInfo.addPanelShown = true;
	}

	public void fadeOutAddFriendsPanel(){
		if(mAddFriendPanel == null)
			return;
		
		Animation animation = AnimUtils.runFadeOutAnimationOn(mActivity, mAddFriendPanel);

		animation.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}
			public void onAnimationEnd(Animation animation) {
				mAddFriendPanel.setVisibility(View.GONE);
				mAddFriendPanel = null;
				AppInfo.addPanelShown = false;
			}
			public void onAnimationRepeat(Animation animation) {
			}
		});

	}

	@Override
	public void onResume(){
		new GetMyFriendsTask().execute(null, null, null);
		super.onResume();
	}

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
	
	private void showPickerFragment(PickerFragment<?> fragment) {
		fragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
			@Override
			public void onError(PickerFragment<?> pickerFragment, FacebookException error) {
				String text = "Exception : " + error.getMessage();
				Toast toast = Toast.makeText(mActivity, text, Toast.LENGTH_SHORT);
				toast.show();
			}
		});

		FragmentManager fm = ((FragmentActivity)mActivity).getSupportFragmentManager();
		fm.beginTransaction()
		.replace(R.id.fragment_container, fragment)
		.addToBackStack(null)
		.commit();

		//controlsContainer.setVisibility(View.GONE);

		// We want the fragment fully created so we can use it immediately.
		fm.executePendingTransactions();

		System.out.println("Fragment : " + fragment);
		fragment.loadData(false);
	}
	private void setFriendPickerListeners(final FriendPickerFragment fragment) {
		fragment.setOnDoneButtonClickedListener(new FriendPickerFragment.OnDoneButtonClickedListener() {
			@Override
			public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
				onFriendPickerDone(fragment);
			}
		});
	}

	private void onFriendPickerDone(FriendPickerFragment fragment) {
		FragmentManager fm = ((FragmentActivity)mActivity).getSupportFragmentManager();
		fm.popBackStack();

		String results = "";

		Collection<GraphUser> selection = fragment.getSelection();
		if (selection != null && selection.size() > 0) {
			ArrayList<String> names = new ArrayList<String>();
			for (GraphUser user : selection) {
				names.add(user.getName());
				System.out.println(user.getName() + "," + user.getId());
			}
			results = TextUtils.join(", ", names);
		} else {
			results = "No friends selected";
		}

		AndroidUtils.showAlert(mActivity, "You Picked :", results);
	}
}
