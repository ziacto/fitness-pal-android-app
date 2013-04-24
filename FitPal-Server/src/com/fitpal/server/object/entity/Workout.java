package com.fitpal.server.object.entity;

public class Workout {
	public String workoutBody;
	public String exercise;
	
	@Override
	public String toString(){
		return exercise;
	}
}
