package com.example.getlocation;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView displayLong;
	TextView displayLat;
	LocationManager locationManager;
	LocationListener locationListener;
	LocationServices locationServices;
	JsonHandler jHandler;

	// Runs on when application starts 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		displayLong = (TextView) findViewById(R.id.displayLong);
		displayLat = (TextView) findViewById(R.id.displayLat);
		
		jHandler = new JsonHandler();
		locationServices = new LocationServices((LocationManager) this.getSystemService(Context.LOCATION_SERVICE), displayLong, displayLat);
	}

	// Called when app is minimized
	protected void onPause() {
		super.onPause();
	}

	// Called when app is stopped
	protected void onStop() {
		super.onStop();
		locationServices.removeUpdates();
	}
	
	public void serverTest(View view) {
	 }
}
