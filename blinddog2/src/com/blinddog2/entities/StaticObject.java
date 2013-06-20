/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog2.entities;

import com.blinddog2.main.Main;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author hady
 */
public class StaticObject extends AbstractEntity {
    private Spatial model;
    private Vector3f position;

    
    public StaticObject(String name){
        super(name);
        this.position = new Vector3f(0f,0f,0f);
        createModel(name,position);
    }
    
    
    public StaticObject(String name, Vector3f position){
        super(name);
        this.position = position;
        createModel(name, position);
    }
    
    private void createModel(String name, Vector3f position){

        
    Sphere sphere = new Sphere(30, 30, 2f); 
   model = new Geometry(name, sphere);
   Material mat = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"); 
    model.setMaterial(mat);
    Main.getInstance().getRootNode().attachChild(model);
    /** Position the brick geometry  */
    model.setLocalTranslation(position);
    /** Make brick physical with a mass > 0.0f. */
    RigidBodyControl modelPhy = new RigidBodyControl(2f);
    /** Add physical brick to physics space. */

    model.addControl(modelPhy);
    modelPhy.setMass(0.2f);
     Main.getInstance().getBulletAppState().getPhysicsSpace().add(modelPhy);
    
    
  
    
    
    }
        public Spatial getModel() {
        return model;
    }

 
    public Vector3f getPosition() {
        return position;
    }
    

  

    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    
    public void update(float tpf){
        setPosition(model.getLocalTranslation());
    }

  
}


