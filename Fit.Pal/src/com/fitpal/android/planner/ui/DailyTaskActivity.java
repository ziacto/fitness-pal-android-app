package com.fitpal.android.planner.ui;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fitpal.android.R;
import com.fitpal.android.common.Constants;
import com.fitpal.android.planner.dataFetcher.PlannerDataFetcher;
import com.fitpal.android.planner.entity.Task;
import com.fitpal.android.utils.AndroidUtils;

public class DailyTaskActivity extends SherlockFragmentActivity {

	private Activity mActivity;
	private ActionMode mActionMode;
	private String mDate;
	private ListView mLvDailyTasks;
	private List<Task> mTaskList;
	private DailyTaskAdapter mTaskAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_planner_page);
		mActivity = this;
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null){
			mDate = extras.getString(Constants.KEY_DATE);
		}
		mLvDailyTasks = (ListView)findViewById(R.id.lv_daily_planner);
		mLvDailyTasks.setClickable(true);
		
		TextView date = (TextView)findViewById(R.id.tv_date);
		date.setText(mDate);

		new GetDailyTaskListTask().execute(null, null, null);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.menu_daily_task, menu);

		menu.getItem(0).setOnMenuItemClickListener(new AddListener());
		return super.onCreateOptionsMenu(menu);
	}
	
    private class AddListener implements MenuItem.OnMenuItemClickListener{

		public boolean onMenuItemClick(MenuItem item) {
			AndroidUtils.showToastNotification("Add Button clicked", mActivity);
			return false;
		}
    }
	
	/* Action Mode class */
	private final class ItemSelectActionMode implements ActionMode.Callback {
		
		private Task mTask;
		private int mPosition;
		
		ItemSelectActionMode(Task task, int position){
			mTask = task;
			mPosition = position;
		}
		
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //Used to put dark icons on light action bar

        	menu.add(R.string.menu_edit)
                .setIcon(R.drawable.ic_compose)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            menu.add(R.string.menu_delete)
                .setIcon(R.drawable.ic_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        	String itemTitle = item.getTitle().toString();
        	if(itemTitle.equalsIgnoreCase(mActivity.getString(R.string.menu_edit))){
        		
        	}else if(itemTitle.equalsIgnoreCase(mActivity.getString(R.string.menu_delete))){
        		mTaskList.remove(mPosition);
        		mTaskAdapter.notifyDataSetChanged();
        		new DeleteTask().execute(mTask.id);
        	}
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    }
	
	/* End Of Action Mode class */
	
	private class GetDailyTaskListTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// get daily tasks from Server
			mTaskList = PlannerDataFetcher.fetchTaskList(mDate);
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			mTaskAdapter = new DailyTaskAdapter(mActivity, mTaskList);
			mLvDailyTasks.setAdapter(mTaskAdapter);
			mLvDailyTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
					mActionMode = startActionMode(new ItemSelectActionMode(mTaskList.get(position), position));
				}

			});
		}

	}
	
	private class DeleteTask extends AsyncTask<String, Void, Void>{

		boolean success = false;
		@Override
		protected Void doInBackground(String... params) {
			if(params == null || params.length < 1)
				return null;
			
			String taskId = params[0]; 
			success = PlannerDataFetcher.deleteTask(taskId);
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			if(success)
				AndroidUtils.showToastNotification("Task Deleted", mActivity);
			else
				AndroidUtils.showToastNotification("Unable to Delete Task", mActivity);
		}

	}
}
