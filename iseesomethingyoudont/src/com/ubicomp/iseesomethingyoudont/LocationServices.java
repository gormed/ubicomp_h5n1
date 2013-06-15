package com.ubicomp.iseesomethingyoudont;

import com.h5n1.eventsys.EventSystem;
import com.h5n1.eventsys.JsonRequester;
import com.h5n1.eventsys.events.EventState;
import com.h5n1.eventsys.events.GPSEvent;
import com.h5n1.eventsys.events.GPSEvent.GPSEventType;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class LocationServices {
	public static final int ACCURACY_GPS_TIME = 1000;
	public static final float ACCURACY_GPS_LOCATION = 0;
	LocationManager locationManager;
	LocationListener locationListener;
	private GPSEvent updateLocation;
	private String deviceid;

	public LocationServices(LocationManager locationManager) {
		this.deviceid = JsonRequester.getDeviceID();
		updateLocation = new GPSEvent(deviceid, GPSEventType.UPDATE_LOCATION,
				0, 0);
		updateLocation.setState(EventState.NEW_UPDATE_EVENT);
		this.locationManager = locationManager;
		enableGPSServices(locationManager);
	}

	private void enableGPSServices(LocationManager locationManager) {
		// Gives access to the location service
		this.locationManager = locationManager;
		// Creates LocationListener - listens to locationManager
		locationListener = new LocationListener() {
			// Called when location changed
			public void onLocationChanged(Location location) {
				if (location != null) {

					double pLong = location.getLongitude();
					double pLat = location.getLatitude();
					updateLocation.setLa(pLat);
					updateLocation.setLo(pLong);
					EventSystem.pushEvent(updateLocation);

//					Log.i("changedLongitude",
//							"Changed to: "
//									+ String.valueOf(location.getLongitude()));
//					Log.i("changedLatitude",
//							"Changed to: "
//									+ String.valueOf(location.getLatitude()));
					// MainActivity.updateInterface(pLong, pLat);
				}

			}

			public void onProviderDisabled(String provider) {
				EventSystem.pushEvent(new GPSEvent(deviceid,
						GPSEventType.SIGNAL_LOST, 0, 0));
				// TODO Auto-generated method stub
				// displayLong.setText("Turn on GPS!");
			}

			public void onProviderEnabled(String provider) {
				EventSystem.pushEvent(new GPSEvent(deviceid,
						GPSEventType.SIGNAL_FOUND, 0, 0));
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				switch (status) {
				case LocationProvider.OUT_OF_SERVICE:
					EventSystem.pushEvent(new GPSEvent(deviceid,
							GPSEventType.SIGNAL_LOST, 0, 0));
					break;
				case LocationProvider.AVAILABLE:
					EventSystem.pushEvent(new GPSEvent(deviceid,
							GPSEventType.SIGNAL_FOUND, 0, 0));
					break;
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					EventSystem.pushEvent(new GPSEvent(deviceid,
							GPSEventType.NO_SIGNAL, 0, 0));
					break;
				default:
					break;
				}

			}
		};

		// Register to get location updates - 1000: wait at least 1000ms
		// torequest an update, 10=10m
		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_FINE);
		c.setCostAllowed(false);
		c.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
		c.setSpeedRequired(true);
		c.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		c.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

		locationManager.requestLocationUpdates(ACCURACY_GPS_TIME,
				ACCURACY_GPS_LOCATION, c, locationListener, null);
	}

	public void removeUpdates() {
		locationManager.removeUpdates(this.locationListener);

	}
}
