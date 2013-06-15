package com.h5n1.eventsys.events;

import com.h5n1.eventsys.events.Event;
// 2) GPS-Events

// Signal gefunden
// Server Anfrage
// Kein Signal erreichbar
// GPS Update
// Geraet bewegt sich
// Server push (Aenderung)
// Geraet steht

public class GPSEvent extends Event<GPSEvent.GPSEventType> {

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

	private double lo,la;
	private GPSEventType type;

	public GPSEvent(String json) {
		
	}

	public GPSEvent(GPSEventType type, double lo, double la) {
		this.type = type;
		this. lo = lo;
		this.la = la;
	}

	public String toJsonString() {
		return lo + "," + la;
	}

	public GPSEventType getType() {
		return type;
	}
}