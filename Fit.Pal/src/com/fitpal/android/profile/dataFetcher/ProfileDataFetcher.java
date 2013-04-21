package com.fitpal.android.profile.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.profile.entity.Profile;

public class ProfileDataFetcher {

	//private static Gson gson = new Gson();
	
	public static Profile fetchProfileInfo(String userName){
		Profile profile = new Profile();
		profile.dob = "27 March 1987";
		profile.name = "Sushil";
		profile.weight = "136 lb";
		profile.height = "175cms";
		profile.gender="Male";
		profile.statusMessage="Life is a mess";

		return profile;
	}
	
	public static void addNewUser(String username){
		
	}
	
	public static void updateProfile(Profile profile){

	}
	
	public static List<Profile> getAllFriends(String username){
		List<Profile> profileList = new ArrayList<Profile>();
		
		return profileList;
	}
}
