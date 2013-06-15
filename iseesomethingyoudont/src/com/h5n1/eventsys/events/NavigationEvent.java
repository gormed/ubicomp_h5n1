package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.JsonRequester;
import com.h5n1.eventsys.events.Event;
// 2) GPS-Events
import com.h5n1.eventsys.events.GPSEvent.GPSEventType;

// Signal gefunden
// Server Anfrage
// Kein Signal erreichbar
// GPS Update
// Geraet bewegt sich
// Server push (Aenderung)
// Geraet steht

public class NavigationEvent extends Event<NavigationEvent.NavigationEventType> {

	public enum NavigationEventType {
		OBSTACLE_SMALL, OBSTACLE_MEDIUM, OBSTACLE_BIG, OBSTACLE_HUMAN, OBSTACLE_STREET, OBSTACLE_WALL, OBSTACLE_DOOR, OBSTACLE_CAR, OBSTACLE_BIKE
	}

	private NavigationEventType type;
	private double[] data;

	public NavigationEvent(String deviceid, JSONObject json) {
		super();
		this.deviceId = deviceid;
		try {
			setReceiverId(json.getString(JsonRequester.TAG_RECEIVERID));
			setEventId(json.getInt(JsonRequester.TAG_EVENTID));
			String type = json.getString(JsonRequester.TAG_TYPE);
			String[] split = type.split("[-]");
			String eventType = split[1];
			this.type = NavigationEventType.valueOf(eventType);
			JSONObject content = new JSONObject(json.getString(JsonRequester.TAG_CONTENT));
			int length = content.getInt("length");
			data = new double[length];
			for (int i = 0; i < length; i++) {
				double f = content.getDouble(""+i);
				data[i] = f;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NavigationEvent(String deviceid, NavigationEventType type,
			double[] data) {
		super();
		this.type = type;
		this.deviceId = deviceid;
		this.data = data;
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		try {
			for (int i = 0; i < data.length; i++) {
				obj.accumulate(i+"", data[i]);
			}
			obj.accumulate("length", data.length);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

	public NavigationEventType getType() {
		return type;
	}

	public double[] getData() {
		return data;
	}
}