package com.blinddog.eventsystem;

import com.blinddog.entities.Person;
import com.blinddog.entities.base.AbstractEntity;
import com.jme3.bounding.BoundingVolume;
import com.blinddog.eventsystem.listener.KeyInputListener;
//import eventsystem.listener.MouseInputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
//import entities.Creep;
//import entities.base.AbstractEntity;
import com.blinddog.eventsystem.listener.CollisionListener;
import com.blinddog.eventsystem.listener.EntityListener;
import com.blinddog.eventsystem.listener.MouseInputListener;
import com.blinddog.eventsystem.listener.PersonListener;
import com.jme3.math.Vector3f;
//import eventsystem.listener.TimerEventListener;

/**
 * The class EventManager for manageing all events.
 * It updates times events, click events, collision events,
 * entity events, creep events ans jme3 input events.
 * @author Hans Ferchland
 * @version 0.5
 */
public class EventManager {
    //==========================================================================
    //===   Singleton
    //==========================================================================

    /**
     * The hidden constructor of the singleton.
     */
    private EventManager() {
    }

    /**
     * Static method to retrieve the one and olny reference to the manager.
     * @return the reference of the EventManager
     */
    public static EventManager getInstance() {
        return EventManager.EventManagerHolder.INSTANCE;
    }

    /**
     * Holder class for the EventManager.
     */
    private static class EventManagerHolder {

        /** The Constant INSTANCE. */
        private static final EventManager INSTANCE = new EventManager();
    }
    //==========================================================================
    //===   Private Fields
    //==========================================================================

    
    /** The collision handler. */
    private CollisionHandler collisionHandler = CollisionHandler.getInstance();
    
            
        /** The input handler. */
    private InputHandler inputHandler = InputHandler.getInstance();
        /** The enitiy handler. */
    private EntityHandler enitiyHandler = EntityHandler.getInstance();

    /** The creep handler. */
    private PersonHandler personHandler = PersonHandler.getInstance();

    //==========================================================================
    //===   Methods
    //==========================================================================

    /**
     * Updates manager in general and the timer-events in special.
     * @param tpf the time-gap between two update ticks.
     */
    public void update(float tpf) {
        //timerHandler.update(tpf);
        collisionHandler.update(tpf);
    }

    /**
     * Adds a CollisionListener to the manager that listens to collision-events
     * from the list of BoundingVolumes given.
     * @param listener the listener to add
     * @param boundingVolumes the list of bv to listen to
     */
    public void addCollisionListener(
            CollisionListener listener, BoundingVolume... boundingVolumes) {
        collisionHandler.addCollisionListener(listener, boundingVolumes);
    }

    /**
     * Removes a CollisionListener from the manager and the according 
     * BoundingVolumes if not listened to by other listeners.
     * @param listener the listener to remove
     */
    public void removeCollisionListener(
            CollisionListener listener) {
        collisionHandler.removeCollisionListener(listener);
    }
       
    /**
     * Adds a KeyInputListener for one or more mappings.
     * @param inputListener the listener to add
     * @param mappings the mappings to hear for the listener
     */
    public void addKeyInputListener(KeyInputListener inputListener, String... mappings) {
        inputHandler.addKeyInputListener(inputListener, mappings);
    }

    /**
     * Removes a KeyInputListener for all its mappings.
     * @param inputListener the listener to remove
     */
    public void removeKeyInputListener(KeyInputListener inputListener) {
        inputHandler.removeKeyInputListener(inputListener);
    }
    
        /**
     * Adds a key-input-mapping to the input-manager.
     * @param mapping the key-name of the mappig of the triggers
     * @param keyTriggers the triggers that will trigger 
     * the mapping for the listeners
     */
    public void addKeyInputEvent(String mapping, KeyTrigger... keyTriggers) {
        inputHandler.addKeyInputEvent(mapping, keyTriggers);
    }
    
       /**
     * Adds a MouseInputListener for one or more mappings.
     * @param inputListener the listener to add
     * @param mappings the mappings to hear for the listener
     */
    public void addMouseInputListener(MouseInputListener inputListener, String... mappings) {
        inputHandler.addMouseInputListener(inputListener, mappings);
    }

    /**
     * Removes a MouseInputListener for all its mappings.
     * @param inputListener the listener to remove
     */
    public void removeMouseInputListener(MouseInputListener inputListener) {
        inputHandler.removeMouseInputListener(inputListener);
    }
    /**
     * Adds a mouse-button-mapping to the input-manager.
     * @param mapping the key-name of the mappig of the triggers
     * @param mouseButtonTriggers the triggers that will trigger 
     * the mapping for the listeners
     */
    public void addMouseButtonEvent(String mapping, MouseButtonTrigger... mouseButtonTriggers) {
        inputHandler.addMouseButtonEvent(mapping, mouseButtonTriggers);
    }

    /**
     * Adds a mouse-movement-mapping to the input-manager.
     * @param mapping the key-name of the mappig of the triggers
     * @param mouseAxisTriggers the triggers that will trigger 
     * the mapping for the listeners
     */
    public void addMouseMovementEvent(String mapping, MouseAxisTrigger... mouseAxisTriggers) {
        inputHandler.addMouseMovementEvent(mapping, mouseAxisTriggers);
    }
    
    /**
     * Adds a EntityListener any entity-event.
     *
     * @param entityListener the listener
     * @param entitys the entitys
     */
    public void addEntityListener(EntityListener entityListener, AbstractEntity... entitys) {

        enitiyHandler.addEntityListener(entityListener, entitys);
    }

    /**
     * Removes a EntityListener from its entity-events.
     * @param entityListener the listener
     */
    public void removeEntityListener(EntityListener entityListener) {

        enitiyHandler.removeEntityListener(entityListener);
    }

    /**
     * Adds a CreepListener for any entity-event.
     *
     * @param creepListener the listener
     * @param creeps the creeps
     */
    public void addPersonListener(PersonListener personListener, Person... persons) {

        personHandler.addPersonListener(personListener, persons);
    }

    /**
     * Removes a CreepListener from its creep-events.
     * @param creepListener the listener
     */
    public void removePersonListener(PersonListener personListener) {

        personHandler.removePersonListener(personListener);
    }


}
