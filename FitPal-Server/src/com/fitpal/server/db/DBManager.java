package com.fitpal.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.fitpal.server.utils.Utils;


public class DBManager {
	
	private static Logger log = Utils.getLogger();
	
	static{
		try{
			Class.forName(DbConstants.MYSQL_DRIVER);
		}catch(ClassNotFoundException cfe){
			log.error("DBManager:int :: Database Driver Class Not Found");
			cfe.printStackTrace();
		}
	}


	public static Connection openConnection() {
		Connection conn = null;
		try {			
			conn = DriverManager.getConnection
					(DbConstants.MYSQL_JDBC_CONNECTION_STRING 
							+ DbConstants.HOST + DbConstants.MYSQL_SEPARATOR 
							+ DbConstants.DB, DbConstants.USER, DbConstants.PASSWD);

			return conn;
		}catch (SQLException ex) {
			log.error("DBManager:openConnection :: " + ex);            
		}

		return conn;
	}

	public static void cleanupCollection(Connection conn, Statement stmt) {

		try {
			if(stmt != null)
				stmt.close();

			if (conn != null)
				conn.close();

		} catch (SQLException ex) {
			log.error("DBManager:cleanupCollection :: " + ex); 
		}

	}

	public static void closeResultSet(ResultSet rs){
		try{
			if(rs != null)
				rs.close();
		}catch(SQLException sqle){
			log.error("DBManager:closeResultSet :: " + sqle); 
		}
	}
	
}
