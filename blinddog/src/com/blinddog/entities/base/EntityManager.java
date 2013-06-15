package com.blinddog.entities.base;

import com.blinddog.entities.Buergersteig;
import com.blinddog.entities.CollidableEntity;
import com.blinddog.entities.Grass;
import com.jme3.math.Vector2f;
import com.blinddog.eventsystem.port.ScreenRayCast3D;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.blinddog.entities.Person ;
import com.blinddog.entities.ModelObject;
import com.blinddog.entities.Street;
import com.blinddog.eventsystem.PersonHandler;
import com.blinddog.eventsystem.events.PersonEvent.PersonEventType;
import com.blinddog.eventsystem.port.Collider3D;
import java.util.HashMap;
import java.util.Map;
import com.blinddog.main.Main;

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
        return EntityManager.EntityManagerHolder.INSTANCE;
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
    private HashMap<Integer, AbstractEntity> objectHashMap = new HashMap<Integer, AbstractEntity>();
    
    /** The creep hash map. */
    private HashMap<Integer, Person> personHashMap = new HashMap<Integer, Person>();

    
    /** The game. */
    private Main game = Main.getInstance();
    
    /** The ray cast3d. */
    private ScreenRayCast3D rayCast3D = ScreenRayCast3D.getInstance();
    
    /** The collider3d. */
    private Collider3D collider3D = Collider3D.getInstance();
    
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
        objectHashMap.clear();
        personHashMap.clear();
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
        objectHashMap.clear();
        personHashMap.clear();
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
        objectHashMap.remove(id);
        return entityHashMap.remove(id);
    }

    /**
     * Updates all the entitys in game.
     * @param tpf the time between the last update call
     */
    public void update(float tpf) {

        rayCast3D.update(tpf);
        HashMap<Integer, AbstractEntity> clone = new HashMap<Integer, AbstractEntity>(entityHashMap);
        for (Map.Entry<Integer, AbstractEntity> e : clone.entrySet()) {
            e.getValue().update(tpf);
        }
    }

    /**
     * Creates a tower on a given position.
     * @param name the towers name
     * @param square the map square to posit the tower
     * @return the tower just created
     */
    public Buergersteig createBuergersteig(String name, Vector3f position) {
        Buergersteig t = new Buergersteig(name, position);

        addEntity(t);
        objectHashMap.put(t.getEntityId(), t);
        Node geometryNode = t.createNode(game);
        Collider3D.getInstance().addCollisonObject(geometryNode);
        //ScreenRayCast3D.getInstance().addClickableObject(geometryNode);
        return t;
    }
/**
     * Creates a tower on a given position.
     * @param name the towers name
     * @param square the map square to posit the tower
     * @return the tower just created
     */
    public Street createStreet(String name, Vector3f position) {
        Street t = new Street(name, position);

        addEntity(t);
        objectHashMap.put(t.getEntityId(), t);
        Node geometryNode = t.createNode(game);
        Collider3D.getInstance().addCollisonObject(geometryNode);
        //ScreenRayCast3D.getInstance().addClickableObject(geometryNode);
        return t;
    }
    /**
     * Creates a tower on a given position.
     * @param name the towers name
     * @param square the map square to posit the tower
     * @return the tower just created
     */
    public Grass createGrass(String name, Vector3f position) {
        Grass t = new Grass(name, position);

        addEntity(t);
        objectHashMap.put(t.getEntityId(), t);
        Node geometryNode = t.createNode(game);
        Collider3D.getInstance().addCollisonObject(geometryNode);
        //ScreenRayCast3D.getInstance().addClickableObject(geometryNode);
        return t;
    }
    /**
     * Creates a creep on a given position.
     * @param name the creeps name
     * @param position the creeps position in 3D
     * @param healthPoints the current HP of the creep
     * @param maxHealthPoints the maximum HP of the creep
     * @return the creep just created
     */
    public Person createPerson(String name, Vector3f position) {
        Person c = new Person(name, position);

        addEntity(c);
        personHashMap.put(c.getEntityId(), c);
        Node geometryNode = c.createNode(game);
        Collider3D.getInstance().addCollisonObject(geometryNode);
        PersonHandler.getInstance().
                invokePersonAction(
                PersonEventType.Created, c, null);
        return c;
    }

    /**
     * Gets the hashmap with all creeps with their entityid as key.
     * @return all the creeps in a hashmap keyed by its entity-ids
     */
    public HashMap<Integer, Person> getPersonHashMap() {
        return personHashMap;
    }

   
    /**
     * Gets the hashmap with all towers with their entityid as key.
     * @return all the towers in a hashmap keyed by its entity-ids
     */
    public HashMap<Integer, AbstractEntity> getObjectHashMap() {
        return objectHashMap;
    }
}
