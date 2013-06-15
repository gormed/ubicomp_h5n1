package com.blinddog.entities.nodes;

import com.jme3.collision.CollisionResults;
import com.blinddog.entities.CollidableEntity;
import com.blinddog.eventsystem.interfaces.Collidable3D;
import com.blinddog.entities.nodes.EntityNode;
import com.blinddog.eventsystem.events.CollisionEvent;

/**
 * The class CollidableEntityNode which has as child all geometry that will be
 * collidable.
 * @author Hans Ferchland
 * @version 0.2
 */
public class CollidableEntityNode extends EntityNode implements Collidable3D {
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

    /* (non-Javadoc)
     * @see eventsystem.interfaces.Collidable3D#onCollision3D(com.jme3.collision.CollisionResults)
     */
    @Override
    public void onCollision3D(CollisionEvent e) {
        entity.onCollision(e);
    }

    /* (non-Javadoc)
     * @see entities.nodes.EntityNode#getEntity()
     */
    @Override
    public CollidableEntity getEntity() {
        return entity;
    }
}
