package com.fitpal.android.routine.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;
import com.fitpal.android.common.BaseFragment;
import com.fitpal.android.common.FragmentManager;
import com.fitpal.android.routine.dataFetcher.RoutineDataFetcher;
import com.fitpal.android.routine.entity.Routine;

public class MyRoutinesFragment extends BaseFragment {

	private Activity mActivity;
	private List<Routine> mRoutineList;
	private ListView mListView;
	private RoutineAdapter mRoutineAdapter;
	
	public MyRoutinesFragment(){
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.my_routines_page, container);
		initializeUI(view);

		return null;
	}

	protected void initializeUI(View view){
		mActivity = getActivity();
		mListView = (ListView)view.findViewById(R.id.lv_routines);
	}
	

	@Override
	public void addClicked(){
		System.out.println("inside Myroutines clicked");
		Intent intent = new Intent(mActivity, AddRoutineActivity.class);
		intent.putExtra("MODE", "ADD");
		mActivity.startActivity(intent);
	}
	
	@Override
	public void onResume(){
		new GetMyRoutinesTask().execute(null, null, null);
		FragmentManager.getInstance().addFragmentToMap(this, "com.fitpal.android.routine.ui.MyRoutinesFragment" );
		super.onResume();
	}
	
/* End Of Action Mode class */
	
	private class GetMyRoutinesTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// get daily tasks from Server
			mRoutineList = RoutineDataFetcher.fetchRoutineList();
			System.out.println("Routine List : " + mRoutineList.size());
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			mRoutineAdapter = new RoutineAdapter(mActivity, mRoutineList);
			mListView.setAdapter(mRoutineAdapter);
			
			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
					AppInfo.routine = mRoutineList.get(position);
					Intent intent = new Intent(mActivity, AddRoutineActivity.class);
					intent.putExtra("MODE", "EDIT");
					mActivity.startActivity(intent);
				}

			});
		}

	}
}
