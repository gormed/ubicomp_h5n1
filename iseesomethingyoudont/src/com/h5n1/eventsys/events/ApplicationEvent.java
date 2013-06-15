package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.events.GPSEvent.GPSEventType;

public class ApplicationEvent extends Event<ApplicationEvent.ApplicationEventType> {
	
	public enum ApplicationEventType {
		DELETE_ALL_EVENTS,
		GET_ALL_EVENTS,
		CONNECT_DEVICE,
		DISCONNECT_DEVICE,
	}
	
	private ApplicationEventType type;
	
	public ApplicationEvent(String deviceid, JSONObject json) {
		super();
		this.deviceId = deviceid;
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


}
