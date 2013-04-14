package com.fitpal.android.routine.ui;

import java.util.List;

import com.fitpal.android.R;
import com.fitpal.android.routine.entity.Routine;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoutineAdapter extends BaseAdapter{

	private List<Routine> mRoutineList = null;
	private Activity mActivity = null;
	private LayoutInflater layoutInflator = null;
	private View mView;
	
	public RoutineAdapter(Activity activity, List<Routine> routineList){
		mActivity = activity;
		mRoutineList = routineList;
		layoutInflator = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return mRoutineList.size();
	}

	
	@Override
	public Object getItem(int arg0) {

		return mRoutineList.get(arg0);
	}

	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Routine routine = mRoutineList.get(position);
		
		mView = convertView;
		mView = layoutInflator.inflate(R.layout.routine_row_layout, null);
		TextView routineName = (TextView)mView.findViewById(R.id.tv_routine_name);
		routineName.setText(routine.name);
		
		return mView;
	}
	
}

