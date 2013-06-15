package com.h5n1.eventsys.events;

import com.h5n1.eventsys.events.Event;
// 1) RFID-Events

// TAG ist in der Naehe

// -> TAG entdeckt
// -> TAG verbleibt
// -> TAG verlässt/ entfernt

public class RFIDEvent extends Event<RFIDEvent.RFIDEventType> {

	public enum RFIDEventType {
		NEW_TAG,
		UPDATE_TAG,
		REMOVE_TAG	
	}

	private RFIDEventType type;
	private String data;

	public RFIDEvent(String json) {
		
	}

	public RFIDEvent(RFIDEventType type, String data) {
		this.type = type;
		this.data = data;
	}

	public RFIDEventType getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	public String toJsonString() {
		return this.data;
	}
}