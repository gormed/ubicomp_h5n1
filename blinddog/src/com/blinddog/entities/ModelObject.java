/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog.entities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Quad;

import com.blinddog.entities.CollidableEntity;
import com.blinddog.entities.base.AbstractEntity;
import com.blinddog.entities.base.EntityManager;
import com.blinddog.entities.geometry.ClickableGeometry;
import com.blinddog.entities.nodes.CollidableEntityNode;
import com.blinddog.eventsystem.EventManager;
import com.blinddog.eventsystem.PersonHandler;
import com.blinddog.eventsystem.events.CollisionEvent;
import com.blinddog.eventsystem.events.PersonEvent.PersonEventType;
import com.blinddog.eventsystem.port.ScreenRayCast3D;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import com.blinddog.main.Main;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import java.util.HashMap;
import java.util.Map;


/**
 * The class Creep discribes the creep entity.
 * A creep can walk to a given position, can be hit by a projectile, can have 
 * orb effects on it and can destroy a tower.
 * @author Hans Ferchland
 * @version 0.3
 */
public class ModelObject extends CollidableEntity {
    //==========================================================================
    //===   Constants
    //==========================================================================

  
    /** The Constant CREEP_BASE_SPEED. */
    public static final float PERSON_BASE_SPEED = 1.1f;
    
    /** The Constant CREEP_HEIGHT. */
    public static final float PERSON_HEIGHT = 2.0f;
    
    /** The Constant CREEP_MIN_DISTANCE. */
    public static final float PERSON_MIN_DISTANCE = 0.5f;

    /** The Constant CREEP_TOP_RADIUS. */
    public static final float PERSON_RADIUS = 1.0f;
    
        /** The Constant CREEP_SAMPLES. */
    private static final int PERSON_SAMPLES = 10;

    //==========================================================================
    //===   Private Fields
    //==========================================================================
    // visual
    /** The geometry. */
    private Geometry geometry;
    
    /** The material. */
    private Material material;
    
    private BoundingVolume boundingSphere;
    
    /** The position. */
    private Vector3f position;
    
    /** The destination. */
    private Vector3f destination;
    
    /** The decaying. */
    private boolean decaying = false;

    /** The debug geometry. */
    private ClickableGeometry debugGeometry;
    
    /** The debug path toggle. */
    private boolean debugPathToggle = false;
    

   

  
    /** The speed. */
    private float speed = PERSON_BASE_SPEED;
    
    /** The moving. */
    private boolean moving = true;
    
    /** The attacking. */
    private boolean attacking = false;

    /** The interval counter. */
    private float intervalCounter = 0;

    
    //Particles
    /** The gold emitter. */
    private ParticleEmitter goldEmitter;
    
    /** The destroyed emitter. */
    private ParticleEmitter destroyedEmitter;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================

    /**
     * The constructor of the entity with a given name, hp and position.
     *
     * @param name the desired name of the creep
     * @param position the position on the map
     * @param healthPoints the HP of the creep
     * @param maxHealthPoints the max health points
     */
    public ModelObject(String name, Vector3f position) {
        super(name);
        this.position = position;
        

    }

    /* (non-Javadoc)
     * @see entities.base.CollidableEntity#onCollision(com.jme3.collision.CollisionResults)
     */
    @Override
     public void onCollision(CollisionEvent e){
        System.out.println(this.getName() + " collides with " + e.getWith());
    }

    /* (non-Javadoc)
     * @see entities.base.CollidableEntity#createNode(mazetd.MazeTDGame)
     */
    @Override
    public CollidableEntityNode createNode(Main game,  Vector3f position) {
        super.createNode(game, position);
        createDebugGeometry(game);


        return collidableEntityNode;
    }

