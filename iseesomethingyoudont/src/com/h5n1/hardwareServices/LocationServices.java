package com.h5n1.hardwareServices;

import com.h5n1.eventsys.EventSystem;
import com.h5n1.eventsys.JsonRequester;
import com.h5n1.eventsys.events.EventState;
import com.h5n1.eventsys.events.GPSEvent;
import com.h5n1.eventsys.events.GPSEvent.GPSEventType;
import com.ubicomp.iseesomethingyoudont.ControlActivity;
import com.ubicomp.iseesomethingyoudont.EventToSpeechSynthesis;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationServices {
	public static final int ACCURACY_GPS_TIME = 1000;
	public static final float ACCURACY_GPS_LOCATION = 0;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private GPSEvent updateLocation;
	private String deviceid;
	private EventToSpeechSynthesis ttsengine;

	// Creates all location services
	public LocationServices(HapticalFeedbackServices vibrator, EventToSpeechSynthesis ttsengine, Activity activity) {
		this.deviceid = JsonRequester.getDeviceID();
		this.ttsengine = ttsengine;
		updateLocation = new GPSEvent(deviceid, GPSEventType.UPDATE_LOCATION, 0, 0);
		updateLocation.setState(EventState.NEW_UPDATE_EVENT);
		// Creates the locationManager within the application context
		locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		enableGPSServices(locationManager);
		
		// Check if gps connection or location per wifi is enabled
	    if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        long[] pattern = {200,100,200,100,500};
	    	vibrator.vibrateSpecificTime(200);
	    	showToast(activity, "GPS ist aus!");
	    	ttsengine.stopSpeaking();
	    	ttsengine.speakTest("Achtung: GPS ist deaktiviert");
	    } 
	}
	
	// Gives acces to all gps services
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
				}
			}

			public void onProviderDisabled(String provider) {
				EventSystem.pushEvent(new GPSEvent(deviceid, GPSEventType.SIGNAL_LOST, 0, 0));
				// TODO Auto-generated method stub
				// displayLong.setText("Turn on GPS!");
			}

			public void onProviderEnabled(String provider) {
				EventSystem.pushEvent(new GPSEvent(deviceid, GPSEventType.SIGNAL_FOUND, 0, 0));
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {
				switch (status) {
					case LocationProvider.OUT_OF_SERVICE:
						EventSystem.pushEvent(new GPSEvent(deviceid, GPSEventType.SIGNAL_LOST, 0, 0));
						ttsengine.speakTest("Achtung: GPS Verbindung verloren. Stehen bleiben");
						break;
					case LocationProvider.AVAILABLE:
						EventSystem.pushEvent(new GPSEvent(deviceid, GPSEventType.SIGNAL_FOUND, 0, 0));
						ttsengine.speakTest("GPS Verbindung wieder hergestellt");
						break;
					case LocationProvider.TEMPORARILY_UNAVAILABLE:
						EventSystem.pushEvent(new GPSEvent(deviceid, GPSEventType.NO_SIGNAL, 0, 0));
						ttsengine.speakTest("Achtung: GPS Verbindung verloren. Stehen bleiben");
						break;
					default:
						break;
				}
			}
		};

		// Register to get location updates - 1000: wait at least 1000ms, torequest an update, 10=10m
		locationManager.requestLocationUpdates(ACCURACY_GPS_TIME, ACCURACY_GPS_LOCATION, createCriteria(), locationListener, null);
	}
	
	// Creates criteria for location updates
	private Criteria createCriteria(){
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setSpeedRequired(true);
		criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
		return criteria;
	}

	// Removes all Updates
	public void removeUpdates() {
		locationManager.removeUpdates(this.locationListener);
	}

	// Anzeige nur zu DEBUG ZWECKEN!!
	private void showToast(Activity activity, String text){
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(activity.getApplicationContext(), text, duration);
		toast.show();
	}

	public GPSEvent getUpdateLocation() {
		return updateLocation;
	}
	
}
