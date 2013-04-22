package com.fitpal.android.profile.ui;

import java.util.List;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.fitpal.android.R;
import com.fitpal.android.profile.entity.Profile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendsAdapter extends BaseAdapter{

	private List<Profile> mProfileList = null;
	private Activity mActivity = null;
	private LayoutInflater layoutInflator = null;
	private View mView;
	
	public FriendsAdapter(Activity activity, List<Profile> profileList){
		mActivity = activity;
		mProfileList = profileList;
		layoutInflator = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return mProfileList.size();
	}

	
	@Override
	public Object getItem(int arg0) {

		return mProfileList.get(arg0);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Profile profile = mProfileList.get(position);
		
		mView = convertView;
		mView = layoutInflator.inflate(R.layout.friends_row_layout, null);
		TextView friendName = (TextView)mView.findViewById(R.id.tv_friend_name);
		friendName.setText(profile.displayName);

		ProfilePictureView profilePictureView = (ProfilePictureView)mView.findViewById(R.id.profilePicture);
		makeUserRequest(Session.getActiveSession(), profilePictureView, profile.facebookId);
		return mView;
	}
	
	private void makeUserRequest(final Session session, final ProfilePictureView profilePictureView, final String userId) {
		 
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {
				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						profilePictureView.setProfileId(userId);
					}
				}
				if (response.getError() != null) {
					// Handle errors, will do so later.
				}
			}
		});
		
		request.executeAsync();
	}
	
	
}

