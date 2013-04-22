package com.fitpal.android.profile.ui;

import java.util.ArrayList;
import java.util.Collection;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.facebook.FacebookException;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.fitpal.android.R;
import com.fitpal.android.utils.AndroidUtils;
import com.fitpal.android.utils.Utils;

public class AddFacebookFriendsActivity extends SherlockFragmentActivity {

	private FragmentActivity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState){
		getSupportActionBar().setBackgroundDrawable(Utils.getActionBarBackground(this));
		super.onCreate(savedInstanceState);
		mActivity = this;
		setContentView(R.layout.add_fb_friends_page);
		
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

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
		.replace(R.id.fragment_container, fragment)
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
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStack();

		String results = "";

		Collection<GraphUser> selection = fragment.getSelection();
		if (selection != null && selection.size() > 0) {
			ArrayList<String> names = new ArrayList<String>();
			for (GraphUser user : selection) {
				names.add(user.getName());
			}
			results = TextUtils.join(", ", names);
		} else {
			results = "No friends selected";
		}

		AndroidUtils.showAlert(mActivity, "You Picked :", results);
	}


}
