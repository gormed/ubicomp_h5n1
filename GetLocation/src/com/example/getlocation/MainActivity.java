package com.example.getlocation;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{
	TextView displayLong;
	TextView displayLat;
	
	//Runs on app start
	protected void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		displayLong = (TextView)findViewById(R.id.displayLong);
		displayLat = (TextView)findViewById(R.id.displayLat);
		
		enableGPSServices();
	}

	//Enables all GPS Services
	private void enableGPSServices()
	{
		//Gives access to the location service
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
				
		//Creates LocationListener - listens to locationManager
		LocationListener locationListener = new LocationListener()
		{
			//Called when location changed
			public void onLocationChanged(Location location)
			{
				if (location != null)
				{
					Log.i("changedLongitude", "Changed to: " + String.valueOf(location.getLongitude()));
					Log.i("changedLatitude", "Changed to: " + String.valueOf(location.getLatitude()));
				
					double pLong = location.getLongitude();
					double pLat = location.getLatitude();
				
					displayLong.setText(Double.toString(pLong));
					displayLat.setText(Double.toString(pLat));
				}
				
			}

			public void onProviderDisabled(String provider)
			{
				// TODO Auto-generated method stub
				displayLong.setText("In order to dipsplay your coordinates:");
				displayLat.setText("Please enable GPS Service");
			}

			public void onProviderEnabled(String provider)
			{
				// TODO Auto-generated method stub
			}

			public void onStatusChanged(String provider, int status, Bundle extras)
			{
				// TODO Auto-generated method stub
			}
		};
		
		//Register to get location updates - 1000: wait at least 1000ms to request an update, 10=10m
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
	}
	
}
