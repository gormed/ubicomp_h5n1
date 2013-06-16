package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.events.Event;
// 1) RFID-Events

// TAG ist in der Naehe

// -> TAG entdeckt
// -> TAG verbleibt
// -> TAG verlässt/ entfernt

public class RFIDEvent extends Event<RFIDEvent.RFIDEventType> {

	public enum RFIDEventType {
		NEW_TAG, UPDATE_TAG, REMOVE_TAG
	}

	private RFIDEventType type;
	private String name;
	private float[] size;
	private float mass;

	public RFIDEvent(String deviceid, JSONObject json) {
		super();
		this.deviceId = deviceid;

	}

	public RFIDEvent(String deviceid, RFIDEventType type, String name, float[] size, float mass) {
		this.type = type;
		this.name = name;
		this.mass = mass;
		this.size = size;
	}

	public RFIDEventType getType() {
		return type;
	}

	public float getMass() {
		return mass;
	}
	
	public String getName() {
		return name;
	}
	
	public float[] getSize() {
		return size;
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		try {
			for (int i = 0; i < size.length; i++) {
				obj.accumulate(i+"", size[i]);
			}
			obj.accumulate("length", size.length);
			obj.accumulate("mass", mass);
			obj.accumulate("name", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
}