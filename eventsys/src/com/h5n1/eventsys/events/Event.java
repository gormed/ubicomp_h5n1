package com.h5n1.eventsys.events;

// 1) RFID-Events
// 2) GPS-Events [ (1+2) Blinden-Events ]
// 3) Bewegungs-Events
// 4) Betreuer-Events

public abstract class Event {

	private static long uniqueCounter = 0;
	private static long getUniqueId() { return uniqueCounter++; }

	private long id = getUniqueId();

	public Event() {

	}

	public abstract String toJsonString();

	public long getEventId() {
		return id;
	}
}