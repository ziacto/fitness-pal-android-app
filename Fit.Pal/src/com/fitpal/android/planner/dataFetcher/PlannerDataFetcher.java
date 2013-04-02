package com.fitpal.android.planner.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.planner.entity.Task;

public class PlannerDataFetcher {

	//private static Gson gson = new Gson();
	
	public static List<Task> fetchTaskList(String date){
		List<Task> taskList = new ArrayList<Task>();
		Task task = new Task();
		task.name = "Running";
		task.startTime = "12:30pm";
		task.endTime = "2:30pm";
		task.duration = "2 hours";
		taskList.add(task);
		
		Task task2 = new Task();
		task2.name = "Push Ups";
		task2.startTime = "2:45pm";
		task2.endTime = "3:15pm";
		task2.duration = "30 minutes";
		taskList.add(task2);
		
		return taskList;
	}
	
	public static boolean deleteTask(String taskId){
		return true;
	}
	
	public static boolean addTask(Task task){
		
		return true;
	}
}
