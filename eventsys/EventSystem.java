package com.h5n1.eventsys;

import com.h5n1.eventsys.events.*;
import java.lang.Integer;
import java.util.HashMap;

public class EventSystem {
	private static EventSystem instance;
	private EventSystem() {

	}

	public static EventSystem getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new EventSystem();
	}

	// =================================================

	private HashMap<Integer, Event> incomingEvents = new HashMap<Integer, Event> ();
	private HashMap<Integer, Event> outgoingEvents = new HashMap<Integer, Event> ();
}