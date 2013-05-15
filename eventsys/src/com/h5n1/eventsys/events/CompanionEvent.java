package com.h5n1.eventsys.events;

import com.h5n1.eventsys.events.Event;
// 4) Betreuer-Events

// Blinder in Gefahr
// Positions initialisierung
// Positions Abgleich
// Blinder auf Idle
// Tag hinzugefügt/entfernt
// Blinder in Bewegung
// Signal verloren
// Signal gefunden

public class CompanionEvent extends Event<CompanionEvent.CompanionEventType> {

	public enum CompanionEventType {
		DANGER_SITUATION,
		IDLE_SITUATION,
		MOVING_SITUATION,
		INIT_POSITION,
		EXCHANGE_POSITION,
		TAG_ADDED,
		FOUND_SIGNAL,
		LOST_SIGNAL
	}

	private CompanionEventType type;
	private String message;

	public CompanionEvent(String json) {
		String[] split = json.split("[,]");
		
		for (String s : split) {

		}
	}

	public CompanionEvent(CompanionEventType type, String message) {
		this.message = message;
		this.type = type;
	}

	public String toJsonString() {
		return message;
	}

	public CompanionEventType getType() {
		return type;
	}
}