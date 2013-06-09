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
import com.blinddog.entities.base.EntityManager;
import com.blinddog.entities.geometry.ClickableGeometry;
import com.blinddog.entities.nodes.CollidableEntityNode;
import com.blinddog.eventsystem.PersonHandler;
import com.blinddog.eventsystem.events.PersonEvent.PersonEventType;
import com.blinddog.eventsystem.port.Collider3D;
import com.blinddog.eventsystem.port.ScreenRayCast3D;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import com.blinddog.main.Main;


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

    /** The Constant CREEP_BASE_DAMAGE. */
    public static final float CREEP_BASE_DAMAGE = 50.0f;
    
    /** The Constant CREEP_BASE_ORB_DROP. */
    public static final float CREEP_BASE_ORB_DROP = 0.0f;
    
    /** The Constant CREEP_BASE_SPEED. */
    public static final float CREEP_BASE_SPEED = 1.1f;
    
    /** The Constant CREEP_DECAY. */
    public static final float CREEP_DECAY = 1f;
    
    /** The Constant CREEP_DESTROY_PARTICLES. */
    public static final int CREEP_DESTROY_PARTICLES = 10;
    
    /** The Constant CREEP_GOLD_PARTICLES. */
    public static final int CREEP_GOLD_PARTICLES = 5;
    
    /** The Constant CREEP_GROUND_RADIUS. */
    public static final float CREEP_GROUND_RADIUS = 0.25f;
    
    /** The Constant CREEP_HEIGHT. */
    public static final float CREEP_HEIGHT = 0.5f;
    
    /** The Constant CREEP_MAX_HP. */
    public static final int CREEP_MAX_HP = 100;
    
    /** The Constant CREEP_MIN_DISTANCE. */
    public static final float CREEP_MIN_DISTANCE = 0.5f;
    
    /** The Constant CREEP_SAMPLES. */
    private static final int CREEP_SAMPLES = CREEP_DESTROY_PARTICLES;
    
    /** The Constant CREEP_TOP_RADIUS. */
    public static final float CREEP_TOP_RADIUS = 0.1f;
    
    /** The Constant CREEP_BASE_DAMAGE_INTERVAL. */
    private static final float CREEP_BASE_DAMAGE_INTERVAL = 1f;
    //==========================================================================
    //===   Private Fields
    //==========================================================================
    // visual
    /** The geometry. */
    private Geometry geometry;
    
    /** The material. */
    private Material material;
    
    /** The position. */
    private Vector3f position;
    
    /** The destination. */
    private Vector3f destination;
    
    /** The decaying. */
    private boolean decaying = false;
    
    /** The decay time. */
    private float decayTime = 0;
    
    /** The debug geometry. */
    private ClickableGeometry debugGeometry;
    
    /** The debug path toggle. */
    private boolean debugPathToggle = false;
    

   
    /** The target. */
    private ModelObject target;

  
    /** The speed. */
    private float speed = CREEP_BASE_SPEED;
    
    /** The moving. */
    private boolean moving = true;
    
    /** The attacking. */
    private boolean attacking = false;
        
    /** The damage interval. */
    private float damageInterval = CREEP_BASE_DAMAGE_INTERVAL;
    
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
    public void onCollision(CollisionResults collisionResults) {
    }

    /* (non-Javadoc)
     * @see entities.base.CollidableEntity#createNode(mazetd.MazeTDGame)
     */
    @Override
    public CollidableEntityNode createNode(Main game) {
        super.createNode(game);
        createDebugGeometry(game);


        return collidableEntityNode;
    }

    /* (non-Javadoc)
     * @see entities.base.CollidableEntity#update(float)
     */
    @Override
    protected void update(float tpf) {

        // if dead and therfore decaying
        if (decaying) {
            decayTime += tpf;
            if (decayTime > CREEP_DECAY) {
                // finally destroy
           
            }
            return;
        }

     
      
    }

    /**
     * Creates the creeps geometry and attaches it to the 
     * collidableEntityNode.
     * @param game the MazeTDGame reference
     */
    private void createObjectGeometry(Main game) {
        // Material
        material = new Material(
                game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);  // Set some parameters, e.g. blue.
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.Black);   // ... color of this object
        material.setColor("Diffuse", ColorRGBA.Gray);   // ... color of light being reflected

        // Geometry
        float[] angles = {(float) Math.PI / 2, 0, 0};

        Cylinder c = new Cylinder(
                CREEP_SAMPLES,
                CREEP_SAMPLES,
                CREEP_GROUND_RADIUS,
                CREEP_TOP_RADIUS,
                CREEP_HEIGHT,
                true, false);

        geometry = new Geometry("Creep_Geometry_" + name, c);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(0, CREEP_HEIGHT * 0.5f + 0.01f, 0);
        geometry.setLocalRotation(new Quaternion(angles));
        geometry.setQueueBucket(Bucket.Inherit);

        collidableEntityNode.attachChild(geometry);
        collidableEntityNode.setLocalTranslation(position);
        collidableEntityNode.setShadowMode(ShadowMode.CastAndReceive);
    }

    /**
     * Creates debug geometry for pathfind-debug.
     * @param game the mazetd game
     */
    private void createDebugGeometry(Main game) {

        Material m = new Material(
                game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors", true);  // Set some parameters, e.g. blue.
        m.setColor("Specular", ColorRGBA.BlackNoAlpha);
        m.setColor("Ambient", ColorRGBA.BlackNoAlpha);   // ... color of this object
        m.setColor("Diffuse", ColorRGBA.BlackNoAlpha);   // ... color of light being reflected


        float[] angles = {(float) Math.PI / 2, 0, 0};

        debugGeometry = new ClickableGeometry(
                name + "_DebugGeometry",
                new Cylinder(
                CREEP_SAMPLES,
                CREEP_SAMPLES,
                CREEP_GROUND_RADIUS,
                CREEP_TOP_RADIUS,
                CREEP_HEIGHT,
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
        debugGeometry.setCullHint(CullHint.Always);
        //debugGeometry.setLocalTranslation(0, CREEP_HEIGHT * 0.5f + 0.01f, 0);
        debugGeometry.setLocalRotation(new Quaternion(angles));
        ScreenRayCast3D.getInstance().addClickableObject(debugGeometry);

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

  
}
