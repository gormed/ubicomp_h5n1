package com.blinddog2.eventsys.events;

// 1) RFID-Events
// 2) GPS-Events [ (1+2) Blinden-Events ]
// 3) Bewegungs-Events
// 4) Betreuer-Events

public abstract class Event<T> implements EventType<T> {

	private static long uniqueCounter = 0;
	private static long getUniqueId() { return uniqueCounter++; }

	private long id = getUniqueId();
	private String receiverId = "0";
	protected String deviceId = "0";
	public abstract String toJsonString();
	private EventState state = EventState.NEW_EVENT;

	public void setEventId(long id) {
		this.id = id;
	}
	
	public long getEventId() {
		return id;
	}
	
	public abstract T getType();

	public void setReceiverId(String recieverId) {
		this.receiverId = recieverId;
	}
	
	public String getReceiverId() {
		return this.receiverId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public EventState getState() {
		return state;
	}
	
	public void setState(EventState state) {
		this.state = state;
	}
}