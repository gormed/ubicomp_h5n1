package com.example.getlocation;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView displayLong;
	TextView displayLat;
	LocationManager locationManager;
	LocationListener locationListener;
	LocationServices locationServices;
	EventHandler eHandler;

	// Runs on when application starts 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		displayLong = (TextView) findViewById(R.id.displayLong);
		displayLat = (TextView) findViewById(R.id.displayLat);
		
		eHandler = new EventHandler();
		locationServices = new LocationServices((LocationManager) this.getSystemService(Context.LOCATION_SERVICE), displayLong, displayLat);
	}

	// Called when app is minimized
	protected void onPause() {
		super.onPause();
	}

	// Called when app is stopped
	protected void onStop() {
		super.onStop();
		//locationManager.removeUpdates(locationListener);
		locationServices.removeUpdates((LocationManager) this.getSystemService(Context.LOCATION_SERVICE), locationListener);
	}

	// Display an info dialog
	private void showDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Reset...");
		alertDialog.setMessage("Are you sure?");
		alertDialog.show();
	}
}
