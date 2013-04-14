package com.fitpal.android.routine.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.routine.entity.Routine;

public class RoutineDataFetcher {

	//private static Gson gson = new Gson();
	
	public static List<Routine> fetchRoutineList(){
		List<Routine> routineList = new ArrayList<Routine>();
		
		Routine routine1 = new Routine();
		routine1.name = "Morning";
		routineList.add(routine1);
		
		Routine routine2 = new Routine();
		routine2.name = "Evening";
		routineList.add(routine2);
		
		return routineList;
	}
	
	public static boolean deleteRoutine(String routineId){
		return true;
	}
	
	public static boolean addTask(Routine routine){
		return true;
	}
}
