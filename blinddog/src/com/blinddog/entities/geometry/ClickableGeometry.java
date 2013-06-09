package com.blinddog.entities.geometry;

import com.blinddog.eventsystem.interfaces.Clickable3D;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

/**
 * The class ClickableGeometry for geometry that listens to mouse-events.
 * @author Hans Ferchland
 * @version 0.2
 */
public abstract class ClickableGeometry extends Geometry implements Clickable3D {

    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================
    /**
     * Create a geometry node without any mesh data.
     * Both the mesh and the material are null, the geometry
     * cannot be rendered until those are set.
     * 
     * @param name The name of this geometry
     */
    public ClickableGeometry(String name) {
        super(name);
    }

    /**
     * Create a geometry node with mesh data.
     * The material of the geometry is null, it cannot
     * be rendered until it is set.
     * 
     * @param name The name of this geometry
     * @param mesh The mesh data for this geometry
     */
    public ClickableGeometry(String name, Mesh mesh) {
        super(name, mesh);
    }
}