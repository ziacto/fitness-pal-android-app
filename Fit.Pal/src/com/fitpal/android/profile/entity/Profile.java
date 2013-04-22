package com.fitpal.android.profile.entity;

public class Profile {

	public String displayName;
	public String userName;
	public String statusMessage;
	public String gender;
	public String dob;
	public String email;
	public String weight;
	public String height;
	public double latitude;
	public double longitude;
	public String facebookId;
	
	public Profile(){
		
	}
	
	public Profile(String name, String id){
		this.displayName = name;
		this.facebookId = id;
	}
}
