package com.h5n1.hardwareServices;

import java.util.Calendar;
import java.util.Date;

import com.ubicomp.iseesomethingyoudont.EventToSpeechSynthesis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class GestureServices extends GestureDetector.SimpleOnGestureListener {
	private static final String DEBUG_TAG = "Gestures";
	private TextView gestureText;
	private HapticalFeedbackServices vibrator;
	private Activity activity;
	private EventToSpeechSynthesis ttsengine;
	private Display display;
	private long lastTime;
	private long currentTime;
	private int sosCounter = 0;
	private float e1X;
	private float e1Y;
	private float e2X;
	private float e2Y;
	private float screenX;
	private float screenY;

	public GestureServices(TextView gestureText, HapticalFeedbackServices vibrator, EventToSpeechSynthesis eventToSpeechSynthesis, Display display, Activity activity) {
		this.vibrator = vibrator;
		this.gestureText = gestureText;
		this.activity = activity;
		this.display = display;
		ttsengine = eventToSpeechSynthesis;
		screenX = display.getWidth();
		screenY = display.getHeight();
		lastTime = System.currentTimeMillis();
	}

	@Override
	public boolean onDown(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDown: " + event.toString());
		gestureText.setText("Down".toCharArray(), 0, "Down".length());
		// vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		//Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
		gestureText.setText("LongPress".toCharArray(), 0, "LongPress".length());
		//vibrator.vibrateSpecificTime(500);
		// Gets the currents system time
		currentTime = System.currentTimeMillis();
		// if difference current/last smaller than mseconds second, do this
		if (currentTime - lastTime < 3000){
			sosCounter++;
			if(sosCounter >= 3){
				sosCounter = 0;
				// Call SOS method here!!!
				ttsengine.stopSpeaking();
				ttsengine.speakTest("Ein S O S Signal wird gesendet");
			} else { }
		} else {
			sosCounter = 0;
		}
		lastTime = currentTime;
		Log.i("HATATATATATA", Integer.toString(sosCounter));
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
		gestureText.setText("Scroll".toCharArray(), 0, "Scroll".length());
		// vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		// Get data to check finger position
		e1X = event1.getX();
		e1Y = event1.getY();
		e2X = event2.getX();
		e2Y = event2.getY();
		
		// Checks, if close-gesture aczivated, Checks if e1 is in bottom left corner
		if((0 <= e1X && e1X <= screenX*0.1) && (screenY*0.90 <= e1Y && e1Y <= screenY)){
			// Checks if in upper right corner
			if((screenX*0.9 <= e2X && e2X <= screenX) && (0 <= e2Y && e2Y <= screenY*0.1)){
				vibrator.vibrateSpecificTime(5000);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		
		Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
		gestureText.setText("Fling".toCharArray(), 0, "Fling".length());
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
//		gestureText.setText("SingleTapUp".toCharArray(), 0,
//				"SingleTapUp".length());
		// vibrator.vibrateSpecificTime(100);
		return true;
	}


	public boolean onDoubleTap(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
		gestureText.setText("DoubleTap".toCharArray(), 0, "DoubleTap".length());
		// vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
		gestureText.setText("DoubleTapEvent".toCharArray(), 0,
				"DoubleTapEvent".length());
		vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
		gestureText.setText("SingleTapConfirmed".toCharArray(), 0, "SingleTapConfirmed".length());
		vibrator.vibrateSpecificTime(100);
		ttsengine.stopSpeaking();
		ttsengine.speakTest("Ein Druck");
		return true;
	}

	// Anzeige nur zu DEBUG ZWECKEN!!
	private void showToast(String text){
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(activity.getApplicationContext(), text, duration);
		toast.show();
	}
}
