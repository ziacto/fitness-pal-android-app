package com.fitpal.android.routine.dataFetcher;

import java.util.ArrayList;
import java.util.List;

import com.fitpal.android.routine.entity.Routine;
import com.fitpal.android.utils.Utils;

public class RoutineDataFetcher {

	private static List<Routine> mRoutineList;
	
	public static List<Routine> fetchRoutineList(){
		if(Utils.isNullOrEmptyCollection(mRoutineList))
			mRoutineList = new ArrayList<Routine>();

		return mRoutineList;
	}

	public static boolean deleteRoutine(long routineId){
		if(Utils.isNullOrEmptyCollection(mRoutineList))
			return false;

		boolean isDeleted = false;
		for(int count = 0; count < mRoutineList.size() ; count++){
			if(mRoutineList.get(count).id == routineId){
				mRoutineList.remove(count);
				isDeleted = true;
				break;
			}
		}
		return isDeleted;
	}
	
	public static boolean shareRoutine(long routineId){
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