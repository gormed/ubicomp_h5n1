package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.JsonRequester;
import com.h5n1.eventsys.events.Event;
// 4) Betreuer-Events

// Blinder in Gefahr
// Positions initialisierung
// Positions Abgleich
// Blinder auf Idle
// Tag hinzugef�gt/entfernt
// Blinder in Bewegung
// Signal verloren
// Signal gefunden

public class CompanionEvent extends Event<CompanionEvent.CompanionEventType> {

	public enum CompanionEventType {
		DANGER_SITUATION, IDLE_SITUATION, MOVING_SITUATION, INIT_POSITION, EXCHANGE_POSITION, TAG_ADDED, FOUND_SIGNAL, LOST_SIGNAL
	}

	private CompanionEventType type;
	private String message;

	public CompanionEvent(String deviceid, CompanionEventType type, JSONObject json) {
		super();
		this.deviceId = deviceid;
		this.type = type;
		try {
			setReceiverId(json.getString(JsonRequester.TAG_RECEIVERID));
			setEventId(json.getInt(JsonRequester.TAG_EVENTID));
			JSONObject content = new JSONObject(json.getString(JsonRequester.TAG_CONTENT));
			this.message = content.getString("message");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public CompanionEvent(String deviceid, CompanionEventType type,
			String message) {
		super();
		this.message = message;
		this.type = type;
		this.deviceId = deviceid;
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		try {
			obj.accumulate("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

	public CompanionEventType getType() {
		return type;
	}
}