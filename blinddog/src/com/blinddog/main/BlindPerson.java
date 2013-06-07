/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blinddog.main;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author hady
 */
public class BlindPerson extends Node {
    
    private Vector3f position;
    private float orientation;
    
    public BlindPerson(String name){
        
    }
    public void setPosition(Vector3f pos){
        this.position = pos;
    }
    
    public Vector3f getPosition(){
        return this.getLocalTranslation();
    }


    
}
