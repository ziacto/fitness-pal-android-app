package com.fitpal.android.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String KEY_DATE = "KEY_DATE";
	public static final String SIMPLE_DATE_FORMAT = "MM/dd/yyyy";
		
	public static final String[] CARDIO_EXERCISES = {"Climbing the Stairmaster", 
														"Cycling in the Stationary Bicycle", 
														"Running On the treadmill"};

	public static final String[] ABS_EXERCISES = {"Crunch", "Twisting Crunch", "Side Crunch", "Reverse Crunch", "Sit Up"};
	public static final String[] BACK_EXERCISES = {"Row(Dumbells)", "Back Fly", "Front Fly", "Pulldown - Back"};
	public static final String[] CHEST_EXERCISES = {"Bench Press", "Dumbell Press", "Chest Press", "Push Up"};
	public static final String[] SHOULDER_EXERCISES = {"Shoulder Press", "Upright Row", "Military Press", "Push Press"};
	public static final String[] BICEPS_EXERCISES = {"Biceps Curl", "Chin Up", "Inner Biceps"};
	public static final String[] STRETCHING_EXERCISES = {"Head Tilt", "Trunk Rotation", "Sideways Arm Swing", "Hamstring Stretch"};
	
	public static Map<String, String[]> exercisesMap = new HashMap<String, String[]>();
	
	static{
		exercisesMap.put("Cardio", CARDIO_EXERCISES);
		exercisesMap.put("Cardio", ABS_EXERCISES);
		exercisesMap.put("Cardio", BACK_EXERCISES);
		exercisesMap.put("Cardio", CHEST_EXERCISES);
		exercisesMap.put("Cardio", SHOULDER_EXERCISES);
		exercisesMap.put("Cardio", BICEPS_EXERCISES);
		exercisesMap.put("Cardio", STRETCHING_EXERCISES);
	}
}
