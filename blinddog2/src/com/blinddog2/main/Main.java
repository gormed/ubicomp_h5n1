package com.blinddog2.main;
 
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
 
/**
 * Example 9 - How to make walls and floors solid.
 * This collision code uses Physics and a custom Action Listener.
 * @author normen, with edits by Zathras
 */
public class Main extends SimpleApplication
        implements ActionListener, PhysicsCollisionListener {
 
  private Spatial sceneModel;
  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private CharacterControl player;
  
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false;
 
  public static void main(String[] args) {
    Main app = new Main();
    app.start();
  }
    private Spatial streetModel;
    private Spatial grassModel;
    private Spatial buergersteigModel;
    private RigidBodyControl grasslandscape;
    private RigidBodyControl streetlandscape;
    private RigidBodyControl buergersteiglandscape;
    private CameraNode camNode;
    private Spatial blindPersonModel;
    private CharacterControl blindPersonControl;
 
  public void simpleInitApp() {
    /** Set up Physics */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
 
    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
   

    setUpKeys();
    setUpLight();
 
   
    // load street Model
    streetModel = assetManager.loadModel("Scenes/street.j3o");
    streetModel.setLocalScale(2f);
    CollisionShape streetShape =
            CollisionShapeFactory.createMeshShape((Node) streetModel);
    streetlandscape = new RigidBodyControl(streetShape, 0);
    streetModel.addControl(streetlandscape);
 
      // load grass Model
    grassModel = assetManager.loadModel("Scenes/grass.j3o");
    grassModel.setLocalScale(2f);
    CollisionShape grassShape =
            CollisionShapeFactory.createMeshShape((Node) grassModel);
    grasslandscape = new RigidBodyControl(grassShape, 0);
    grassModel.addControl(grasslandscape);
 
    
    // load grass Model
    buergersteigModel = assetManager.loadModel("Scenes/buergersteig.j3o");
    buergersteigModel.setLocalScale(2f);
    CollisionShape buergersteigShape =
            CollisionShapeFactory.createMeshShape((Node) buergersteigModel);
    buergersteiglandscape = new RigidBodyControl(buergersteigShape, 0);
    buergersteigModel.addControl(buergersteiglandscape);
 
    
    
     Box blindPerson = new Box(Vector3f.ZERO, 1, 1.8f, 1); // create cube shape at the origin
       blindPersonModel = new Geometry("Box", blindPerson);  // create cube geometry from the shape
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
        blindPersonModel.setMaterial(mat);  
        rootNode.attachChild(blindPersonModel); 

    
    // We set up collision detection for the player by creating
    // a capsule collision shape and a CharacterControl.
    // The CharacterControl offers extra settings for
    // size, stepheight, jumping, falling, and gravity.
    // We also put the player in its starting position.
    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
    blindPersonControl = new CharacterControl(capsuleShape, 0.05f);
    blindPersonModel.addControl(blindPersonControl);
    blindPersonControl.setJumpSpeed(20);
    blindPersonControl.setFallSpeed(30);
    blindPersonControl.setGravity(30);
    blindPersonControl.setPhysicsLocation(new Vector3f(0, 10, 0));
 
    // We attach the scene and the player to the rootNode and the physics space,
    // to make them appear in the game world.
    rootNode.attachChild(streetModel);
    rootNode.attachChild(grassModel);
    rootNode.attachChild(buergersteigModel);
    
    bulletAppState.getPhysicsSpace().add(streetlandscape);
    bulletAppState.getPhysicsSpace().add(grasslandscape);
    bulletAppState.getPhysicsSpace().add(buergersteiglandscape);
    bulletAppState.getPhysicsSpace().add(blindPersonControl);

    
     flyCam.setEnabled(true);

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
 
  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private void setUpKeys() {
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
  }
 
  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("Left")) {
      left = value;
    } else if (binding.equals("Right")) {
      right = value;
    } else if (binding.equals("Up")) {
      up = value;
    } else if (binding.equals("Down")) {
      down = value;
    } else if (binding.equals("Jump")) {
      blindPersonControl.jump();
    }
  }
 
  /**
   * This is the main event loop--walking happens here.
   * We check in which direction the player is walking by interpreting
   * the camera direction forward (camDir) and to the side (camLeft).
   * The setWalkDirection() command is what lets a physics-controlled player walk.
   * We also make sure here that the camera moves with player.
   */
  @Override
  public void simpleUpdate(float tpf) {
//Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
//Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
         bulletAppState.update(tpf);
    Vector3f vDir = new Vector3f(0,0,0.5f);
    Vector3f vLeft = new Vector3f(0.5f,0,0);
    Vector3f oldPos = blindPersonModel.getLocalTranslation();
    walkDirection.set(0, 0, 0);
    if (left)  { walkDirection.addLocal(vLeft); }
    if (right) { walkDirection.addLocal(vLeft.negate()); }
    if (up)    { walkDirection.addLocal(vDir); }
    if (down)  { walkDirection.addLocal(vDir.negate()); }
     blindPersonControl.setWalkDirection(walkDirection);
            oldPos = oldPos.add(walkDirection);
    
  }

    public void collision(PhysicsCollisionEvent event) {
        System.out.println(event.getNodeA().getName());
    }
}