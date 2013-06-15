package com.blinddog.eventsystem;

import com.blinddog.entities.CollidableEntity;
import com.blinddog.entities.Person;
import com.blinddog.eventsystem.events.PersonEvent;
import com.blinddog.eventsystem.listener.PersonListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The class CreepHandler for the handling of the creep-events.
 * @author Hans Ferchland
 */
public class PersonHandler {
    //==========================================================================
    //===   Singleton
    //==========================================================================

    /**
     * The hidden constructor of CreepHandler.
     */
    private PersonHandler() {
    }

    /**
     * The static method to retrive the one and only instance of CreepHandler.
     *
     * @return single instance of CreepHandler
     */
    public static PersonHandler getInstance() {
        return PersonHandler.PersonHandlerHolder.INSTANCE;
    }

    /**
     * The holder-class CreepHandlerHolder for the CreepHandler.
     */
    private static class PersonHandlerHolder {

        /** The Constant INSTANCE. */
        private static final PersonHandler INSTANCE = new PersonHandler();
    }
    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The creep listeners. */
    private HashMap<Person, HashSet<PersonListener>> personListeners =
            new HashMap<Person, HashSet<PersonListener>>();
    //==========================================================================
    //===   Methods
    //==========================================================================

    /**
     * Adds a CreepListener for any given set of Creeps.
     *
     * @param creepListener the listener to add
     * @param creeps the creeps
     */
    void addPersonListener(PersonListener personListener, Person... persons) {
        for (Person c : persons) {
            HashSet<PersonListener> listeners;
            if (!personListeners.containsKey(c)) {
                listeners = new HashSet<PersonListener>();
                listeners.add(personListener);
                personListeners.put(c, listeners);
            } else {
                listeners = personListeners.get(c);
                listeners.add(personListener);
            }
        }
    }

    /**
     * Removes a creeplistener from the handler, the listener will no longer
     * be informed about creep events.
     * @param listener the listener to remove
     */
    void removePersonListener(PersonListener listener) {
        PersonListener remove = null;
        for (Map.Entry<Person, HashSet<PersonListener>> entry :
                personListeners.entrySet()) {
            for (PersonListener entityListener : entry.getValue()) {
                if (entityListener.equals(listener)) {
                    remove = entityListener;
                    break;
                }
            }
            if (remove != null) {
                entry.getValue().remove(remove);
                remove = null;
            }
        }
    }
    //==========================================================================
    //===   Inner Classes
    //==========================================================================

    /**
     * Invokes a creep action/event by a given type and the creep invoking the
     * action. If the creep attacks a tower or is killed by a tower you can
     * handle the tower too, but otherwise leave it null.
     * @param actionType the type of creep-event
     * @param creep the creep invoking the action
     * @param target the target tower if needed, otherwise null
     */
    public void invokePersonAction(
            PersonEvent.PersonEventType actionType, Person person,
            CollidableEntity target) {
        if (personListeners.isEmpty()) {
            return;
        }
        PersonEvent event = new PersonEvent(actionType, person, target);

        if (personListeners.containsKey(person)) {
            HashSet<PersonListener> listeners = personListeners.get(person);
            for (PersonListener l : listeners) {
                l.onAction(event);
            }
        }
        if (personListeners.containsKey(null)) {
            HashSet<PersonListener> listeners = personListeners.get(null);
            for (PersonListener l : listeners) {
                l.onAction(event);
            }
        }
    }
}
