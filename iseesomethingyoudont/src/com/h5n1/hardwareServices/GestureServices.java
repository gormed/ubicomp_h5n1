package com.h5n1.hardwareServices;

import com.ubicomp.iseesomethingyoudont.EventToSpeechSynthesis;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class GestureServices extends GestureDetector.SimpleOnGestureListener {
	private static final String DEBUG_TAG = "Gestures";
	private TextView gestureText;
	private HapticalFeedbackServices vibrator;
	private Activity activity;
	private TextToSpeech ttsengine;

	public GestureServices(TextView gestureText, HapticalFeedbackServices vibrator, EventToSpeechSynthesis eventToSpeechSynthesis, Activity activity) {
		this.vibrator = vibrator;
		this.gestureText = gestureText;
		this.activity = activity;
		ttsengine = eventToSpeechSynthesis.getTtsEngine();
	}
	
	// Anzeige nur zu DEBUG ZWECKEN!!
	private void showToast(String text){
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(activity.getApplicationContext(), text, duration);
		toast.show();
	}

	@Override
	public boolean onDown(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDown: " + event.toString());
		gestureText.setText("Down".toCharArray(), 0, "Down".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
		Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
		gestureText.setText("Fling".toCharArray(), 0, "Fling".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		gestureText.setText("LongPress".toCharArray(), 0, "LongPress".length());
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
		gestureText.setText("Scroll".toCharArray(), 0, "Scroll".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent event) {
		Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
		gestureText.setText("ShowPress".toCharArray(), 0, "ShowPress".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
		gestureText.setText("SingleTapUp".toCharArray(), 0, "SingleTapUp".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		return true;
	}

	public boolean onDoubleTap(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
		gestureText.setText("DoubleTap".toCharArray(), 0, "DoubleTap".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
		gestureText.setText("DoubleTapEvent".toCharArray(), 0, "DoubleTapEvent".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
		gestureText.setText("SingleTapConfirmed".toCharArray(), 0, "SingleTapConfirmed".length());
		// long[] pattern = {100,100,100};
		vibrator.vibrateSpecificTime(300);
		// vibrator.vibratePattern(pattern, -1);
		//ttsengine.speak("hallo", TextToSpeech.QUEUE_FLUSH, null);
		return true;
	}
}
