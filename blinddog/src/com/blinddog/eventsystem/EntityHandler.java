package com.blinddog.eventsystem;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector2f;
import com.blinddog.entities.base.AbstractEntity;
import com.blinddog.entities.ClickableEntity;
import com.blinddog.entities.base.SimpleClickable;
import com.blinddog.eventsystem.events.EntityEvent;
import com.blinddog.eventsystem.listener.EntityListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The class EntityHandler is responsible for invoking EntityEvents.
 * @author Hans Ferchland
 */
public class EntityHandler {
    //==========================================================================
    //===   Singleton
    //==========================================================================

    /**
     * The hidden constructor of EnitiyHandler.
     */
    private EntityHandler() {
    }

    /**
     * The static method to retrive the one and only instance of EnitiyHandler.
     *
     * @return single instance of EntityHandler
     */
    public static EntityHandler getInstance() {
        return EntityHandler.EnitiyHandlerHolder.INSTANCE;
    }

    /**
     * The holder-class EnitiyHandlerHolder for the EnitiyHandler.
     */
    private static class EnitiyHandlerHolder {

        /** The Constant INSTANCE. */
        private static final EntityHandler INSTANCE = new EntityHandler();
    }
    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The entity listeners. */
    private HashMap<AbstractEntity, HashSet<EntityListener>> entityListeners =
            new HashMap<AbstractEntity, HashSet<EntityListener>>();
    //==========================================================================
    //===   Methods
    //==========================================================================

    /**
     * Adds a EntityListener for any given set of AbstractEntity or null if you
     * want to listen to all the entities events.
     *
     * @param entityListener the listener to add
     * @param entitys the entitys
     */
    void addEntityListener(EntityListener entityListener, AbstractEntity... entitys) {

        for (AbstractEntity e : entitys) {
            HashSet<EntityListener> listeners;
            if (!entityListeners.containsKey(e)) {
                listeners = new HashSet<EntityListener>();
                listeners.add(entityListener);
                entityListeners.put(e, listeners);
            } else {
                listeners = entityListeners.get(e);
                listeners.add(entityListener);
            }
        }
    }

    /**
     * Removes a EntityListener from the handler, the listener will no longer
     * be informed about entity events.
     * @param listener the listener to remove
     */
    void removeEntityListener(EntityListener listener) {
        EntityListener remove = null;
        for (Map.Entry<AbstractEntity, HashSet<EntityListener>> entry : entityListeners.entrySet()) {
            for (EntityListener entityListener : entry.getValue()) {
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
    //===   Invocation Methods
    //==========================================================================
    /**
     * Invokes a given entity-event for a given entity. 
     * <p>
     * See EntityEventType in EntityEvent for more. Should only be called for 
     * special purposes; if an enity is clicked, killed, destructed or created. 
     * 
     * Whatever you do use this with care!
     * </p>
     * <p>
     * Have in mind that new events can be created if you add your type to the
     * enum EntityEventType and implement the usage of this event. Means that 
     * you have to call it in a special case, e.g. if a unit is at zero HP or
     * whatever. Hand over special variables as you wish, extend the EnityEvent
     * what you like (new constructor of fields+getter). You may also extend even
     * this function or class if necessary for calling the event.
     * </p>
     * 
     * @param actionType the desired event type to be fired
     * @param entity the firing entity
     * @param mouse the mouse position if its a mouse-event otherwise null
     * @param result the collision result if its a mouse-event or 
     * collision event
     * @see EntityEvent
     * @see ClickableEntity
     */
    public void invokeEntityAction(
            EntityEvent.EntityEventType actionType, SimpleClickable entity,
            Vector2f mouse, CollisionResult result) {
        if (entityListeners.isEmpty()) {
            return;
        }
        if (entity instanceof AbstractEntity) {
            AbstractEntity e = (AbstractEntity) entity;
            EntityEvent event = new EntityEvent(e, actionType, mouse, result);
            if (entityListeners.containsKey(e)) {
                HashSet<EntityListener> listeners = entityListeners.get(e);
                for (EntityListener l : listeners) {
                    l.onAction(event);
                }
            }
            if (entityListeners.containsKey(null)) {
                HashSet<EntityListener> listeners = entityListeners.get(null);
                for (EntityListener l : listeners) {
                    l.onAction(event);
                }
            }
        }

    }
}
