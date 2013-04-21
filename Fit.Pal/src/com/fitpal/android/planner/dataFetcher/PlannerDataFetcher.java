package com.fitpal.android.planner.dataFetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fitpal.android.planner.entity.Task;

public class PlannerDataFetcher {

	//private static Gson gson = new Gson();
	
	private static Map<String,List<Task>> plannerMap = new HashMap<String, List<Task>>();

	public static List<Task> fetchTaskList(String date){
		List<Task> taskList = plannerMap.get(date);
		if(taskList == null){
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}

	public static void addRoutineToDay(Task task, String date){
		List<Task> taskList = plannerMap.get(date);
		if(taskList == null){
			taskList = new ArrayList<Task>();
		}

		taskList.add(task);
		plannerMap.put(date, taskList);
	}

	public static boolean deleteTask(long taskId){
		
		return true;
	}
	
}
