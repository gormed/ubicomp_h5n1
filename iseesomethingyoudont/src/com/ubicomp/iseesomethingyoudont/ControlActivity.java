package com.ubicomp.iseesomethingyoudont;

import java.util.UUID;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import com.h5n1.eventsys.EventSystem;
import com.h5n1.eventsys.JsonRequester;
import com.h5n1.hardwareServices.GestureServices;
import com.h5n1.hardwareServices.HapticalFeedbackServices;
import com.h5n1.hardwareServices.LocationServices;
import com.ubicomp.iseesomethingyoudont.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * @see SystemUiHider
 */

public class ControlActivity extends Activity implements OnTouchListener {

	private GestureDetectorCompat detector = null;
	private static final String DEBUG_TAG = "Gestures";
	private static final int DATA_CHECK_CODE = 0;
	private EventToSpeechSynthesis eventToSpeechSynthesis = null;
	private EventSystem eventSystem;
	private EventHandler eventHandler = null;
	private String deviceId = "42";
	private LocationServices locationServices;
	private HapticalFeedbackServices vibrator;
	private GestureServices gestures;
	private static final boolean TOGGLE_ON_CLICK = false; // set=will toggle systemUI visibility upon interaction. Otherwise will show systemUI visibility upon interaction.
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION; // The flags to pass to {@link SystemUiHider#getInstance}.
	private SystemUiHider mSystemUiHider; // The instance of the {@link SystemUiHider} for this activity.
	private TextView gestureText = null; // DEBUG

	
	
	@Override // Main method of the android application
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		/*final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		gestureText = (TextView) findViewById(R.id.gestureText);

		
		
		

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
		

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		//findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

		// ============================================================================
		// # Own code goes below
		// ============================================================================
		createDeviceId();
		JsonRequester.setDeviceID(deviceId);
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, DATA_CHECK_CODE);*/
		enableHardwareServices();
	}	
	
	
	
	
	public void enableHardwareServices(){
		final View contentView = findViewById(R.id.fullscreen_content);
		// apply a touch listener to the view
		contentView.setOnTouchListener(this);
		
		
		
		
		
		
		
		
		
		
		// Creates a vibrator mechanism
		vibrator = new HapticalFeedbackServices(this);
		// Creates the recognition of gestures -- VERURSACHT NOCH FEHLER WEGEN DER SPEECH SYNTHESIS
		//gestures = new GestureServices(gestureText, vibrator, eventToSpeechSynthesis, this);
		
		// implementation of GestureDetector.OnGestureListener
		//detector = new GestureDetectorCompat(this, gestures);
		// Creates location Services, GPS and WIFI Location
		locationServices = new LocationServices(vibrator, this);
		// Creates event system
		eventSystem = EventSystem.getInstance();
		
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		//startActivityForResult(checkIntent, DATA_CHECK_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				eventToSpeechSynthesis = new EventToSpeechSynthesis(this);
				eventHandler = new EventHandler(eventSystem, eventToSpeechSynthesis);
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}
	
	
	
	
	
	/*public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}*/
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		this.detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	private void createDeviceId() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		deviceId = deviceUuid.toString();
	}

	

	/*class BlindGesturesListener extends GestureDetector.SimpleOnGestureListener {
		private static final String DEBUG_TAG = "Gestures";

		@Override
		public boolean onDown(MotionEvent event) {
			Log.d(DEBUG_TAG, "onDown: " + event.toString());
			gestureText.setText("Down".toCharArray(), 0, "Down".length());
			return true;
		}

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
			Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
			gestureText.setText("Fling".toCharArray(), 0, "Fling".length());
			return true;
		}

		@Override
		public void onLongPress(MotionEvent event) {
			Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
			gestureText.setText("LongPress".toCharArray(), 0, "LongPress".length());
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
			gestureText.setText("Scroll".toCharArray(), 0, "Scroll".length());
			return true;
		}

		@Override
		public void onShowPress(MotionEvent event) {
			Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
			gestureText.setText("ShowPress".toCharArray(), 0, "ShowPress".length());
		}

		@Override
		public boolean onSingleTapUp(MotionEvent event) {
			Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
			gestureText.setText("SingleTapUp".toCharArray(), 0, "SingleTapUp".length());
			// vibrate on tab for specific time
			vibrator.vibrateSpecificTime(500);
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent event) {
			Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
			gestureText.setText("DoubleTap".toCharArray(), 0, "DoubleTap".length());
			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent event) {
			Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
			gestureText.setText("DoubleTapEvent".toCharArray(), 0, "DoubleTapEvent".length());
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent event) {
			Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
			gestureText.setText("SingleTapConfirmed".toCharArray(), 0, "SingleTapConfirmed".length());
			return true;
		}
	}*/

	// ===========================================================================
	// # Automatically Created Content Below
	// ===========================================================================

	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	//////////////////////////////__________________________________________________
	

	

	
	

	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
 	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		//delayedHide(100);
	}
	
	public HapticalFeedbackServices getVibrator(){
		return vibrator;
	}
	
	public EventToSpeechSynthesis getTtsEngine(){
		return eventToSpeechSynthesis;
	}
	
	

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};


}
