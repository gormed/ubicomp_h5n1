package com.h5n1.eventsys.events;

import com.h5n1.eventsys.events.Event;
// 3) Bewegungs-Events

public class MotionEvent extends Event {

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

	public MotionEvent(MotionEventType type, float[] motionVector) {
		super();
		this.type = type;
		if (motionVector != null && motionVector.length > 2) { 
			this.motionVector = {motionVector[0], motionVector[1], motionVector[2]};
		} else {
			this.motionVector = {0,0,0};
		}
	}

	public float[] getMotionVector() {
		return motionVector;
	}

	public MotionEventType getType() {
		return type;
	}

	public String toJsonString() {
		return getEventId() + ",MOTION," + this.type + "," + motionVector[0] +","+ motionVector[1]+","+motionVector[2];
	}
}