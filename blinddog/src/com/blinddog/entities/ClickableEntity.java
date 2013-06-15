package com.blinddog.entities;

import com.blinddog.entities.base.AbstractEntity;
import com.blinddog.entities.base.SimpleClickable;
import com.jme3.scene.Node;
import com.blinddog.entities.nodes.ClickableEntityNode;
import com.blinddog.main.Main;

/**
 * The class ClickableEntity.
 * @author Hans Ferchland
 * @version 1.0
 */
public abstract class ClickableEntity extends AbstractEntity implements SimpleClickable {

    //==========================================================================
    //===   Private Fields
    //==========================================================================
    /** The clickable entity node. */
    protected ClickableEntityNode clickableEntityNode;
    //==========================================================================
    //===   Methods & Constructor
    //==========================================================================

    /**
     * Constructs an ClickableEntity by a given name.
     *
     * @param name the desired name
     */
    public ClickableEntity(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see entities.base.AbstractEntity#createNode(mazetd.MazeTDGame)
     */
    @Override
    public Node createNode(Main game) {
        
        clickableEntityNode =
                new ClickableEntityNode(name + "s_GeometryNode", this);
        super.createNode(game).attachChild(clickableEntityNode);
        
        return clickableEntityNode;
    }

    /* (non-Javadoc)
     * @see entities.base.low.SimpleClickable#getClickableEntityNode()
     */
    @Override
    public ClickableEntityNode getClickableEntityNode() {
        return clickableEntityNode;
    }

    /**
     * Is called if geometry is clicked.
     */
    @Override
    public abstract void onClick();

    /**
     * Is called if geometry is hovered.
     */
    @Override
    public abstract void onMouseOver();

    /**
     * Is called if geometry is not hovered anymore.
     */
    @Override
    public abstract void onMouseLeft();
}
