package com.h5n1.eventsys.events;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.JsonRequester;

public class ApplicationEvent extends Event<ApplicationEvent.ApplicationEventType> {
	
	public enum ApplicationEventType {
		REGISTER_DEVICE,
		CREATE_DEVICE_TABLE,
		DELETE_ALL_EVENTS,
		GET_ALL_EVENTS,
		CONNECT_DEVICE,
		DISCONNECT_DEVICE,
		GET_ALL_DEVICES,
		GET_ALL_DEVICE_EVENTS
	}
	
	private ApplicationEventType type;
	private JSONArray devices = null;
	private JSONArray events = null;
	
	public ApplicationEvent(String deviceid, ApplicationEventType type, JSONObject json) {
		super();
		this.deviceId = deviceid;
		this.type = type;
		try {
			if (type == ApplicationEventType.GET_ALL_DEVICES) {
				setReceiverId(json.getString(JsonRequester.TAG_RECEIVERID));
				setEventId(json.getInt(JsonRequester.TAG_EVENTID));
				JSONObject content = new JSONObject(json.getString(JsonRequester.TAG_CONTENT));
				devices = content.getJSONArray("devices");
				
			} else if (type == ApplicationEventType.GET_ALL_DEVICE_EVENTS) {
				setReceiverId(json.getString(JsonRequester.TAG_RECEIVERID));
				setEventId(json.getInt(JsonRequester.TAG_EVENTID));
				JSONObject content = new JSONObject(json.getString(JsonRequester.TAG_CONTENT));
				events = content.getJSONArray("events");
				
			} else {

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public ApplicationEvent(String deviceid, ApplicationEventType type) {
		super();
		this.type = type;
		this.deviceId = deviceid;
	}
	
	@Override
	public String toJsonString() {
		JSONObject o = new JSONObject();
		try {
			o.accumulate("deviceid", deviceId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return o.toString();
	}

	@Override
	public ApplicationEventType getType() {
		return type;
	}

	public JSONArray getDevices() {
		return devices;
	}
	
	public JSONArray getEvents() {
		return events;
	}
}
