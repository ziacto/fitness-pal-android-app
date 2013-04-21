package com.fitpal.android.common;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

public class FacebookHelper {

	public static void makeMeRequest(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
					
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
			            if (session == Session.getActiveSession()) {
			                if (user != null) {
			                    // Set the id for the ProfilePictureView
			                    // view that in turn displays the profile picture.
//			                    profilePictureView.setProfileId(user.getId());
			                    // Set the Textview's text to the user's name.
			//                    userNameView.setText(user.getName());
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
