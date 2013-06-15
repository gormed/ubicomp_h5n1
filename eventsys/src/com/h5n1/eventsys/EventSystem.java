package com.h5n1.eventsys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.h5n1.eventsys.events.CompanionEvent;
import com.h5n1.eventsys.events.Event;
import com.h5n1.eventsys.events.GPSEvent;
import com.h5n1.eventsys.events.MotionEvent;
import com.h5n1.eventsys.events.RFIDEvent;
import com.h5n1.eventsys.listener.EventListener;


public class EventSystem {
	private static EventSystem instance;
	private EventSystem() {

	}

	public static EventSystem getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = new EventSystem();
	}

	// =================================================

	private static Queue<Event> incomingEvents = new LinkedList<Event> ();
	private static Queue<Event> outgoingEvents = new LinkedList<Event> ();

	private ArrayList<EventListener<RFIDEvent>> rfidEventListeners = new ArrayList<>();
	private ArrayList<EventListener<GPSEvent>> gpsEventListeners = new ArrayList<>();
	private ArrayList<EventListener<MotionEvent>> motionEventListeners = new ArrayList<>();
	private ArrayList<EventListener<CompanionEvent>> companionEventListeners = new ArrayList<>();

	public static void pushEvent(Event event) {
		outgoingEvents.offer(event);

	}

	public static void pullEvent(Event event) {
		incomingEvents.offer(event);
	}

	public void updateEvents(float timeGap) {
		Event temp;
		while (!incomingEvents.isEmpty()) {
			temp = incomingEvents.poll();
			raiseCheckedEvent(temp);
		}
		while (!outgoingEvents.isEmpty()) {
			temp = outgoingEvents.poll();
			System.out.println( JsonRequester.newEvent(temp).toString() );
		}
	}

	public void addRFIDListener(EventListener<RFIDEvent> listener) {
		rfidEventListeners.add(listener);
	}
	public void addGPSListener(EventListener<GPSEvent> listener) {
		gpsEventListeners.add(listener);
	}
	public void addMotionListener(EventListener<MotionEvent> listener) {
		motionEventListeners.add(listener);
	}
	public void addCompanionListener(EventListener<CompanionEvent> listener) {
		companionEventListeners.add(listener);
	}

	public void removeRFIDListener(EventListener<RFIDEvent> listener) {
		rfidEventListeners.remove(listener);
	}
	public void removeGPSListener(EventListener<GPSEvent> listener) {
		gpsEventListeners.remove(listener);
	}
	public void removeMotionListener(EventListener<MotionEvent> listener) {
		motionEventListeners.remove(listener);
	}
	public void removeCompanionListener(EventListener<CompanionEvent> listener) {
		companionEventListeners.remove(listener);
	}

	private void raiseCheckedEvent(Event event) {
		if (event instanceof RFIDEvent) {
			raiseRFIDEvent((RFIDEvent) event);
		} else if (event instanceof GPSEvent) {
			raiseGPSEvent((GPSEvent) event);
		} else if (event instanceof MotionEvent) {
			raiseMotionEvent((MotionEvent) event);
		} else if (event instanceof CompanionEvent) {
			raiseCompanionEvent((CompanionEvent) event);
		} else {
			System.err.println("The event-type is not supported!");
		}
	}

	private void raiseRFIDEvent(RFIDEvent event) {
		for (EventListener<RFIDEvent> lst : rfidEventListeners) {
			lst.fired(event);
		}
	}
	private void raiseGPSEvent(GPSEvent event) {
		for (EventListener<GPSEvent> lst : gpsEventListeners) {
			lst.fired(event);
		}
	}
	private void raiseMotionEvent(MotionEvent event) {
		for (EventListener<MotionEvent> lst : motionEventListeners) {
			lst.fired(event);
		}
	}
	private void raiseCompanionEvent(CompanionEvent event) {
		for (EventListener<CompanionEvent> lst : companionEventListeners) {
			lst.fired(event);
		}
	}
}