package com.ubicomp.iseesomethingyoudont;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class EventToSpeechSynthesis implements OnInitListener {
	
	private Dictionary<String, String[]> eventSymbols;
	private TextToSpeech ttsEngine;
	public EventToSpeechSynthesis(ControlActivity activity) {
		ttsEngine = new TextToSpeech(activity, this);
		eventSymbols = new Hashtable<String, String[]>();
	}

	@Override
	public void onInit(int arg0) {
		ttsEngine.setLanguage(Locale.GERMAN);
	}
	
	public TextToSpeech getTtsEngine() {
		return ttsEngine;
	}
}
