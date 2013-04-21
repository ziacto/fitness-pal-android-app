package com.fitpal.android.routine.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.communication.DataCommunicator;
import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.routine.entity.RoutineResponse;
import com.fitpal.android.utils.Utils;
import com.google.gson.Gson;

public class RoutineDataFetcher {

	private static Gson gson = new Gson();
	
	private static final String GET_ROUTINE_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/get-routines?username=";
	private static final String ADD_ROUTINE_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/add-routine/";
	private static final String EDIT_ROUTINE_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/edit-routine/";
	private static final String DELETE_ROUTINE_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/delete-routine?id=";
	private static final String SHARE_ROUTINE_URL = DataCommunicator.SERVER_BASE_ADDRESS +  "/share-routine?id=";
	
	public static List<Routine> fetchRoutineList(String userName){
		List<Routine> routineList = null;

		try{
			String response = Utils.convertStreamToString(DataCommunicator.sendGetDataToServer(GET_ROUTINE_URL + userName));
			if(!Utils.isNullOrEmptyStr(response)){
				RoutineResponse routineResp = gson.fromJson(response, RoutineResponse.class);
				if(routineResp != null)
					routineList = routineResp.list;
				else
					routineList = new ArrayList<Routine>();
			}
		}catch(Exception e){
			e.printStackTrace();
			routineList = new ArrayList<Routine>();
		}
		return routineList;
	}

	public static void deleteRoutine(long routineId){
		
		try{
			DataCommunicator.sendGetDataToServer(DELETE_ROUTINE_URL + routineId);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void shareRoutine(long routineId){
		try{
			DataCommunicator.sendGetDataToServer(SHARE_ROUTINE_URL + routineId);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void addRoutine(Routine routine){
		List<Routine> routineList;
		try{
			String postPayload = gson.toJson(routine);
			DataCommunicator.sendPostDataToServer(ADD_ROUTINE_URL, postPayload);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void editRoutine(Routine routine){
		try{
			String postPayload = gson.toJson(routine);
			DataCommunicator.sendPostDataToServer(EDIT_ROUTINE_URL, postPayload);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}