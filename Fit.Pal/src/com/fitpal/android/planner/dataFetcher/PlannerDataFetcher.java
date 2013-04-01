package com.fitpal.android.planner.dataFetcher;

import com.fitpal.android.profile.entity.Profile;

public class PlannerDataFetcher {

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
