package com.h5n1.eventsys.events;

import org.json.JSONException;
import org.json.JSONObject;

import com.h5n1.eventsys.events.Event;

// 3) Bewegungs-Events

public class MotionEvent extends Event<MotionEvent.MotionEventType> {

	public enum MotionEventType {
		FALLEN_HUMAN, STUMBLED_HUMAN, RAISES_HUMAN, PUSHED_HUMAN, PULLED_HUMAN, STOPPED_HUMAN, STARTED_HUMAN
	}

	private float[] motionVector;
	private MotionEventType type;

	public MotionEvent(String deviceid, MotionEventType type, JSONObject json) {
		super();
		this.deviceId = deviceid;
		this.type = type;
	}

	public MotionEvent(String deviceid, MotionEventType type,
			float[] motionVector) {
		super();
		this.type = type;
		if (motionVector != null && motionVector.length == 3) {
			this.motionVector = motionVector;
		} else {
			this.motionVector = new float[3];
		}
		this.deviceId = deviceid;
	}

	public float[] getMotionVector() {
		return motionVector;
	}

	public MotionEventType getType() {
		return type;
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		try {
			int i = 0;
			for (float f : motionVector)
				obj.accumulate((i++) + "", f);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
}