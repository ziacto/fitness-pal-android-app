package com.fitpal.android.common;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationTracker extends Service implements LocationListener{

	private Context mContext;
	private LocationManager mLocationManager;
	private Location mLocation;

	public LocationTracker(Context context){
		this.mContext = context;
		//getLocation();
	}

	@Override
	public void onLocationChanged(Location arg0) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public Location getLocation() {
		boolean isGPSEnabled = false;
		boolean isWifiEnabled = false;
		try {
			mLocationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = mLocationManager.isProviderEnabled(mLocationManager.GPS_PROVIDER);

			// getting network status
			isWifiEnabled = mLocationManager.isProviderEnabled(mLocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isWifiEnabled) {
				// Current location cant be obtained
			} else {
				if (isWifiEnabled) {
					mLocationManager.requestLocationUpdates(mLocationManager.NETWORK_PROVIDER, Constants.MIN_TIME_BW_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.i("LocationTracker", "Wifi enabled");

					if (mLocationManager != null) {
						mLocation = mLocationManager.getLastKnownLocation(mLocationManager.NETWORK_PROVIDER);
					}
				}

				if (isGPSEnabled) {
					if (mLocation == null) {
						mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, Constants.MIN_TIME_BW_UPDATES, Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES , this);
						Log.i("LocationTracker", "GPS Enabled");
						if (mLocationManager != null) {
							mLocation = mLocationManager.getLastKnownLocation(mLocationManager.GPS_PROVIDER);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mLocation;
	}
	
	 public void stopTracking(){
	        if(mLocationManager != null){
	        	mLocationManager.removeUpdates(LocationTracker.this);
	        }
	    }

}
