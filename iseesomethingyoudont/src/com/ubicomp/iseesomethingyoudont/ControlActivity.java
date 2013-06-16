package com.ubicomp.iseesomethingyoudont;

import java.util.Locale;
import java.util.UUID;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.System;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.view.GestureDetectorCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;
import com.h5n1.eventsys.EventSystem;
import com.h5n1.eventsys.JsonRequester;
import com.h5n1.eventsys.events.EventState;
import com.h5n1.eventsys.events.NavigationEvent;
import com.h5n1.eventsys.events.RFIDEvent;
import com.h5n1.eventsys.events.RFIDEvent.RFIDEventType;
import com.h5n1.hardwareServices.GestureServices;
import com.h5n1.hardwareServices.HapticalFeedbackServices;
import com.h5n1.hardwareServices.LocationServices;
import com.ubicomp.iseesomethingyoudont.util.SystemUiHider;


public class ControlActivity extends Activity implements OnTouchListener, OnInitListener{

	private GestureDetectorCompat detector = null;
	private static final String DEBUG_TAG = "Gestures";
	private static final int DATA_CHECK_CODE = 0;
	private EventToSpeechSynthesis eventToSpeechSynthesis = null;
	private EventSystem eventSystem;
	private EventHandler eventHandler;
	private String deviceId = "42";
	private LocationServices locationServices;
	private HapticalFeedbackServices vibrator;
	private GestureServices gestures;
	private static final boolean TOGGLE_ON_CLICK = false; // set=will toggle systemUI visibility upon interaction. Otherwise will show systemUI visibility upon interaction.
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION; // The flags to pass to {@link SystemUiHider#getInstance}.
	private SystemUiHider mSystemUiHider; // The instance of the {@link SystemUiHider} for this activity.
	private TextView gestureText = null; // DEBUG
	private AudioManager audioManager;
	private ConnectivityManager connectivityManager;
	private NetworkInfo networkInfo;

	// Called when app is created (not when displayed)
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		gestureText = (TextView) findViewById(R.id.gestureText);
		
		// Creates a unique device id
		createDeviceId();
		// initialises the json requester
		JsonRequester.setDeviceID(deviceId);
		
		// WENN DEAKTIVIERT, ERKENNT ER ALLE GESTEN ALS LONG PRESS
		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {} 
				else {}}});
				
		// Check if speak engine is installed??
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, DATA_CHECK_CODE);
	}	
	
	// Called when a new thing? is initiated, here: ttsengine
	@Override
	public void onInit(int status) {
		eventToSpeechSynthesis.getTtsengine().setLanguage(Locale.GERMAN);
		enableHardwareServices();
		checkRequired();
	}
	
	// Called when app is enden (not killed)
	@Override
	public void onStop(){
		
	}
	
	// Called when app is minimized
	@Override
	public void onPause(){
		super.onPause();//
		// Wenn man den kill auﬂerhalb des if macht, wird die app sofort bei start gekillt
		if(eventToSpeechSynthesis != null ){
			eventToSpeechSynthesis.stopSpeaking();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
	
	// Creates the option menu
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Tries to find the ttsengine, if not installed, install it
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// ttesengine is  installed, create the TTS instance
				eventToSpeechSynthesis = new EventToSpeechSynthesis(this);
			} else {
				// ttsengine is not installed, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	// Calls method when specific menu item is pressed
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.gpsMenu:
	            showTest("GPS");
	            return true;
	        case R.id.rfidMenu:
	        	createTestRFID();
	        	showTest("RFID");
	        	return true;
	        case R.id.obstacleMenu:
	        	showTest("OBSTACLE");
	        	return true;
	        case R.id.exitMenu:
	        	//finish();
	        	// Better: use finish() (ends the app, doesnt kill it)
	        	android.os.Process.killProcess(android.os.Process.myPid());
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	// Applys a touch detector
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		this.detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	// Initialises all Hardware Services
	public void enableHardwareServices(){
		final View contentView = findViewById(R.id.fullscreen_content);
		// apply a touch listener to the view
		contentView.setOnTouchListener(this);
		// Creates a vibrator mechanism
		vibrator = new HapticalFeedbackServices(this);
		// Creates the recognition of gestures -- VERURSACHT NOCH FEHLER WEGEN DER SPEECH SYNTHESIS
		gestures = new GestureServices(gestureText, vibrator, eventToSpeechSynthesis, this);
		// implementation of GestureDetector.OnGestureListener
		detector = new GestureDetectorCompat(this, gestures);
		// Creates location Services, GPS and WIFI Location
		locationServices = new LocationServices(vibrator, eventToSpeechSynthesis, this);
		// Creates Audiomanager, gives access to audio settings
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		// Creates cmanager, gives access to network status
		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// Gives access to the network info
		networkInfo = connectivityManager.getActiveNetworkInfo();
		// Creates event system
		eventSystem = EventSystem.getInstance();
		// Creates the event handler
		eventHandler = new EventHandler(eventSystem, eventToSpeechSynthesis);
	}
	
	// Creates a unique device id
	private void createDeviceId() {
		// Creates access to telephony systems
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		// Creates device id
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		deviceId = deviceUuid.toString();
	}

	// Creates a test RFID event
	public void createTestRFID() {
		// breite, hˆhe, tiefe
		float[] size = { 40, 160, 60 };
		float mass = 100;
		//RFIDEvent event = new RFIDEvent(JsonRequester.getDeviceID(), RFIDEventType.NEW_TAG, "Opa", size, mass);
		// Sets an optional eventState
		//event.setState(EventState.);
	//	EventSystem.pushEvent(event);
	}
	
	// Checks if all required objects are available, kills if not
	private void checkRequired(){
		if(audioManager.isWiredHeadsetOn() && locationServices.checkGPSOn() && networkInfo.isConnectedOrConnecting()){
		} else {
			//eventToSpeechSynthesis.stopSpeaking();
			//eventToSpeechSynthesis.speakTest("Ihr Ger‰t unterst¸tzt die benˆtigten Funktionen nicht");
			//android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
	
	// Displays a test ghost message
	public void showTest(String text){
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(this.getApplicationContext(), text, duration);
		toast.show();
	}
	

	

}
