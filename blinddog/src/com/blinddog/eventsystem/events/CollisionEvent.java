
package com.blinddog.eventsystem.events;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;

/**
 * The class CollisionEvent that capsules the collision data for listeners.
 * @author Hans Ferchland
 * @version 1.0
 */
public class CollisionEvent extends AbstractEvent {

    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The self. */
    private Collidable self;
    
    /** The with. */
    private CollisionResults with;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================
    /**
     * Creates a new collision event.
     * @param self the object that collides
     * @param with the list of resulst of the collision
     */
    public CollisionEvent(Collidable self, CollisionResults with) {
        super(self);
        this.self = self;
        this.with = with;
    }

    /**
     * Gets the main actor in the collision.
     *
     * @return the main collidable
     */
    public Collidable getSelf() {
        return self;
    }

    /**
     * Gets the list of collision objects, the object collides with.
     * @return the collision results
     */
    public CollisionResults getWith() {
        return with;
    }
}
