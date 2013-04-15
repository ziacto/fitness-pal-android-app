package com.fitpal.android.routine.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.utils.Utils;

public class RoutineDataFetcher {

	//private static Gson gson = new Gson();
	private static List<Routine> mRoutineList;
	
	public static List<Routine> fetchRoutineList(){
		if(Utils.isNullOrEmptyCollection(mRoutineList))
			mRoutineList = new ArrayList<Routine>();
		
		return mRoutineList;
	}
	
	public static boolean deleteRoutine(long routineId){
		return true;
	}
	
	public static boolean addRoutine(Routine routine){
		if(mRoutineList == null || Utils.isNullOrEmptyCollection(mRoutineList)){
			mRoutineList = new ArrayList<Routine>();
		}
		mRoutineList.add(routine);
		return true;
	}
	
	public static boolean editRoutine(Routine routine){
		return true;
	}
}