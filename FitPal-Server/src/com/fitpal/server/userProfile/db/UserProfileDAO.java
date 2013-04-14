package com.fitpal.server.userProfile.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitpal.server.db.DBManager;
import com.fitpal.server.userProfile.entity.UserProfile;

public class UserProfileDAO {

	private static final String TABLE_NAME = "user_profile";
	private static final String COL_USERNAME = "username";
	private static final String COL_PASSWORD = "password";
	private static final String COL_DISPLAY_NAME = "display_name";
	private static final String COL_EMAIL = "email";
	private static final String COL_DOB = "dob";
	private static final String COL_HEIGHT = "height";
	private static final String COL_WEIGHT = "weight";
	
	
	
	private static final String GET_USER_PROFILE = "select * from " + TABLE_NAME + " where " + COL_USERNAME + " = ? ";

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
				userProfile.dob = rs.getString(COL_DOB);
				userProfile.email = rs.getString(COL_EMAIL);
				userProfile.height = rs.getFloat(COL_HEIGHT);
				userProfile.weight = rs.getFloat(COL_WEIGHT);
				userProfile.password = rs.getString(COL_PASSWORD);
				userProfile.userName = rs.getString(COL_USERNAME);
			}
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return userProfile;
	}
}
