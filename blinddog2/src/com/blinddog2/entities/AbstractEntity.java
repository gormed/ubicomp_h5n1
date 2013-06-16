package com.blinddog2.entities;

import com.blinddog2.main.Main;
import com.jme3.scene.Node;


/**
 * The class AbstractEntity. This class is the parent of all entitys!
 * @author Hans Ferchland
 * @version 0.2
 */
public abstract class AbstractEntity {
    //==========================================================================
    //===   private, package & protected Fields
    //==========================================================================

    /** the unique id of the entity!. */
    protected int id;
    /** the desired name of an entity, could be twice in the game. */
    protected String name;
    /** The Node for the geometry of this entity. */
    protected Node geometryNode;

    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================
    /**
     * Hidden construnctor for internal creation. Maybe this will be usefull.
     * @param id the id given by a gerator for running ids
     * @param name the desired name of an entity, could be twice in the game.
     */
    AbstractEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Contructor of the basic entity parameters. Needs to be 
     * enhanced for child-classes.
     * @param name the desired name of an entity, could be twice in the game.
     */
    public AbstractEntity(String name) {
        this.name = name;
        this.id = EntityManager.getContinousEntityID();
    }

    /**
     * Creates the geometry node and returns it. Generally all geometry of an
     * entity is placed unter this node! This node will be added to se SG.
     *
     * @param game the game
     * @return the created main-geometry node
     */
    public Node createNode(Main game) {
        geometryNode = new Node(name + "s_GeometryNode");
        
        return geometryNode;
    }

    /**
     * Updates the entity every game-tick.
     * @param tpf the time between the last update call
     */
    protected abstract void update(float tpf);

    /**
     * Gets the enities node where the geometry is attached.
     * @return the node with the geometry of this entity
     */
    public Node getGeometryNode() {
        return geometryNode;
    }

    /**
     * Gets the entity-id from this entity; this id is unique!.
     *
     * @return the entities unique id
     */
    public int getEntityId() {
        return id;
    }

    /**
     * Gets the entity-name from this entity, could be twice in the game.
     * @return the enities name
     */
    public String getName() {
        return name;
    }
}
