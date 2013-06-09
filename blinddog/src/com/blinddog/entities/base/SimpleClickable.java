package com.blinddog.entities.base;

import com.blinddog.entities.nodes.ClickableEntityNode;

/**
 * The inteface SimpleClickable for clickable entities.
 * @author Hans Ferchland
 */
public interface SimpleClickable {

    /**
     * Gets the geometry that is clickable and will raise the given events if 
     * clicked.
     * @return the node with the geometry that will be clickable
     */
    public ClickableEntityNode getClickableEntityNode();

    /**
     * Is called if geometry is clicked.
     */
    public void onClick();

    /**
     * Is called if geometry is hovered.
     */
    public void onMouseOver();

    /**
     * Is called if geometry is not hovered anymore.
     */
    public void onMouseLeft();
}
