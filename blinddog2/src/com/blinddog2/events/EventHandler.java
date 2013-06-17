/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog2.events;

import com.blinddog2.entities.EntityManager;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.blinddog2.eventsys.EventSystem;
import com.blinddog2.eventsys.JsonRequester;
import com.blinddog2.eventsys.events.ApplicationEvent;
import com.blinddog2.eventsys.events.ApplicationEvent.ApplicationEventType;
import com.blinddog2.eventsys.events.CompanionEvent;
import com.blinddog2.eventsys.events.EventState;
import com.blinddog2.eventsys.events.GPSEvent;
import com.blinddog2.eventsys.events.MotionEvent;
import com.blinddog2.eventsys.events.NavigationEvent;
import com.blinddog2.eventsys.events.NavigationEvent.NavigationEventType;
import com.blinddog2.eventsys.events.RFIDEvent;
import com.blinddog2.eventsys.listener.ApplicationEventListener;
import com.blinddog2.eventsys.listener.CompanionEventListener;
import com.blinddog2.eventsys.listener.GPSEventListener;
import com.blinddog2.eventsys.listener.MotionEventListener;
import com.blinddog2.eventsys.listener.NavigationEventListener;
import com.blinddog2.eventsys.listener.RFIDEventListener;

public class EventHandler {

	private GPSEventListener gpsEventListener;
	private RFIDEventListener rfidEventListener;
	private MotionEventListener motionEventListener;
	private CompanionEventListener companionEventListener;
	private ApplicationEventListener applicationEventListener;
	private NavigationEventListener navigationEventListener;

	private ArrayList<Integer> focusedEventIds = new ArrayList<Integer>();

	private Timer timer = null;
	private TimerTask eventsystemTask = null;

	private ApplicationEvent getAllEvents;

	public EventHandler(final EventSystem system) {

                // hole alle Devices Events -> in progress Ahmed
            
            
		// get all events for this device
		getAllEvents = new ApplicationEvent("0", ApplicationEventType.GET_ALL_EVENTS);
		getAllEvents.setState(EventState.GET_ALL_EVENTS);
		getAllEvents.setReceiverId(JsonRequester.getDeviceID());

		gpsEventListener = new GPSEventListener() {

			@Override
			public void fired(GPSEvent event) {
                            if (event.getType() == GPSEvent.GPSEventType.UPDATE_LOCATION ){
                                //...
                                
                            }
			}
		};
		rfidEventListener = new RFIDEventListener() {

			@Override
			public void fired(RFIDEvent event) {
                            
			}
		};
		motionEventListener = new MotionEventListener() {

			@Override
			public void fired(MotionEvent event) {

			}
		};
		companionEventListener = new CompanionEventListener() {

			@Override
			public void fired(CompanionEvent event) {

			}
		};

		applicationEventListener = new ApplicationEventListener() {

			@Override
			public void fired(ApplicationEvent event) {

			}
		};

		navigationEventListener = new NavigationEventListener() {

			@Override
			public void fired(NavigationEvent event) {
				event.setState(EventState.DELETE_EVENT);
				EventSystem.pushEvent(event);
			}
		};

		system.addCompanionListener(companionEventListener);
		system.addGPSListener(gpsEventListener);
		system.addMotionListener(motionEventListener);
		system.addRFIDListener(rfidEventListener);
		system.addApplicationListener(applicationEventListener);
		system.addNavigationListener(navigationEventListener);

		timer = new Timer("eventTimer", false);
		final float timeGap = 1;
		eventsystemTask = new TimerTask() {

			@Override
			public void run() {
				EventSystem.pushEvent(getAllEvents);
				system.update(timeGap);
				// LinkedList<Event> in = system.updateInEvents(timeGap);
				// HashMap<Event, JSONArray> out =
				// system.updateOutEvents(timeGap);
			}
		};
		timer.schedule(eventsystemTask, 2000, (long) (timeGap * 1000));

	}

	public ArrayList<Integer> getFocusedEventIds() {
		return focusedEventIds;
	}

	public void addFocusedEvent(Integer id) {
		focusedEventIds.add(id);
	}

}
