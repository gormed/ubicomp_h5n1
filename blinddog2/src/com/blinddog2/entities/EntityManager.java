package com.blinddog2.entities;

import com.jme3.math.Vector2f;
//import eventsystem.port.ScreenRayCast3D;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.blinddog2.entities.Person;
import com.blinddog2.entities.SampleStaticObject;
import com.blinddog2.main.Main;
import java.util.HashMap;
import java.util.Map;

/**
 * The class EntityManager is a singleton that handles all entities in MazeTD, 
 * the updating, the resources, the IDs and all events.
 * @author Hans Ferchland
 */
public class EntityManager {
    //==========================================================================
    //===   Singleton
    //==========================================================================

    /**
     * The hidden constructor of EntityManager.
     */
    private EntityManager() {
    }

    /**
     * The static method to retrieve the one and only instance of EntityManager.
     *
     * @return single instance of EntityManager
     */
    public static EntityManager getInstance() {
        return EntityManagerHolder.INSTANCE;
    }

    /**
     * The holder-class EntityManagerHolder for the EntityManager.
     */
    private static class EntityManagerHolder {

        /** The Constant INSTANCE. */
        private static final EntityManager INSTANCE = new EntityManager();
    }
    //==========================================================================
    //===   Static Fields & Methods
    //==========================================================================
    /** The running entity id. */
    private static int runningEntityID = 0;

    /**
     * Gets the continuous entity id.
     *
     * @return the continuous entity id
     */
    static int getContinousEntityID() {
        return runningEntityID++;
    }
    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The entity hash map. */
    private HashMap<Integer, AbstractEntity> entityHashMap = new HashMap<Integer, AbstractEntity>();
    
    /** The tower hash map. */
    private HashMap<Integer, Person> personHashMap = new HashMap<Integer, Person>();
    
    /** The creep hash map. */
    private HashMap<Integer, StaticObject> staticObjectHashMap = new HashMap<Integer, StaticObject>();
    
    /** The game. */
    private Main game = Main.getInstance();
    
//    /** The ray cast3d. */
//    private ScreenRayCast3D rayCast3D = ScreenRayCast3D.getInstance();
    
    /** The initialized. */
    private boolean initialized = false;
    //==========================================================================
    //===   Methods
    //==========================================================================

    /**
     * Initializes the singleton the first time or after destroyed.
     */
    public void initialize() {
        if (initialized) {
            return;
        }
        entityHashMap.clear();
        personHashMap.clear();
        staticObjectHashMap.clear();
        initialized = true;
    }

    /**
     * Destroys the singleton if initialized and frees all resources so if can
     * be reinitialized.
     */
    public void destroy() {
        if (!initialized) {
            return;
        }
        entityHashMap.clear();
        personHashMap.clear();
        staticObjectHashMap.clear();
        initialized = false;
    }

    /**
     * This method adds any given AbstractEntity to the EntityManagers hashmap,
     * so it will be updated!.
     *
     * @param entity the entity inheriting AbstractEntity to add
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public AbstractEntity addEntity(AbstractEntity entity) {
        return entityHashMap.put(entity.getEntityId(), entity);
    }

    /**
     * This method will remove a given entity from the EntityManagers hashmap. 
     * It wont be updated anymore!
     * @param id the id of the entity to remove
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public AbstractEntity removeEntity(int id) {
        personHashMap.remove(id);
        staticObjectHashMap.remove(id);
        return entityHashMap.remove(id);
    }

    /**
     * Updates all the entitys in game.
     * @param tpf the time between the last update call
     */
    public void update(float tpf) {

        //rayCast3D.update(tpf);
        HashMap<Integer, AbstractEntity> clone = new HashMap<Integer, AbstractEntity>(entityHashMap);
        for (Map.Entry<Integer, AbstractEntity> e : clone.entrySet()) {
            e.getValue().update(tpf);
        }
    }

    /**
     * Creates a tower on a given position.
     * @param name the towers name
     * @return the tower just created
     */
    public Person createPerson(String name) {
        Person t = new Person(name);
        addEntity(t);
        personHashMap.put(t.getEntityId(), t);
        //rayCast3D.addClickableObject(geometryNode);
        return t;
    }

    
     public StaticObject createStaticObject(String name) {
        StaticObject t = new StaticObject(name, new Vector3f(20f,200f,10f));
        addEntity(t);
        staticObjectHashMap.put(t.getEntityId(), t);
        //rayCast3D.addClickableObject(geometryNode);
        return t;
    }
    /**
     * Gets the hashmap with all creeps with their entityid as key.
     * @return all the creeps in a hashmap keyed by its entity-ids
     */
    public HashMap<Integer, Person> getPersonHashMap() {
        return personHashMap;
    }


}
