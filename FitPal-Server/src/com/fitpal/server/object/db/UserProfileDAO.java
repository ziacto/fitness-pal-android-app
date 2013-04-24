package com.fitpal.server.object.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fitpal.server.db.DBManager;
import com.fitpal.server.object.entity.FriendsResponse;
import com.fitpal.server.object.entity.NearbyUsersResponse;
import com.fitpal.server.object.entity.UserProfile;

public class UserProfileDAO {

	private static final String TABLE_NAME = "userprofile";
	private static final String COL_USERNAME = "username";
	private static final String COL_DISPLAY_NAME = "display_name";
	private static final String COL_EMAIL = "email";
	private static final String COL_DOB = "dob";
	private static final String COL_HEIGHT = "height";
	private static final String COL_WEIGHT = "weight";
	private static final String GET_USER_PROFILE = "select * from " + TABLE_NAME + " where " + COL_USERNAME + " = ? ";
	private static final String INSERT_USER = "insert into " + TABLE_NAME + "(username, display_name, email, dob, height, weight, latitude, longitude, facebookId) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_USER = "update " + TABLE_NAME + " set display_name = ?, email = ?, dob = ?, height = ?, weight = ?, latitude = ?, longitude = ?, facebookId = ? where username = ?";
	private static final String ADD_FRIEND = "insert into friends(user, friend) values(?, ?)";
	private static final String GET_FRIENDS = "select userprofile.* from friends, userprofile where (userprofile.username = friend) and user = ?";
	private static final String GET_NEARBY_USERS = "select u2.* from userprofile u1, userprofile u2 where (u1.username = ?) and (ABS(u1.latitude - u2.latitude) <= 0.1 and ABS(u1.longitude - u2.longitude) <= 0.1) and (u1.username <> u2.username) and (u2.username not in (select friend from friends where user = ?))"; 

	public UserProfile getUserProfile(String userName){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		UserProfile userProfile = null;

		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(GET_USER_PROFILE);
			pStmt.setString(1, userName);
			rs = pStmt.executeQuery();
			if(rs.next()){
				userProfile = new UserProfile();
				userProfile.displayName = rs.getString(COL_DISPLAY_NAME);
				//formatter.applyPattern("MM/dd/yyyy");
				userProfile.dob = rs.getString(COL_DOB);
				userProfile.email = rs.getString(COL_EMAIL);
				userProfile.height = rs.getFloat(COL_HEIGHT);
				userProfile.weight = rs.getFloat(COL_WEIGHT);
				userProfile.userName = rs.getString(COL_USERNAME);
				userProfile.latitude = rs.getDouble("latitude");
				userProfile.longitude = rs.getDouble("longitude");
				userProfile.facebookId = rs.getString("facebookId");
			}
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return userProfile;
	}
	
