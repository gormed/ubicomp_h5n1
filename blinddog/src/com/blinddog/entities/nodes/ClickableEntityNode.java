package com.blinddog.entities.nodes;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector2f;
import com.blinddog.entities.ClickableEntity;
import com.blinddog.eventsystem.EntityHandler;
import com.blinddog.eventsystem.events.EntityEvent.EntityEventType;
import com.blinddog.eventsystem.interfaces.Clickable3D;

/**
 * The class ClickableEntityNode for all the entitys geometry 
 * that will be clickable.
 * @author Hans Ferchland
 */
public class ClickableEntityNode extends EntityNode implements Clickable3D {
    //==========================================================================
    //===   Private Fields
    //==========================================================================

    /** The entity. */
    protected ClickableEntity entity;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================
    /**
     * Contructor of the node for the clickable geometry.
     *
     * @param name the deseired node-name
     * @param entity the entity
     */
    public ClickableEntityNode(String name, ClickableEntity entity) {
        super(entity, name);
        this.entity = entity;
    }

    /* (non-Javadoc)
     * @see eventsystem.interfaces.Clickable3D#onRayCastClick(com.jme3.math.Vector2f, com.jme3.collision.CollisionResult)
     */
    @Override
    public void onRayCastClick(Vector2f mouse, CollisionResult result) {
        entity.onClick();
        EntityHandler.getInstance().invokeEntityAction(
                EntityEventType.Click, entity, mouse, result);
    }

    /* (non-Javadoc)
     * @see eventsystem.interfaces.Clickable3D#onRayCastMouseOver(com.jme3.math.Vector2f, com.jme3.collision.CollisionResult)
     */
    @Override
    public void onRayCastMouseOver(Vector2f mouse, CollisionResult result) {
        entity.onMouseOver();
        EntityHandler.getInstance().invokeEntityAction(
                EntityEventType.MouseOver, entity, mouse, result);
    }

    /* (non-Javadoc)
     * @see eventsystem.interfaces.Clickable3D#onRayCastMouseLeft(com.jme3.math.Vector2f, com.jme3.collision.CollisionResult)
     */
    @Override
    public void onRayCastMouseLeft(Vector2f mouse, CollisionResult result) {
        entity.onMouseLeft();
        EntityHandler.getInstance().invokeEntityAction(
                EntityEventType.MouseLeft, entity, mouse, result);
    }

    /* (non-Javadoc)
     * @see entities.nodes.EntityNode#getEntity()
     */
    @Override
    public ClickableEntity getEntity() {
        return entity;
    }
}
