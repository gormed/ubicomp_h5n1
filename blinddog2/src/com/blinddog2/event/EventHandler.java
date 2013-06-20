package com.blinddog2.event;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.h5n1.eventsys.EventSystem;
import com.h5n1.eventsys.JsonRequester;
import com.h5n1.eventsys.events.ApplicationEvent;
import com.h5n1.eventsys.events.ApplicationEvent.ApplicationEventType;
import com.h5n1.eventsys.events.CompanionEvent;
import com.h5n1.eventsys.events.EventState;
import com.h5n1.eventsys.events.GPSEvent;
import com.h5n1.eventsys.events.MotionEvent;
import com.h5n1.eventsys.events.NavigationEvent;
import com.h5n1.eventsys.events.NavigationEvent.NavigationEventType;
import com.h5n1.eventsys.events.RFIDEvent;
import com.h5n1.eventsys.listener.ApplicationEventListener;
import com.h5n1.eventsys.listener.CompanionEventListener;
import com.h5n1.eventsys.listener.GPSEventListener;
import com.h5n1.eventsys.listener.MotionEventListener;
import com.h5n1.eventsys.listener.NavigationEventListener;
import com.h5n1.eventsys.listener.RFIDEventListener;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventHandler {

    private GPSEventListener gpsEventListener;
    private RFIDEventListener rfidEventListener;
    private MotionEventListener motionEventListener;
    private CompanionEventListener companionEventListener;
    private ApplicationEventListener applicationEventListener;
    private NavigationEventListener navigationEventListener;
    private ArrayList<Integer> focusedEventIds = new ArrayList<Integer>();
    private ArrayList<Device> devices = new ArrayList<Device>();
    private Timer timer = null;
    private TimerTask eventsystemTask = null;
    private boolean gotAllDevices = false;
    private HashMap<String, ApplicationEvent> getAllEvents = new HashMap<String, ApplicationEvent>();
    private ApplicationEvent getAllDevices;

    public EventHandler(final EventSystem system) {
        // register device
        getAllDevices = new ApplicationEvent("0", ApplicationEventType.GET_ALL_DEVICES);
        getAllDevices.setState(EventState.GET_ALL_DEVICES);
        EventSystem.pushEvent(getAllDevices);


        // test event
        double[] val = {1, 2, 3};
        NavigationEvent nav = new NavigationEvent(JsonRequester.getDeviceID(), NavigationEventType.OBSTACLE_HUMAN, val);
        nav.setReceiverId(JsonRequester.getDeviceID());
        EventSystem.pushEvent(nav);

        gpsEventListener = new GPSEventListener() {
            @Override
            public void fired(GPSEvent event) {
                if (event.getType().equals(GPSEvent.GPSEventType.UPDATE_LOCATION)) {
                   String deviceid = event.getDeviceId();
                    double x = event.getLa();
                    double y = event.getLo();
                    System.out.println("Device " + deviceid + " changed location to "+ x + " "+ y);
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
                if (event.getState().equals(EventState.GET_ALL_DEVICES)) {
                    JSONArray allDevices = event.getDevices();
                    for (int i = 0; i < allDevices.length(); i++) {
                        JSONArray device = (JSONArray) allDevices.get(i);
                        devices.add(new Device(device.getInt(0), device.getString(1)));
                        System.out.println("Device " + devices.get(i).deviceid);
                        ApplicationEvent getevents = new ApplicationEvent(device.getString(1), ApplicationEventType.GET_ALL_EVENTS);
                        getevents.setState(EventState.GET_ALL_EVENTS);
                        getAllEvents.put(device.getString(1), getevents);
                        //EventSystem.pushEvent(getevents);
                    }
                    gotAllDevices = true;
                }
            }
        };

        navigationEventListener = new NavigationEventListener() {
            @Override
            public void fired(NavigationEvent event) {
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
                for (Map.Entry<String, ApplicationEvent> entry : getAllEvents.entrySet()) {
                    EventSystem.pushEvent(entry.getValue());
                    
                }
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

    public boolean isGotAllDevices() {
        return gotAllDevices;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public static class Device {

        public int id;
        public String deviceid;

        public Device(int id, String deviceid) {
            this.id = id;
            this.deviceid = deviceid;
        }
    }
}
