package com.example.iseesomethingyoudont;

import com.example.iseesomethingyoudont.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ControlActivity extends Activity implements OnTouchListener {

	private GestureDetectorCompat detector;
	private static final String DEBUG_TAG = "Gestures";

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// this.detector.onTouchEvent(event);
		//
		// int action = MotionEventCompat.getActionMasked(event);
		//
		// switch (action) {
		// case (MotionEvent.ACTION_DOWN):
		// Log.d(DEBUG_TAG, "Action was DOWN");
		// return true;
		// case (MotionEvent.ACTION_MOVE):
		// Log.d(DEBUG_TAG, "Action was MOVE");
		// return true;
		// case (MotionEvent.ACTION_UP):
		// Log.d(DEBUG_TAG, "Action was UP");
		// return true;
		// case (MotionEvent.ACTION_CANCEL):
		// Log.d(DEBUG_TAG, "Action was CANCEL");
		// return true;
		// case (MotionEvent.ACTION_OUTSIDE):
		// Log.d(DEBUG_TAG, "Movement occurred outside bounds "
		// + "of current screen element");
		// return true;
		// default:
		// // Be sure to call the superclass implementation
		// return super.onTouchEvent(event);
		// }
		return false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		this.detector.onTouchEvent(event);

		int action = MotionEventCompat.getActionMasked(event);

		switch (action) {
		case (MotionEvent.ACTION_DOWN):
			Log.d(DEBUG_TAG, "Action was DOWN");
			return true;
		case (MotionEvent.ACTION_MOVE):
			Log.d(DEBUG_TAG, "Action was MOVE");
			return true;
		case (MotionEvent.ACTION_UP):
			Log.d(DEBUG_TAG, "Action was UP");
			return true;
		case (MotionEvent.ACTION_CANCEL):
			Log.d(DEBUG_TAG, "Action was CANCEL");
			return true;
		case (MotionEvent.ACTION_OUTSIDE):
			Log.d(DEBUG_TAG, "Movement occurred outside bounds "
					+ "of current screen element");
			return true;
		default:
			// Be sure to call the superclass implementation
			return super.onTouchEvent(event);
		}
	}

	class BlindGesturesListener extends GestureDetector.SimpleOnGestureListener {
		private static final String DEBUG_TAG = "Gestures";
		final TextView gestureText = (TextView) findViewById(R.id.gestureText);
		
	    @Override
	    public boolean onDown(MotionEvent event) { 
	        Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
	        gestureText.setText("Down".toCharArray(), 0, "Down".length());
	        return true;
	    }

	    @Override
	    public boolean onFling(MotionEvent event1, MotionEvent event2, 
	            float velocityX, float velocityY) {
	        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
	        gestureText.setText("Fling".toCharArray(), 0, "Fling".length());
	        return true;
	    }

	    @Override
	    public void onLongPress(MotionEvent event) {
	        Log.d(DEBUG_TAG, "onLongPress: " + event.toString()); 
	        gestureText.setText("LongPress".toCharArray(), 0, "LongPress".length());
	    }

	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	            float distanceY) {
	        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
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
	}

	// ===========================================================================
	// # Automatically Created Content Below
	// ===========================================================================

	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 15000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = false;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_control);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

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
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);

		// ============================================================================
		// # Own code goes below
		// ============================================================================

		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		contentView.setOnTouchListener(this);
		detector = new GestureDetectorCompat(this, new BlindGesturesListener());
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
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

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

}
