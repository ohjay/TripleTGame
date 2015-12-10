package TripleT;

import java.awt.Rectangle;
import java.awt.Graphics2D;

/**
 * Kirby in his Level 2 form, which is different than most other forms
 * in that he's being pulled into the air and therefore requires less controls + less animations.
 * @author Owen Jow
 */
public class Level2Kirby extends Kirby {
    private static final int FLY_FACTOR = 3, L2_FRAME_DEL = 6;
    
    public Level2Kirby(int x, int y, Animation animation) {
        super(x, y, animation);
    }
    
    @Override
    public void move() {
        if (x + dx >= 0 && x + dx + spriteWidth <= TripleTWindow.SCR_WIDTH) {
            x += dx;
        }
    }
    
    /**
     * A level 2 thing. This is what Kirby does when he fails to clog the vacuum in time.
     */
    public void flyUpward() {
        y -= FLY_FACTOR;
    }
    
    @Override
    boolean updateFrame() {
        counter++;
        if (counter % L2_FRAME_DEL == 0) {
            currFrame = (currFrame + 1) % currAnimation.getLength();
            return true;
        } else { return false; }
    }
    
    @Override
    public void rightPressed() {
        rightKeyPressed = true;
        facingLeft = false;
        dx = 1;
    }
    
    @Override
    public void leftPressed() {
        leftKeyPressed = true;
        facingLeft = true;
        dx = -1;
    }
    
    public void rightReleased() {
        rightKeyPressed = false;
        if (leftKeyPressed) {
            dx = -1;
            facingLeft = true;
        } else {
            dx = 0;
        }
    }
    
    public void leftReleased() {
        leftKeyPressed = false;
        if (rightKeyPressed) {
            dx = 1;
            facingLeft = false;
        } else {
            dx = 0;
        }
    }
}
