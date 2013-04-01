package com.fitpal.android.profile.dataFetcher;

import com.fitpal.android.profile.entity.Profile;

public class ProfileDataFetcher {

	//private static Gson gson = new Gson();
	
	public static Profile fetchProfileInfo(){
		Profile profile = new Profile();
		profile.age = 23;
		profile.displayName = "Sushil";
		profile.gender="Male";
		profile.statusMessage="Life is a mess";

		return profile;
	}
}
