package com.blinddog.entities.nodes;

import com.jme3.scene.Node;
import com.blinddog.entities.base.AbstractEntity;

/**
 * The class EntityNode is the base class of all nodes that carry geometry or 
 * bv or clickables for entities.
 * @author Hans Ferchland
 * @version 0.2
 */
public class EntityNode extends Node {
    //==========================================================================
    //===   Private Fields
    //==========================================================================

    /** The entity. */
    protected AbstractEntity entity;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================

    /**
     * Creates the entity-node by a name and an entity.
     * @param entity the desired entity to represent
     * @param name the deseired node-name
     */
    public EntityNode(AbstractEntity entity, String name) {
        super(name);
        this.entity = entity;
    }

    /**
     * Gets the entity of the EntityNode. Childs of this class will return their
     * proper Entity; e.g. so that ClickableEntity is returned by the 
     * ClickableEntityNodes getEntity().
     * 
     * @return the entity represented by this node.
     */
    public AbstractEntity getEntity() {
        return entity;
    }
}
