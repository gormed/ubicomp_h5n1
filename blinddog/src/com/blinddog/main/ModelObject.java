/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog.main;

import com.jme3.asset.AssetManager;
import com.jme3.collision.Collidable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author hady
 */
public class ModelObject extends Node{
    private final Node rootNode;
    private final AssetManager assetManager;
    
    public ModelObject(String name, Node root, AssetManager assetManager){
        super(name);
        this.rootNode = root;
        this.assetManager = assetManager;
        createGeometry();
    }


    private void createGeometry() {
    rootNode.attachChild(this); 
    Box b = new Box(new Vector3f(10,0,-10), 2, 2, 20); // create cube shape at the origin
    Geometry geom = new Geometry("blindPerson", b);  // create cube geometry from the shape
    Material mat = new Material(assetManager,
      "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
    mat.setColor("Color", ColorRGBA.Red);   // set color of material to blue
    geom.setMaterial(mat);                   // set the cube's material
    this.attachChild(geom); 
    }
}
