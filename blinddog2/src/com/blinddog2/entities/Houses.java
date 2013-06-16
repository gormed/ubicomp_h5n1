/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog2.entities;

import com.blinddog2.main.Main;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author hady
 */
public class Houses {
    private Spatial model;
    private RigidBodyControl landscape;

    public Houses(){
        
        createModel();
    }
    
    private void createModel(){
         // load street Model
    model = Main.getInstance().getAssetManager().loadModel("Scenes/houses.j3o");
    model.setName("houses");
    model.setLocalScale(2f);
    CollisionShape cs =
            CollisionShapeFactory.createMeshShape((Node) model);
    landscape = new RigidBodyControl(cs, 0);
    model.addControl(landscape);  
    Main.getInstance().getRootNode().attachChild(model);
    Main.getInstance().getBulletAppState().getPhysicsSpace().add(landscape);
    }
        public Spatial getModel() {
        return model;
    }

    public RigidBodyControl getLandscape() {
        return landscape;
    }
}

