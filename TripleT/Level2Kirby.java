package TripleT;

import java.awt.Rectangle;
import java.awt.Graphics2D;

/**
 * Kirby in his Level 2 form, which is different than most other forms
 * because he's being pulled into the air and therefore requires less control + less animations.
 * @author Owen Jow
 */
public class Level2Kirby extends Kirby {
    
    public Level2Kirby(int x, int y, Animation animation) {
        super(x, y, animation);
    }
    
    @Override
    public void move() {
        if (x + dx >= 0 && x + dx + spriteWidth <= TripleTWindow.SCR_WIDTH) {
            x += dx;
        }
    }
    
    @Override
    boolean updateFrame() {
        counter++;
        return nextFrame();
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