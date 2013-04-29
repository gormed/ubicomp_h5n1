package com.example.getlocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationServices {
	LocationManager locationManager;
	LocationListener locationListener;
	
	public LocationServices(LocationManager locationManager, TextView displayLong, TextView displayLat){
		enableGPSServices(locationManager, displayLong, displayLat);
	}

	private void enableGPSServices(LocationManager locationManager, final TextView displayLong, final TextView displayLat) {
		// Gives access to the location service
		this.locationManager = locationManager;
		// Creates LocationListener - listens to locationManager
		locationListener = new LocationListener() {
			// Called when location changed
			public void onLocationChanged(Location location) {
				if (location != null) {
					double pLong = location.getLongitude();
					double pLat = location.getLatitude();
					displayLong.setText(Double.toString(pLong));
					displayLat.setText(Double.toString(pLat));
					Log.i("changedLongitude","Changed to: "+ String.valueOf(location.getLongitude()));
					Log.i("changedLatitude","Changed to: "+ String.valueOf(location.getLatitude()));
				}

			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				displayLong.setText("In order to dipsplay your coordinates:");
				displayLat.setText("Please enable GPS Service");
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
			}
		};

		// Register to get location updates - 1000: wait at least 1000ms torequest an update, 10=10m
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 10, locationListener);
	}
	
	public void removeUpdates(LocationManager locationManager, LocationListener locationListener)
	{
		locationManager.removeUpdates(locationListener);
		
	}
}
