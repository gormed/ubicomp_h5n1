package com.blinddog.eventsystem.listener;

import com.blinddog.eventsystem.events.PersonEvent;

/**
 * This interface CreepListener for all creep events.
 * @author Hans Ferchland
 */
public interface PersonListener {
    /**
     * Is invoked by event manager if a certain creep event happens.
     * @param e the event infos, what creep, what event type and so on
     * @see CreepEvent
     */
    public void onAction(PersonEvent e);
}
