package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.JsonRequester;
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
		SIGNAL_FOUND, NO_SIGNAL, SIGNAL_LOST, REQUEST_LOCATION, UPDATE_LOCATION, MOVEMENT_STARTED, MOVEMENT_STOPPED
	}

	private double lo, la;
	private GPSEventType type;

	public GPSEvent(String deviceid, GPSEventType type, JSONObject json) {
		super();
		this.deviceId = deviceid;

		try {
			setReceiverId( json.getString(JsonRequester.TAG_RECEIVERID) );
			setEventId(json.getInt(JsonRequester.TAG_EVENTID));
			this.type = type;
			JSONObject content = json.getJSONObject(JsonRequester.TAG_CONTENT);
			lo = content.getDouble("lo");
			la = content.getDouble("la");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GPSEvent(String deviceid, GPSEventType type, double lo, double la) {
		super();
		this.type = type;
		this.lo = lo;
		this.la = la;
		this.deviceId = deviceid;
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		try {
			obj.accumulate("lo", lo);
			obj.accumulate("la", la);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}

	public GPSEventType getType() {
		return type;
	}

	public void setLa(double la) {
		this.la = la;
	}

	public void setLo(double lo) {
		this.lo = lo;
	}

	public double getLo() {
		return lo;
	}

	public double getLa() {
		return la;
	}
	
	
}