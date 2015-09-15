package TripleT;

/**
 * A sprite that can be controlled by the player (via the keyboard).
 * @author Owen Jow
 */
abstract class ControllableSprite extends Sprite {
    protected boolean leftKeyPressed, rightKeyPressed, upKeyPressed, downKeyPressed;
    
    //================================================================================
    // Action methods (to be overridden by the actual sprite)
    //================================================================================
    
    abstract void rightPressed();
    abstract void leftPressed();
    abstract void downPressed();
    abstract void upPressed();
    abstract void aPressed();
    
    abstract void rightReleased();
    abstract void leftReleased();
    abstract void downReleased();
    abstract void upReleased();
}
