package com.blinddog.entities.nodes;

import com.jme3.collision.CollisionResults;
import com.blinddog.entities.CollidableEntity;
import com.blinddog.entities.nodes.EntityNode;
import com.blinddog.eventsystem.events.CollisionEvent;
import com.jme3.collision.Collidable;

/**
 * The class CollidableEntityNode which has as child all geometry that will be
 * collidable.
 * @author Hans Ferchland
 * @version 0.2
 */
public class CollidableEntityNode extends EntityNode {
    //==========================================================================
    //===   Private Fields
    //==========================================================================

    /** The entity. */
    protected CollidableEntity entity;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================

    /**
     * Constructs a entity node that will be collidable.
     * @param name the deseired node-name
     * @param entity the entity connected to the collidable
     */
    public CollidableEntityNode(String name, CollidableEntity entity) {
        super(entity, name);
        this.entity = entity;
    }
    
    


}
