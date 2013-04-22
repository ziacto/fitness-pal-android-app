package com.fitpal.android.profile.ui;

import java.util.List;

import com.fitpal.android.R;
import com.fitpal.android.common.Constants;
import com.fitpal.android.common.SharedPreferenceStore;
import com.fitpal.android.profile.dataFetcher.ProfileDataFetcher;
import com.fitpal.android.profile.entity.Profile;
import com.fitpal.android.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FindNearbyUsersActivity extends FragmentActivity{

	private Activity mActivity;
	private GoogleMap mMap;
	private List<Profile> mUserList;
	private ListView mListView;
	private AddFriendsAdapter mAddFriendAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		setContentView(R.layout.show_nearby_users_page);
		mListView = (ListView)findViewById(R.id.lv_nearby_users);
		initialize();
	}

	private void initialize(){
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.user_map)).getMap();

			System.out.println("Map : " + mMap);
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}

	}



	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		//  mMap.setOnMapLongClickListener(this);
		//   mMap.setOnMapClickListener(this);
		double latitude = Utils.convertStringToDouble(SharedPreferenceStore.getValueFromStore(Constants.KEY_LATITUDE, this));
		double longitude = Utils.convertStringToDouble(SharedPreferenceStore.getValueFromStore(Constants.KEY_LONGITUDE, this));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
		
	}

	public void setMarkersOnMap(){
		for(Profile profile : mUserList ){
			LatLng myLatLng = new LatLng(profile.latitude, profile.longitude);
			MarkerOptions mrk = new MarkerOptions().position(myLatLng).title(profile.displayName);
			mrk.describeContents();
			mMap.addMarker(mrk);
		}
	}
	
	private class GetMyFriendsTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// get daily tasks from Server
			String userName = SharedPreferenceStore.getValueFromStore(Constants.KEY_USERNAME, mActivity);
			mUserList = ProfileDataFetcher.getNearbyPeople(userName);
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			setMarkersOnMap();
			mAddFriendAdapter = new AddFriendsAdapter(mActivity, mUserList);
			mListView.setAdapter(mAddFriendAdapter);

			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
					// mFriendsList.get(position);
					//Intent intent = new Intent(mActivity, AddRoutineActivity.class);
					//intent.putExtra("MODE", "EDIT");
					//mActivity.startActivity(intent);
				}

			});
		}
	}



}
