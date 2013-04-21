package com.fitpal.android.planner.dataFetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fitpal.android.communication.DataCommunicator;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.planner.entity.TaskResponse;
import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.routine.entity.RoutineResponse;
import com.fitpal.android.utils.Utils;
import com.google.gson.Gson;

public class PlannerDataFetcher {

	private static Gson gson = new Gson();
	private static final String GET_TASL_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/get-tasks?username=%s&date=%s";
	private static final String ADD_TASK_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/add-task";
	
	public static List<Task> fetchTaskList(String userName, String date){
		List<Task> taskList = null;
		try{
			String response = Utils.convertStreamToString(DataCommunicator.sendGetDataToServer(String.format(GET_TASL_URL,userName, date)));
			if(!Utils.isNullOrEmptyStr(response)){
				TaskResponse taskResp = gson.fromJson(response, TaskResponse.class);
				if(taskResp != null)
					taskList = taskResp.list;
				else
					taskList = new ArrayList<Task>();
			}
		}catch(Exception e){
			e.printStackTrace();
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}

	public static void addRoutineToDay(Task task, String date){
		try{
			String postPayload = gson.toJson(task);
			DataCommunicator.sendPostDataToServer(ADD_TASK_URL, postPayload);
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void deleteTask(long taskId){
		
	}
	
}
