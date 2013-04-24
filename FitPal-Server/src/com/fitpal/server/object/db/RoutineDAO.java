package com.fitpal.server.object.db;

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
import com.fitpal.server.object.entity.Routine;
import com.fitpal.server.object.entity.Task;
import com.fitpal.server.object.entity.Workout;

public class RoutineDAO {
	private static final String INSERT_ROUTINE = "insert into routine(routine, username, name) values(?, ?, ?)";
	private static final String INSERT_TASK = "insert into task(routine_id, date, start_time, end_time, duration) values(?, ?, ?, ?, ?)";
	private static final String GET_ROUTINES = "select * from routine where username = ?";
	private static final String GET_TASKS = "select * from routine r, task t where (r.routine_id = t.routine_id) and username = ? and date = ?";
	private static final String UPDATE_ROUTINE = "update routine set routine = ?, username = ?, name = ? where routine_id = ?";
	private static final String DELETE_ROUTINE = "delete from routine where routine_id = ?";
	private static final String GET_ROUTINES_TIME = "select r.*, sum(duration) as total_time from task t, routine r where (t.routine_id = r.routine_id) and (r.username = ?) group by t.routine_id";

	public void insertOrEditRoutine(Routine routine, boolean edit) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try{
			conn = DBManager.openConnection();
			int count = 1;
			if (edit) {
				pStmt = conn.prepareStatement(UPDATE_ROUTINE);
			} else {
				pStmt = conn.prepareStatement(INSERT_ROUTINE);
			}
			String workoutList = "";
			int i = 1;
			for (Workout workout : routine.workoutList) {
				workoutList = workoutList + workout.workoutBody + ":" + workout.exercise;
				if (i < routine.workoutList.size()) {
					workoutList = workoutList + ",";
				}
				i++;
			}
			pStmt.setString(count++, workoutList);
			pStmt.setString(count++, routine.userName);
			pStmt.setString(count++, routine.name);
			if (edit) {
				pStmt.setInt(count++, (int)routine.id);
			}
			pStmt.executeUpdate();			
		}catch(SQLException sqle){
			System.out.println("Exception while inserting plan in db");
			sqle.printStackTrace();
		}finally{			
			DBManager.cleanupCollection(conn, pStmt);
		}
	}
	
	public void deleteRoutine(int routineId) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(DELETE_ROUTINE);
			pStmt.setInt(1, routineId);
			pStmt.executeUpdate();
		}catch (SQLException sqle) {
			System.err.println("Error deleting data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
	}
	
	public List<Routine> getRoutines(String userName){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Routine routine = null;
		List<Routine> routines = new ArrayList<Routine>();

		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(GET_ROUTINES);
			pStmt.setString(1, userName);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				routine = new Routine();
				routine.name = rs.getString("name");
				//Construct routine list
				String [] workouts = rs.getString("routine").split(",");
				List<Workout> workoutList = new ArrayList<Workout>();
				for (String workout : workouts) {
					Workout w = new Workout();
					w.workoutBody = workout.split(":")[0];
					w.exercise = workout.split(":")[1];
					workoutList.add(w);
				}
				routine.id = rs.getInt("routine_id");
				routine.userName = userName;
				routine.workoutList = workoutList;
				routines.add(routine);
			}
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return routines;
	}
	
	public void addTask(Task task) throws ParseException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try{
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(INSERT_TASK);
			int count = 1;
			pStmt.setInt(count++, (int) task.routineId);
			pStmt.setString(count++, task.date);
			pStmt.setString(count++, task.startTime);
			pStmt.setString(count++, task.endTime);
			SimpleDateFormat format = new SimpleDateFormat("h:mm a");
			Date startDate = format.parse(task.startTime);
			Date endDate = format.parse(task.endTime);
			int duration = (int) ((endDate.getTime() - startDate.getTime()) / 1000);
			pStmt.setInt(count++, duration);
			pStmt.executeUpdate();			
		}catch(SQLException sqle){
			System.out.println("Exception while inserting plan in db");
			sqle.printStackTrace();
		}finally{			
			DBManager.cleanupCollection(conn, pStmt);
		}
	}
	
	public List<Task> getTasksByDate(String userName, String date) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Task task = null;
		List<Task> tasks = new ArrayList<Task>();

		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(GET_TASKS);
			pStmt.setString(1, userName);
			pStmt.setString(2, date);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				task = new Task();
				
				Routine routine = new Routine();
				routine.name = rs.getString("name");
				String [] workouts = rs.getString("routine").split(",");
				List<Workout> workoutList = new ArrayList<Workout>();
				for (String workout : workouts) {
					Workout w = new Workout();
					w.workoutBody = workout.split(":")[0];
					w.exercise = workout.split(":")[1];
					workoutList.add(w);
				}
				routine.id = rs.getInt("routine_id");
				routine.userName = userName;
				routine.workoutList = workoutList;
				
				task.id = rs.getInt("id");
				task.routine = routine;
				//formatter.applyPattern("MM/dd/yyyy");
				task.date = rs.getString("date");
				task.startTime = rs.getString("start_time");
				task.endTime = rs.getString("end_time");
				tasks.add(task);
			}
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return tasks;
	}
	
	public List<Routine> getTimeSpentByRountine(String userName){
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Routine routine = null;
		List<Routine> routines = new ArrayList<Routine>();

		try {
			conn = DBManager.openConnection();
			pStmt = conn.prepareStatement(GET_ROUTINES_TIME);
			pStmt.setString(1, userName);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				routine = new Routine();
				routine.name = rs.getString("name");
				//Construct routine list
				String [] workouts = rs.getString("routine").split(",");
				List<Workout> workoutList = new ArrayList<Workout>();
				for (String workout : workouts) {
					Workout w = new Workout();
					w.workoutBody = workout.split(":")[0];
					w.exercise = workout.split(":")[1];
					workoutList.add(w);
				}
				routine.id = rs.getInt("routine_id");
				routine.userName = userName;
				routine.workoutList = workoutList;
				routine.timeSpent = rs.getInt("total_time");
				routines.add(routine);
			}
		}catch (SQLException sqle) {
			System.err.println("Error retrieving data" + sqle);
		} finally {
			DBManager.cleanupCollection(conn, pStmt);
		}
		
		return routines;
	}
}
