package com.h5n1.eventsys.events;

import com.h5n1.eventsys.events.Event;
// 3) Bewegungs-Events

public class MotionEvent extends Event<MotionEvent.MotionEventType> {

	public enum MotionEventType {
		FALLEN_HUMAN,
		STUMBLED_HUMAN,
		RAISES_HUMAN,
		PUSHED_HUMAN,
		PULLED_HUMAN,
		STOPPED_HUMAN,
		STARTED_HUMAN
	}

	private float[] motionVector;
	private MotionEventType type;

	public MotionEvent(String json) {
		super();
	}

	public MotionEvent(MotionEventType type, float[] motionVector) {
		super();
		this.type = type;
		if (motionVector != null && motionVector.length == 3) { 
			this.motionVector = motionVector;
		} else {
			this.motionVector = new float[3];
		}
	}

	public float[] getMotionVector() {
		return motionVector;
	}

	public MotionEventType getType() {
		return type;
	}

	public String toJsonString() {
		return motionVector[0] +","+ motionVector[1]+","+motionVector[2];
	}
}