    /* (non-Javadoc)
     * @see entities.base.CollidableEntity#update(float)
     */
    @Override
    protected void update(float tpf) {
        moveUpdate(tpf);   
        
        // if moving do this part
       CollisionResults results = new CollisionResults();
       HashMap<Integer, AbstractEntity> objects = EntityManager.getInstance().getObjectHashMap(); 
        for(Map.Entry<Integer, AbstractEntity> e : objects.entrySet())  {
            e.getValue().getGeometryNode().collideWith(this.bound,results);
        }
        if (results.size() > 0) {
             System.out.println("BOOOM");
        }
        else {
         System.out.println("---");
        }
        
       

      
    }

    /**
     * Creates debug geometry for pathfind-debug.
     * @param game the mazetd game
     */
    private void createDebugGeometry(Main game) {

       Material  m = new Material(
                game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //m.setBoolean("UseMaterialColors", true);  // Set some parameters, e.g. blue.
        m.setColor("Color", ColorRGBA.Blue);
        //m.setColor("Specular", ColorRGBA.White);
        //m.setColor("Ambient", ColorRGBA.Black);   // ... color of this object
        //m.setColor("Diffuse", ColorRGBA.Gray);   // ... color of light being reflected


        float[] angles = {(float) Math.PI / 2, 0, 0};

        debugGeometry = new ClickableGeometry(
                name + "_DebugGeometry",
                new Cylinder(
                PERSON_SAMPLES,
                PERSON_SAMPLES,
                PERSON_RADIUS,
                PERSON_HEIGHT,
                true, false)) {

            @Override
            public void onRayCastClick(Vector2f mouse, CollisionResult result) {
                //on click
            }

            @Override
            public void onRayCastMouseOver(Vector2f mouse, CollisionResult result) {

            }

            @Override
            public void onRayCastMouseLeft(Vector2f mouse, CollisionResult result) {

            }
        };
        debugGeometry.setMaterial(m);
        setBound(debugGeometry.getMesh().getBound());
        //debugGeometry.setCullHint(CullHint.Always);
        debugGeometry.setLocalTranslation(0, PERSON_HEIGHT * 0.5f + 0.01f, 0);
        debugGeometry.setLocalRotation(new Quaternion(angles));
        ScreenRayCast3D.getInstance().addClickableObject(debugGeometry);
        //createBoundingSphere();
       

        collidableEntityNode.attachChild(debugGeometry);
    }


    /**
     * Finally frees the creeps resources.
     */
    private void rotten() {
        collidableEntityNode.detachAllChildren();
  
        ScreenRayCast3D.getInstance().removeClickableObject(debugGeometry);
        EntityManager.getInstance().removeEntity(id);
    }

    /**
     * Moves the creep for every update call. The creep will go to a position 
     * given by its path calculated by the pathfinder. If it arrives on a position,
     * controlled by a min. distance, it will poll the next point in path to 
     * go to from the path-Queue.
     * @param tpf the time-gap
     */
    private void moveUpdate(float tpf) {
      
        }
 
    /**
     * Sets the moving-target of the creep.
     * @param target the desired position on the map
     */
    public void moveTo(Vector3f target) {
        this.destination = target;
        this.destination.y = 0;
        moving = true;
    }


    /**
     * Stops the movement of the creep. You have to 
     * call moveTo() to move it again.
     */
    public void stop() {
        moving = false;
    }
  
    /**
     * The current position of the creep. May be obsolete if not moving, call 
     * getCollidableEntityNode().getLocalTranslation() for exact info.
     * @return the position
     */
    public Vector3f getPosition() {
        return position;
    }
    
    /**
     * Stes the creeps speed, base speed is 1.1.
     * @param value the new speed
     */
    public void setSpeed(float value) {
        speed = value;
    }

    
    /**
     * Gets the current movement-speed of the creep.
     * @return the amount to move per time-gap
     */
    public float getSpeed() {
        return speed;
    }

    private void createBoundingSphere() {
        this.boundingSphere = debugGeometry.getMesh().getBound();
//        this.boundingSphere.setRadius(20.0f);
//        debugGeometry.getMesh().setBound(boundingSphere);
    }
  
}
