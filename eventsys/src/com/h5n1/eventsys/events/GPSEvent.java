package com.h5n1.eventsys.events;

import com.h5n1.eventsys.events.Event;
// 2) GPS-Events

// Signal gefunden
// Server Anfrage
// Kein Signal erreichbar
// GPS Update
// Gerät bewegt sich
// Server push (änderung)
// Gerät steht

public class GPSEvent extends Event {

	public enum GPSEventType {
		SIGNAL_FOUND,
		NO_SIGNAL,
		SIGNAL_LOST,
		REQUEST_LOCATION,
		PUSH_LOCATION,
		UPDATE_LOCATION,
		MOVEMENT_STARTED,
		MOVEMENT_STOPPED
	}

	private float lo,la;
	private GPSEventType type;

	public GPSEvent(GPSEventType type, float lo, float la) {
		this.type = type;
		this. lo = lo;
		this.la = la;
	}

	public String toJsonString() {
		return getEventId() + ",GPS," + this.type + "," + lo + "," + la;
	}
}