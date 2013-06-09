package com.blinddog.eventsystem.events;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector2f;
import com.blinddog.entities.base.AbstractEntity;

/**
 * The class EntityEvent for all events of an entity.
 * @author Hans Ferchland
 * @version 1.0
 */
public class EntityEvent extends AbstractEvent {
    
    /**
     * The type of entity-event, that happened.
     */
    public enum EntityEventType {

        /** The Click. */
        Click,
        
        /** The Mouse over. */
        MouseOver,
        
        /** The Mouse left. */
        MouseLeft
    }
    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The entity. */
    private AbstractEntity entity;
    
    /** The event type. */
    private EntityEvent.EntityEventType eventType;
    
    /** The mouse. */
    private Vector2f mouse;
    
    /** The result. */
    private CollisionResult result;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================

    /**
     * Creates a new entity-event.
     * @param source the firing source
     * @param entity the entity that invoked the event
     * @param eventType the type of event that happened
     */
    public EntityEvent(Object source, AbstractEntity entity, EntityEvent.EntityEventType eventType) {
        super(source);
        this.entity = entity;
        this.eventType = eventType;
    }

    /**
     * Creates a new entity-event.
     * @param entity the entity that invoked the event
     * @param eventType the type of event that happened
     * @param mouse the mouse position while the event happened
     * @param result the collision results of the ray-cast
     */
    public EntityEvent(
            AbstractEntity entity,
            EntityEvent.EntityEventType eventType,
            Vector2f mouse,
            CollisionResult result) {
        super(entity);
        this.entity = entity;
        this.eventType = eventType;
        this.mouse = mouse;
        this.result = result;
    }

    /**
     * Gets the entity that invoked the event.
     * @return the invoking entity
     */
    public AbstractEntity getEntity() {
        return entity;
    }

    /**
     * Gets the event type of the event.
     * @return the entity events type
     */
    public EntityEvent.EntityEventType getEventType() {
        return eventType;
    }

    /**
     * Gets the events mouse-coordinate.
     *
     * @return the mouse
     */
    public Vector2f getMouse() {
        return mouse;
    }

    /**
     * Gets the events collision results.
     *
     * @return the result
     */
    public CollisionResult getResult() {
        return result;
    }
}
