package com.fitpal.android.profile.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.fitpal.android.R;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.profile.dataFetcher.ProfileDataFetcher;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.utils.AndroidUtils;

public class ProfileActivity extends SherlockFragmentActivity {

	private ProfilePictureView profilePictureView;
	private Profile mProfile;
	private Activity mActivity;
	private TextView userNameView;
	private EditText dob;
	private EditText email;
	private EditText height;
	private EditText weight;
	private String mUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		setContentView(R.layout.profile_page);

		Session session = Session.getActiveSession();

		if(session.isClosed()){
			return;
		}

		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
		userNameView = (TextView) findViewById(R.id.tv_username);
		dob = (EditText) findViewById(R.id.et_dob);
		email = (EditText) findViewById(R.id.et_email);
		height = (EditText) findViewById(R.id.et_height);
		weight = (EditText) findViewById(R.id.et_weight);

		new GetUserProfile().execute(null, null, null);
		makeUserRequest(session);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.menu_profile, menu);
		menu.getItem(0).setOnMenuItemClickListener(new SaveListener());
		return super.onCreateOptionsMenu(menu);
	}
	
	 private class SaveListener implements MenuItem.OnMenuItemClickListener{

			public boolean onMenuItemClick(MenuItem item) {
				new Thread(){
					public void run(){
						mProfile.dob = dob.getText().toString();
						mProfile.email = email.getText().toString();
						mProfile.weight = weight.getText().toString();
						mProfile.height = height.getText().toString();
						mProfile.height = height.getText().toString();
						mProfile.userName = mUserName;
						
						ProfileDataFetcher.updateProfile(mProfile);
						mActivity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								AndroidUtils.showToastNotification("Profile Saved", mActivity);
								finish();
							}
						});
					}
				}.start();
				return false;
			}
	    }
	 
	private void makeUserRequest(final Session session) {
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		 
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {
				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						profilePictureView.setProfileId(user.getId());
						userNameView.setText(user.getName());
					}
				}
				if (response.getError() != null) {
					// Handle errors, will do so later.
				}
			}
		});
		
		request.executeAsync();
	}
	
	private class GetUserProfile extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			mUserName = SharedPreferenceStore.getValueFromStore(Constants.KEY_USERNAME, mActivity);
			System.out.println("User name from SharedPrefs : " + mUserName);
			mProfile = ProfileDataFetcher.fetchProfileInfo(mUserName);
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			if(mProfile == null)
				return;
			
			email.setText(mProfile.email);
			height.setText(mProfile.height);
			weight.setText(mProfile.weight);
			dob.setText(mProfile.dob);
		}
	}
}
