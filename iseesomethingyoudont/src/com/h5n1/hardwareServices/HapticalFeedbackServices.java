package com.h5n1.hardwareServices;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import com.ubicomp.iseesomethingyoudont.*;

public class HapticalFeedbackServices {

	private Vibrator vibrator;

	// Creates a vibrator
	public HapticalFeedbackServices(Context context) {
		// Enable Vibrator
		vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
	}

	// Vibrate for msecons = time in milliseconds
	public void vibrateSpecificTime(int mseconds) {
		vibrator.cancel();
		vibrator.vibrate(mseconds);
	}

	public void vibratePattern(long[] pattern, int repeat) {
		// repeat = -1: vibrate pattern once, pattern=long[], vibrate, pause, vibrate..
		vibrator.cancel();
		vibrator.vibrate(pattern, repeat);
	}

	public void vibratePattern(FeedbackPattern pattern) {
		switch (pattern) {
		case PATTERN_ONE:
			vibrateSpecificTime(100);
			break;
		case PATTERN_TWO:
			long[] p = {100, 100, 100};
			vibratePattern(p, -1);
			break;
		default:
			break;
		}
	}
}
