package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.JsonRequester;
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
	private double[] size;
	private double mass;
	
	private double longitude;
	private double latitude;

	public RFIDEvent(String deviceid, RFIDEventType type, JSONObject json) {
		super();
		this.deviceId = deviceid;
		this.type = type;
		
		try {
			setReceiverId(json.getString(JsonRequester.TAG_RECEIVERID));
			setEventId(json.getInt(JsonRequester.TAG_EVENTID));
			JSONObject content = new JSONObject(json.getString(JsonRequester.TAG_CONTENT));
			int length = content.getInt("length");
			size = new double[length];
			for (int i = 0; i < length; i++) {
				double f = content.getDouble(""+i);
				size[i] = f;
			}
			this.mass = content.getDouble("mass");
			this.longitude = content.getDouble("longitude");
			this.latitude = content.getDouble("latitude");

			this.name = content.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public RFIDEvent(String deviceid, RFIDEventType type, String name, double[] size, double mass, double longitude, double latitude) {
		this.type = type;
		this.name = name;
		this.mass = mass;
		this.size = size;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public RFIDEventType getType() {
		return type;
	}

	public double getMass() {
		return mass;
	}
	
	public String getName() {
		return name;
	}
	
	public double[] getSize() {
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
			obj.accumulate("longitude", longitude);
			obj.accumulate("latitude", latitude);
			obj.accumulate("name", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
}