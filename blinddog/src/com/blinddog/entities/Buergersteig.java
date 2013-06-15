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
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;


/**
 * The class Creep discribes the creep entity.
 * A creep can walk to a given position, can be hit by a projectile, can have 
 * orb effects on it and can destroy a tower.
 * @author Hans Ferchland
 * @version 0.3
 */
public class Buergersteig extends CollidableEntity {

    // visual
    /** The geometry. */
    private Spatial sceneModel;
    
    /** The material. */
    private Material material;
    
      private RigidBodyControl landscape;
    
    /** The position. */
    private Vector3f position;
    

    /** The debug geometry. */
    private ClickableGeometry debugGeometry;
    
    /** The debug path toggle. */
    private boolean debugPathToggle = false;
    

  
    
  
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
    public Buergersteig(String name, Vector3f position) {
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

       
        }

    /**
     * Creates debug geometry for pathfind-debug.
     * @param game the mazetd game
     */
    private void createDebugGeometry(Main game) {

          // We load the scene from the zip file and adjust its size.
    //assetManager.registerLocator("assets/Scenes/town/", ZipLocator.class);
    sceneModel = game.getAssetManager().loadModel("Scenes/buergersteig.j3o");
    sceneModel.setLocalScale(2f);
 
    // We set up collision detection for the scene by creating a
    // compound collision shape and a static RigidBodyControl with mass zero.
    CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape((Node) sceneModel);
    landscape = new RigidBodyControl(sceneShape, 0);
    sceneModel.addControl(landscape);

       
        //sceneModel.setCullHint(CullHint.Always);
        //debugGeometry.setLocalTranslation(0, CREEP_HEIGHT * 0.5f + 0.01f, 0);
        ScreenRayCast3D.getInstance().addClickableObject(sceneModel);

    }

    public RigidBodyControl getLandscape() {
        return landscape;
    }

    public ClickableGeometry getDebugGeometry() {
        return debugGeometry;
    }




  
    /**
     * The current position of the creep. May be obsolete if not moving, call 
     * getCollidableEntityNode().getLocalTranslation() for exact info.
     * @return the position
     */
    public Vector3f getPosition() {
        return position;
    }
    
  
}
