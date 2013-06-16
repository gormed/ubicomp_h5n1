package com.ubicomp.iseesomethingyoudont;

import java.util.Dictionary;
import java.util.Hashtable;

import android.speech.tts.TextToSpeech;

import com.h5n1.eventsys.events.NavigationEvent;
import com.h5n1.eventsys.events.NavigationEvent.NavigationEventType;

public class EventToSpeechSynthesis {

	private Dictionary<String, String> eventSymbols;
	private TextToSpeech ttsEngine;

	public EventToSpeechSynthesis(ControlActivity activity) {
		ttsEngine = new TextToSpeech(activity, activity);
		eventSymbols = new Hashtable<String, String>();
		fillNavigationDictionary();
	}

	private void fillNavigationDictionary() {
		String[] navigation = { "kleines Objekt", "mittleres Objekt",
				"goßes Objekt", "Mensch", "Straße", "Wand", "Tür", "Auto",
				"Motorad", "Fahrrad", "Mysterium" };
		int i = 0;
		for (NavigationEventType evt : NavigationEventType.values()) {
			eventSymbols.put(evt.name(), navigation[i++]);
		}
	}

	public void speakNavigaionEvent(NavigationEvent event) {
		if (event.getType() != NavigationEventType.OBSTACLE_CUSTOM)
			ttsEngine.speak(
					"Achtung, " + eventSymbols.get(event.getType().name())
							+ ", " + event.getData()[0] + " Meter vorraus!",
					TextToSpeech.QUEUE_FLUSH, null);
		else {
			ttsEngine.speak(
					"Achtung, " + event.getContent() + ", "
							+ event.getData()[0] + " Meter vorraus!",
					TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	public void speakTest(String text) {
		ttsEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	TextToSpeech getTtsengine() {
		return ttsEngine;
	}
}
