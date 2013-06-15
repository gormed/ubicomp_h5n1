package com.blinddog.main;

import com.blinddog.entities.Buergersteig;
import com.blinddog.entities.Grass;
import com.blinddog.entities.Person;
import com.blinddog.entities.Street;
import com.blinddog.entities.base.EntityManager;
import com.blinddog.eventsystem.EventManager;
import com.blinddog.eventsystem.listener.KeyInputListener;
import com.blinddog.eventsystem.port.Collider3D;
import com.blinddog.eventsystem.port.ScreenRayCast3D;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
 
/**
 * 
 * This collision code uses Physics and a custom Action Listener.
 * @author normen, with edits by Zathras
 */
public class Main extends SimpleApplication{
 
  private Spatial sceneModel;
  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private CharacterControl blindPersonControl;
  private Vector3f walkDirection = new Vector3f();
  private CameraNode camNode;
  private boolean left = false, right = false, up = false, down = false;
  private EventManager eventManager;
  private EntityManager entityManager;
    /** The single reference to the game. */
    private static Main instance;
 
  public static void main(String[] args) {
    getInstance().start();
  }
   public static Main getInstance() {
           if (instance != null) {
               return instance;
           }
           return instance = new Main();
   }
    private Person blindPerson;
    private Buergersteig buergersteig;
    private Street street;
    private Grass grass;
   
  public void simpleInitApp() {

        entityManager = EntityManager.getInstance();
        eventManager = EventManager.getInstance();
        entityManager.initialize();
        ScreenRayCast3D.getInstance().initialize();
        Collider3D.getInstance().initialize();
    /** Set up Physics */
   
    buergersteig = entityManager.createBuergersteig("Buergersteig",  new Vector3f(0f, 0f, 0f));
    street = entityManager.createStreet("Street",  new Vector3f(0f, 0f, 0f));
    grass = entityManager.createGrass("grass",  new Vector3f(0f, 0f, 0f));

    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
    blindPersonControl = new CharacterControl(capsuleShape, 0.05f);
    
     
    blindPerson = entityManager.createPerson("blindPerson", new Vector3f(0f, 0f, 0f));

    blindPerson.getCollidableEntityNode().addControl(blindPersonControl);
    blindPersonControl.setJumpSpeed(20);
    blindPersonControl.setFallSpeed(30);
    blindPersonControl.setGravity(30);
    blindPersonControl.setPhysicsLocation(new Vector3f(0, 10, 0));
 
    // We attach the scene and the player to the rootNode and the physics space,
    // to make them appear in the game world.

    
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    bulletAppState.getPhysicsSpace().add(buergersteig.getLandscape());
    bulletAppState.getPhysicsSpace().add(street.getLandscape());
    bulletAppState.getPhysicsSpace().add(grass.getLandscape());
    bulletAppState.getPhysicsSpace().add(blindPersonControl);
    //bulletAppState.getPhysicsSpace().addCollisionListener(this);

    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    // Disable the default flyby cam
    flyCam.setEnabled(false);
    //create the camera Node
    camNode = new CameraNode("Camera Node", cam);
    //This mode means that camera copies the movements of the target:
    camNode.setControlDir(ControlDirection.SpatialToCamera);
    //Attach the camNode to the target:
    blindPerson.getCollidableEntityNode().attachChild(camNode);
    //Move camNode, e.g. behind and above the target:
    camNode.setLocalTranslation(new Vector3f(0, 50, 0));
    //Rotate the camNode to look at the target:
    camNode.lookAt(blindPerson.getCollidableEntityNode().getLocalTranslation(), Vector3f.UNIT_Y);
            // EventManager init
    

    setUpKeys();
    setUpLight();
 
  }
   
  private void setUpLight() {
    // We add light so we see the scene
    AmbientLight al = new AmbientLight();
    al.setColor(ColorRGBA.White.mult(1.3f));
    rootNode.addLight(al);
 
    DirectionalLight dl = new DirectionalLight();
    dl.setColor(ColorRGBA.White);
    dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
    rootNode.addLight(dl);
  }
 
  /** Setup Key Mapping **/
  private void setUpKeys() {
    eventManager.addKeyInputEvent("Left", new KeyTrigger(KeyInput.KEY_A));
    eventManager.addKeyInputEvent("Right", new KeyTrigger(KeyInput.KEY_D));
    eventManager.addKeyInputEvent("Up", new KeyTrigger(KeyInput.KEY_W));
    eventManager.addKeyInputEvent("Down", new KeyTrigger(KeyInput.KEY_S));
    eventManager.addKeyInputEvent("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    eventManager.addKeyInputListener(new KeyInputListener() {

            //EVtl auslagern in InputHolder
          public void onAction(String name, boolean isPressed, float tpf) {
              if (name.equals("Left")) {
                left = isPressed;
              } else if (name.equals("Right")) {
                right = isPressed;
              } else if (name.equals("Up")) {
                up = isPressed;
              } else if (name.equals("Down")) {
                down = isPressed;
              } else if (name.equals("Jump")) {
                blindPersonControl.jump();
    }
          }
      }, "Left","Right","Up","Down","Jump");
   
  }

 
//  private void collisionDetection(Node a,Spatial b){
//        // Calculate detection results
//  CollisionResults results = new CollisionResults();
//  a.collideWith(b, results);
//  System.out.println("Number of Collisions between" + 
//      a.getName()+ " and " + b.getName() + ": " + results.size());
//  // Use the results
//  if (results.size() > 0) {
//    // how to react when a collision was detected
//    CollisionResult closest  = results.getClosestCollision();
//    System.out.println("What was hit? " + closest.getGeometry().getName() );
//    System.out.println("Where was it hit? " + closest.getContactPoint() );
//    System.out.println("Distance? " + closest.getDistance() );
//  } else {
//    // how to react when no collision occured
//  }
//}
  
  
// UPDATE 
  @Override
  public void simpleUpdate(float tpf) {
        
        //    Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        //    Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
            Vector3f vDir = new Vector3f(0,0,0.5f);
            Vector3f vLeft = new Vector3f(0.5f,0,0);
            Vector3f oldPos = blindPerson.getPosition();
            walkDirection.set(0, 0, 0);
            if (left)  { walkDirection.addLocal(vLeft); }
            if (right) { walkDirection.addLocal(vLeft.negate()); }
            if (up)    { walkDirection.addLocal(vDir); }
            if (down)  { walkDirection.addLocal(vDir.negate()); }
            blindPersonControl.setWalkDirection(walkDirection);
            oldPos = oldPos.add(walkDirection);
            blindPerson.moveTo(oldPos);
            System.out.println(Collider3D.getInstance().getCollisionNode().getChildren());
            //System.out.println("Objects: " + entityManager.getObjectHashMap());
    eventManager.update(tpf);
    entityManager.update(tpf);
    //blindPersonNode.setPosition(new Vector3f(1,1,1));
    
    
    //fpsText.setText("Pos: "+blindPersonNode.getPosition());
    
  }

}