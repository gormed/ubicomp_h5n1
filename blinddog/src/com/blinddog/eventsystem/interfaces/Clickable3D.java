package com.blinddog.eventsystem.interfaces;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector2f;

/**
 * This interface is for Spatials that can be added into scenegraph 
 * for 3d clicking.
 * @author Hans Ferchland
 */
public interface Clickable3D {

    /**
     * This method is executed if the Spatial implementing this method is clicked.
     * Add the given spatial to the ScreenRayCast3D-Singleton to be clickable!
     * 
     * @param mouse the position of the mouse when the click happened
     * @param result the clicking result, including 
     * 3d-point, normal and some more
     */
    public void onRayCastClick(Vector2f mouse, CollisionResult result);

    /**
     * This method is executed if the Spatial implementing this method is hovered
     * by the mouse-pointer. 
     * Add the given spatial to the ScreenRayCast3D-Singleton to be hoverable!
     * 
     * @param mouse the position of the mouse when the hovering happened
     * @param result the clicking result, including 
     * 3d-point, normal and some more
     */
    public void onRayCastMouseOver(Vector2f mouse, CollisionResult result);

    /**
     * This method is executed if the Spatial implementing this method is no
     * longer hovered by the mouse-pointer.
     * Add the given spatial to the ScreenRayCast3D-Singleton to be hoverable!
     * 
     * @param mouse mouse the position of the mouse when the hovering happened
     * @param result the clicking result, including 
     * 3d-point, normal and some more
     */
    public void onRayCastMouseLeft(Vector2f mouse, CollisionResult result);
}
