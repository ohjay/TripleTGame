package TripleT;

import java.awt.event.KeyEvent;

/**
 * A sprite that can be controlled by the player (via the keyboard).
 * @author Owen Jow
 */
abstract class ControllableSprite extends Sprite {
    protected boolean leftKeyPressed, rightKeyPressed, upKeyPressed, downKeyPressed;
    
    /**
     * The method that will be called whenever a key is pressed.
     * This should be overridden by subclasses for more custom control behavior.
     * @param evt a KeyEvent representing the key that was pressed
     */
    abstract void keyPressed(KeyEvent evt);
    
    /**
     * The method that will be called whenever a key is released.
     * This should be overridden by subclasses for more custom control behavior.
     * @param evt a KeyEvent representing the key that was released.
     */
    abstract void keyReleased(KeyEvent evt);
}
