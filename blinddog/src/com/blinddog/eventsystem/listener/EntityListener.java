package com.blinddog.eventsystem.listener;

import com.blinddog.eventsystem.events.EntityEvent;
import java.util.EventListener;

/**
 * The interface EntityListener for all entity-events.
 * @author Hans Ferchland
 * @version 0.1
 */
public interface EntityListener extends EventListener {
    /**
     * Is invoked by event manager if any certain entity event, like 
     * klicking happened.
     * @param entityEvent the event fired by the manager
     */
    public void onAction(EntityEvent entityEvent);
}
