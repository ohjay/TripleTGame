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
    public void keyPressed(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        
        if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = true;
        }
        
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downKeyPressed = true;
        }
    }
    
    /**
     * The method that will be called whenever a key is released.
     * This should be overridden by subclasses for more custom control behavior.
     * @param evt a KeyEvent representing the key that was released.
     */
    public void keyReleased(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        
        if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false;
        }
        
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downKeyPressed = false;
        }
    }
}
