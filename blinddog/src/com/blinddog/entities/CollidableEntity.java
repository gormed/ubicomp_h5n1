package com.blinddog.entities;

import com.jme3.collision.CollisionResults;
import com.blinddog.entities.base.SimpleCollidable;
import com.blinddog.entities.base.AbstractEntity;
import com.blinddog.entities.nodes.CollidableEntityNode;
import com.blinddog.eventsystem.events.CollisionEvent;
import com.blinddog.eventsystem.listener.CollisionListener;
import com.blinddog.main.Main;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;

/**
 * The class CollidableEntity for entities that will be collidable with others.
 * @author Hans Ferchland
 * @version 0.2
 */
public abstract class CollidableEntity extends AbstractEntity implements CollisionListener {

    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The collidable entity node. */
    protected CollidableEntityNode collidableEntityNode;
    protected BoundingVolume bound;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================

    /**
     * Contructs a collidable entity.
     * @param name the desired name of the entity
     */
    public CollidableEntity(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see entities.base.AbstractEntity#update(float)
     */
    @Override
    protected abstract void update(float tpf);

    /* (non-Javadoc)
     * @see entities.base.AbstractEntity#createNode(mazetd.MazeTDGame)
     */
    @Override
    public CollidableEntityNode createNode(Main game, Vector3f position) {

        collidableEntityNode =
                new CollidableEntityNode(name + "s_GeometryNode", this);
        super.createNode(game, position).attachChild(collidableEntityNode);

        return collidableEntityNode;
    }
    
    @Override
    public CollidableEntityNode getCollidableEntityNode(){
        return collidableEntityNode;
    
    
    }
    
    public void setBound(BoundingVolume bv){
        this.bound = bv;
    }
    
        public BoundingVolume getBound(){
        return this.bound;
    }


}
