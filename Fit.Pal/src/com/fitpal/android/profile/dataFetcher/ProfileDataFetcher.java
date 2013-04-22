package com.fitpal.android.profile.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.communication.DataCommunicator;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.utils.Utils;
import com.google.gson.Gson;

public class ProfileDataFetcher {

	private static Gson gson = new Gson();
	private static final String GET_USER_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/get-user?username=";
	private static final String ADD_USER_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/add-user";
	private static final String EDIT_USER_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/edit-user";
	
	public static Profile fetchProfileInfo(String userName){
		Profile profile = null;
		try{
			String response = Utils.convertStreamToString(DataCommunicator.sendGetDataToServer(GET_USER_URL + userName));
			if(!Utils.isNullOrEmptyStr(response)){
				profile = gson.fromJson(response, Profile.class);
				if(profile == null)
					profile = new Profile();
			}
		}catch(Exception e){
			System.out.println(e);
			profile = new Profile();
		}
		return profile;
	}
	
	public static void addNewUser(Profile profile){
		try{
			String payload = gson.toJson(profile);
			DataCommunicator.sendPostDataToServer(ADD_USER_URL, payload);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void updateProfile(Profile profile){
		try{
			String payload = gson.toJson(profile);
			DataCommunicator.sendPostDataToServer(EDIT_USER_URL, payload);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static List<Profile> getAllFriends(String username){
		List<Profile> profileList = new ArrayList<Profile>();
		profileList.add(new Profile("Hrishikesh Sawle","745668977"));
		profileList.add(new Profile("Hetal Doshi","614822488"));
		profileList.add(new Profile("Aarti Sawant","100000910989023"));
		profileList.add(new Profile("Hrishikesh Deshmukh","100001045971471"));
		profileList.add(new Profile("Hardik Gogri","100000606131285"));
		profileList.add(new Profile("Ujas Doshi","100000544153911"));
		profileList.add(new Profile("Aakansha Singh","705717202"));
		profileList.add(new Profile("Aakash Shah","516150682"));
		profileList.add(new Profile("Hitesh Tanwani","710675906"));
		profileList.add(new Profile("Jay Bharuka","100000870455298"));
		profileList.add(new Profile("Farhadnawaz Shaikh","100002315388212"));
		profileList.add(new Profile("Abhijeet Verma","1348363668"));
		return profileList;
	}
	
	public static List<Profile> getNearbyPeople(String username){
		List<Profile> profileList = new ArrayList<Profile>();
		
		return profileList;
	}
}
