
package com.blinddog.eventsystem.events;

import java.util.EventObject;

/**
 * The class AbstractEvenet for all events in MazeTD.
 * @author Hans Ferchland
 */
public abstract class AbstractEvent extends EventObject {
    //==========================================================================
    //===   Static
    //==========================================================================

    /** The running eventid for all events. */
    private static int runningEventID = 0;

    /**
     * Gets the next eventID. This function increments the eventID by each call.
     * There will never be a doubled eventid!
     * @return the next free and unused eventID
     */
    static int getContiniousEventID() {
        return runningEventID++;
    }
    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /**
     * The events unique id.
     */
    private int eventID = getContiniousEventID();
    //==========================================================================
    //===   Constructor & Methods
    //==========================================================================
    /**
     * Creates an event.
     * @param source the firing object.
     */
    public AbstractEvent(Object source) {
        super(source);
    }

    /**
     * Returns the event-id of the event.
     * @return the events id
     */
    public int getEventID() {
        return eventID;
    }
}
