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
	private long lastLongPress;
	private long currentLongPress;
	private int sosCounter = 0;


	// creates gesture services
	public GestureServices(TextView gestureText, HapticalFeedbackServices vibrator, EventToSpeechSynthesis eventToSpeechSynthesis, Display display, Activity activity) {
		this.vibrator = vibrator;
		this.gestureText = gestureText;
		this.activity = activity;
		this.display = display;
		ttsengine = eventToSpeechSynthesis;
		lastLongPress = System.currentTimeMillis();
	}
	

	@Override
	public boolean onDown(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDown: " + event.toString());
		gestureText.setText("Down".toCharArray(), 0, "Down".length());
		//vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
		gestureText.setText("LongPress".toCharArray(), 0, "LongPress".length());
		//vibrator.vibrateSpecificTime(500);
		
		currentLongPress = System.currentTimeMillis();
		//Log.i("HATATATATATA", "current" + Long.toString(currentLongPress));
		//Log.i("HATATATATATA", "last" + Long.toString(lastLongPress));
		//Log.i("HATATATATATA", "difference" + Long.toString(currentLongPress - lastLongPress));
		//Log.i("HATATATATATA", "1");
		// if difference current/last smaller than 1 second, do this
		if (currentLongPress - lastLongPress < 5000){
			sosCounter++;
			Log.i("HATATATATATA", "current" + Long.toString(currentLongPress));
			Log.i("HATATATATATA", "last" + Long.toString(lastLongPress));
			Log.i("HATATATATATA", "difference" + Long.toString(currentLongPress - lastLongPress));
			if(sosCounter >= 3){
				// Starte den Sos Ruf
				Log.i("HATATATATATA", "3");
				showToast("Sie haben einen SOS ruf gestartet");
				sosCounter = 0;
			}
			Log.i("HATATATATATA", "4");
			sosCounter = 0;
			
			showToast("Counter zurück gesetzt");
		} else {
			
		}
		
		lastLongPress = currentLongPress;
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
		gestureText.setText("Scroll".toCharArray(), 0, "Scroll".length());
		//vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		// Get data to check finger position
		float e1X = event1.getX();
		float e1Y = event1.getY();
		float e2X = event2.getX();
		float e2Y = event2.getY();
		float screenX = display.getWidth();
		float screenY = display.getHeight();
		
		// Checks, if close-gesture aczivated, Checks if e1 is in bottom left corner
		if((0 <= e1X && e1X <= screenX*0.1) && (screenY*0.90 <= e1Y && e1Y <= screenY)){
			// Checks if in upper right corner
			if((screenX*0.9 <= e2X && e2X <= screenX) && (0 <= e2Y && e2Y <= screenY*0.1)){
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
		gestureText.setText("SingleTapUp".toCharArray(), 0, "SingleTapUp".length());
		//vibrator.vibrateSpecificTime(100);
		return true;
	}

	public boolean onDoubleTap(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
		gestureText.setText("DoubleTap".toCharArray(), 0, "DoubleTap".length());
		//vibrator.vibrateSpecificTime(100);
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
		gestureText.setText("DoubleTapEvent".toCharArray(), 0, "DoubleTapEvent".length());
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
	
	private void endAppAction(){
		
	}
	
}
