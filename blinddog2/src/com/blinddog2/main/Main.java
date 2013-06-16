package com.blinddog2.main;
 
import com.blinddog2.entities.Buergersteig;
import com.blinddog2.entities.EntityManager;
import com.blinddog2.entities.Grass;
import com.blinddog2.entities.Houses;
import com.blinddog2.entities.Person;
import com.blinddog2.entities.SampleStaticObject;
import com.blinddog2.entities.StaticObject;
import com.blinddog2.entities.Street;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
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
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
 
/**
 * Example 9 - How to make walls and floors solid.
 * This collision code uses Physics and a custom Action Listener.
 * @author normen, with edits by Zathras
 */
public class Main extends SimpleApplication
        implements ActionListener, PhysicsCollisionListener {
    private Street street;
    private Grass grass;
    private Buergersteig buergersteig;
    private SampleStaticObject sampleStaticObject;
    private Houses houses;
    private Person blindPerson;
    private boolean zoomIn;
    private boolean zoomOut;
    private EntityManager entityManager;
    private StaticObject staticObject1;
  
    //==========================================================================
    //===   Singleton
    //==========================================================================

    /**
     * The hidden constructor of CollisionHandler.
     */
    private Main() {
    }

    /**
     * The static method to retrive the one and only instance of CollisionHandler.
     *
     * @return single instance of CollisionHandler
     */
    public static Main getInstance() {
        return MainHolder.INSTANCE;
    }

    /**
     * The holder-class CollisionHandlerHolder for the CollisionHandler.
     */
    private static class MainHolder {

        /** The Constant INSTANCE. */
        private static final Main INSTANCE = new Main();
    }

  private Spatial sceneModel;
  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private CharacterControl player;
  
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false;
 
  public static void main(String[] args) {
    getInstance().start();
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
    private boolean camleft;
    private boolean camright;
    private boolean camup;
    private boolean camdown;
 
  public void simpleInitApp() {
    entityManager = EntityManager.getInstance();
    entityManager.initialize();
    /** Set up Physics */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
 
    // We re-use the flyby camera for rotation, while positioning is handled by physics
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
   
    //setewretr
    setUpKeys();
    setUpLight();
 
   
    street = new Street();
    grass = new Grass();
    buergersteig = new Buergersteig();
    sampleStaticObject = new SampleStaticObject();
    
    blindPerson = entityManager.createPerson("blindPerson");
    staticObject1 = entityManager.createStaticObject("staticObject1");
    
    bulletAppState.getPhysicsSpace().addCollisionListener(this);
    

 
flyCam.setEnabled(false);
//create the camera Node
camNode = new CameraNode("Camera Node", cam);
////This mode means that camera copies the movements of the target:
///camNode.setControlDir(ControlDirection.SpatialToCamera);
////Move camNode, e.g. behind and above the target:
camNode.setLocalTranslation(new Vector3f(0, 300, -5));
//Rotate the camNode to look at the target:
camNode.lookAt(new Vector3f(0f,-20f,0f), Vector3f.UNIT_Y);

this.getRootNode().attachChild(camNode);

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
    inputManager.addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_LEFT));
    inputManager.addMapping("CamRight", new KeyTrigger(KeyInput.KEY_RIGHT));
    inputManager.addMapping("CamUp", new KeyTrigger(KeyInput.KEY_UP));
    inputManager.addMapping("CamDown", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("zoomIn", new KeyTrigger(KeyInput.KEY_N));
    inputManager.addMapping("zoomOut", new KeyTrigger(KeyInput.KEY_M));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "CamLeft");
    inputManager.addListener(this, "CamRight");
    inputManager.addListener(this, "CamUp");
    inputManager.addListener(this, "CamDown");
    inputManager.addListener(this, "Jump");
    inputManager.addListener(this, "zoomIn");
    inputManager.addListener(this, "zoomOut");
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
      blindPerson.getBlindPersonControl().jump();
    } else if (binding.equals("CamLeft")) {
      camleft = value;
    } else if (binding.equals("CamRight")) {
      camright = value;
    } else if (binding.equals("CamUp")) {
      camup = value;
    } else if (binding.equals("CamDown")) {
      camdown = value;
    } else if (binding.equals("zoomIn")) {
      zoomIn = value;
    } else if (binding.equals("zoomOut")) {
      zoomOut = value;
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
    Vector3f camDir = new Vector3f(0,0,0.5f);
    Vector3f camLeft = new Vector3f(0.5f,0,0);
     Vector3f camZoom = new Vector3f(0,5f,0);
    Vector3f camPos = camNode.getLocalTranslation();
    Vector3f oldPos = blindPerson.getModel().getLocalTranslation();
    walkDirection.set(0, 0, 0);
    if (left)  { walkDirection.addLocal(vLeft); }
    if (right) { walkDirection.addLocal(vLeft.negate()); }
    if (up)    { walkDirection.addLocal(vDir); }
    if (down)  { walkDirection.addLocal(vDir.negate()); }
    if (camleft)  { camPos.addLocal(camLeft); }
    if (camright) { camPos.addLocal(camLeft.negate()); }
    if (camup)    { camPos.addLocal(camDir); }
    if (camdown)  { camPos.addLocal(camDir.negate()); }
    if (zoomIn)    { camPos.addLocal(camZoom); }
    if (zoomOut)  { camPos.addLocal(camZoom.negate()); }
     blindPerson.getBlindPersonControl().setWalkDirection(walkDirection);
     camNode.setLocalTranslation(camPos);
            oldPos = oldPos.add(walkDirection);
            
        
     blindPerson.update(tpf);
//     Vector3f walkpoint = blindPerson.getPosition().add(blindPerson.getBlindPersonControl().getWalkDirection().mult(new Vector3f(10f,10f,10f)));
//        drawLine(blindPerson.getPosition().x,blindPerson.getPosition().y, blindPerson.getPosition().z,walkpoint.x, walkpoint.y, walkpoint.z);
        
  }

    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }
  
    public void drawLine( float x1,float y1,float z1,float x2,float y2,float z2){
    rootNode.detachChildNamed("line"); 
         Mesh m = new Mesh();
            m.setMode(Mesh.Mode.Lines);

            // Line from 0,0,0 to 0,1,0
            m.setBuffer(VertexBuffer.Type.Position, 3, new float[]{ x1, y1, z1, x2, y2, z2});
            m.setBuffer(VertexBuffer.Type.Index, 2, new short[]{ 0, 1 });
            Geometry lineGeometry = new Geometry("line", m);
            Material mat = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
             mat.setColor("Color", ColorRGBA.Red);   // set color of material to blue
            lineGeometry.setMaterial(mat);
            rootNode.attachChild(lineGeometry);
    }
    
    
    public void collision(PhysicsCollisionEvent event) {
        Spatial nodeA = event.getNodeA();
        Spatial nodeB = event.getNodeB();
        float x1 = event.getPositionWorldOnA().x;
        float y1 = event.getPositionWorldOnA().y;
        float z1 = event.getPositionWorldOnA().z;        
        float x2 = event.getPositionWorldOnB().x;
        float y2 = event.getPositionWorldOnB().y;
        float z2 = event.getPositionWorldOnB().z;
        
      if(event.getNodeA().getName().equals("blindPerson") ){
        if ( event.getNodeB().getName().equals("SampleStaticObject") ) {
            //System.out.println("collision "+nodeB + " at " + event.getLocalPointB());
            //System.out.println(blindPerson.getPosition());
        }
        else if ( event.getNodeB().getName().equals("street") ) {
            //System.out.println("collision "+nodeB + " at " + event.getLocalPointB());
        } 
        else if ( event.getNodeB().getName().equals("staticObject1")) {
            System.out.println("collision mit " + event.getNodeB().getName() + " in " + event.getDistance1());
        }
        else if ( event.getNodeB().getName().equals("grass") ) {
            //System.out.println("x1 " + x1 + "z1 " + z1+"x2 "+ x2+"z2 "+ z2);   
             
        } else if ( event.getNodeB().getName().equals("buergersteig") ) {
            //System.out.println("x1 " + x1 + "z1 " + z1+"x2 "+ x2+"z2 "+ z2);   
          
        }  
    }   
      else if(event.getNodeA().getName().equals("staticObject") ){
        //drawLine(x1, 1f, z1, x2, 1f, z2);

      }
    }
    
}