	public FriendsResponse getFriends(String userName){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		UserProfile userProfile = null;
		FriendsResponse res = new FriendsResponse();
		List<UserProfile> list = new ArrayList<UserProfile>();

		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(GET_FRIENDS);
			pStmt.setString(1, userName);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				userProfile = new UserProfile();
				userProfile.displayName = rs.getString(COL_DISPLAY_NAME);
				userProfile.dob = rs.getString(COL_DOB);
				userProfile.email = rs.getString(COL_EMAIL);
				userProfile.height = rs.getFloat(COL_HEIGHT);
				userProfile.weight = rs.getFloat(COL_WEIGHT);
				userProfile.userName = rs.getString(COL_USERNAME);
				userProfile.latitude = rs.getDouble("latitude");
				userProfile.longitude = rs.getDouble("longitude");
				userProfile.facebookId = rs.getString("facebookId");
				list.add(userProfile);
			}
			res.list = list;
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return res;
	}
	
	public NearbyUsersResponse getNearbyUsers(String userName){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		UserProfile userProfile = null;
		NearbyUsersResponse response = new NearbyUsersResponse();

		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(GET_NEARBY_USERS);
			pStmt.setString(1, userName);
			pStmt.setString(2, userName);
			rs = pStmt.executeQuery();
			List<UserProfile> users = new ArrayList<UserProfile>();
			while (rs.next()) {
				userProfile = new UserProfile();
				userProfile.displayName = rs.getString(COL_DISPLAY_NAME);
				userProfile.dob = rs.getString(COL_DOB);
				userProfile.email = rs.getString(COL_EMAIL);
				userProfile.height = rs.getFloat(COL_HEIGHT);
				userProfile.weight = rs.getFloat(COL_WEIGHT);
				userProfile.userName = rs.getString(COL_USERNAME);
				userProfile.latitude = rs.getDouble("latitude");
				userProfile.longitude = rs.getDouble("longitude");
				userProfile.facebookId = rs.getString("facebookId");
				users.add(userProfile);
			}
			response.list = users;
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return response;
	}
	
	public void insertOrEditUser(UserProfile userProfile, boolean edit) throws ParseException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try{
			conn = DBManager.openConnection();
			int count = 1;
			if (edit) {
				pStmt = conn.prepareStatement(UPDATE_USER);
			} else {
				pStmt = conn.prepareStatement(INSERT_USER);
				pStmt.setString(count++, userProfile.userName);
			}
			pStmt.setString(count++, userProfile.displayName);
			pStmt.setString(count++, userProfile.email);
			//Date date = formatter.parse(userProfile.dob);
			//formatter.applyPattern("yyyy-MM-dd");	
			pStmt.setString(count++, userProfile.dob);
			pStmt.setFloat(count++, userProfile.height);
			pStmt.setFloat(count++, userProfile.weight);
			pStmt.setDouble(count++, userProfile.latitude);
			pStmt.setDouble(count++, userProfile.longitude);
			pStmt.setString(count++, userProfile.facebookId);
			if (edit) {
				pStmt.setString(count++, userProfile.userName);
			}
			pStmt.executeUpdate();			
		}catch(SQLException sqle){
			System.out.println("Exception while inserting plan in db");
			sqle.printStackTrace();
		}finally{			
			DBManager.cleanupCollection(conn, pStmt);
		}
	}
	
	public void addFriend(String user, String friend) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try{
			conn = DBManager.openConnection();
			int count = 1;
			pStmt = conn.prepareStatement(ADD_FRIEND);
			pStmt.setString(count++, user);
			pStmt.setString(count++, friend);
			pStmt.executeUpdate();			
		}catch(SQLException sqle){
			System.out.println("Exception while inserting plan in db");
			sqle.printStackTrace();
		}finally{			
			DBManager.cleanupCollection(conn, pStmt);
		}
	}
	
	public static void batchInsert() throws IOException, SQLException {
		File file = new File("C:\\Users\\Dinesh\\Documents\\GitHub\\fitness-pal-android-app\\FitPal-Server\\src\\com\\fitpal\\server\\object\\db\\users.csv");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		Connection conn = DBManager.openConnection();
		PreparedStatement pStmt = null;
		pStmt = conn.prepareStatement(INSERT_USER);
		int count = 1;
		double basLat = 29.6195;
		double baseLong = -82.3741;
		while ((line = reader.readLine()) != null) {
			pStmt.setString(count++, line.split(",")[0].trim());
			pStmt.setString(count++, line.split(",")[2].trim());
			pStmt.setString(count++, null);
			pStmt.setString(count++, null);
			pStmt.setFloat(count++, 0);
			pStmt.setFloat(count++, 0);
			pStmt.setDouble(count++, basLat + Math.random()*0.1);
			pStmt.setDouble(count++, baseLong + Math.random()*0.1);
			pStmt.setString(count++, line.split(",")[1].trim());
			pStmt.executeUpdate();
			count = 1;
		}
		reader.close();
		conn.close();
	}
	
	public static void main(String [] args) throws IOException, SQLException {
		UserProfileDAO.batchInsert();
	}
}
