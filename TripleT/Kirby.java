package TripleT;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * This is story-mode Kirby in all his glory. As a controllable sprite, 
 * Kirby can be directed with the keyboard and will display movement
 * animations based on his spritesheet.
 * 
 * Incidentally, Kirby's spritesheet was obtained from spriters-resource.com
 * and was ripped by A.J. Nitro. Props to him!
 * @author Owen Jow
 */
public class Kirby extends ControllableSprite {
    private static final Image SPRITESHEET = Images.get("kirbySS");
    
    // Animation data: for each animation, this enum contains information about the length 
    // of each animation sequence and its position (measured by pixels) on the spritesheet.
    // It also keeps track of the sprite width and the distance between different frames.
    private static enum Animation { 
        STANDING(2, 0, 1, 25, 25), WALKING(10, 1, 97, 22, 24);
        private final int length, x, y, spriteWidth, frameDist;
        
        private Animation(int length, int x, int y, int spriteWidth, int frameDist) {
            this.length = length;
            this.x = x;
            this.y = y;
            this.spriteWidth = spriteWidth;
            this.frameDist = frameDist;
        }
        
        private int getLength() { return length; }
        private int getX() { return x; }
        private int getY() { return y; }
        private int getSpriteWidth() { return spriteWidth; }
        private int getFrameDist() { return frameDist; }
    };
    
    private static final int BLINK_TIME = 14, FRAME_DELAY = 12;
    private Animation currAnimation = Animation.STANDING;
    int spriteWidth = currAnimation.getSpriteWidth();
    private int currFrame = 0, counter = 0, noBlinkPeriod = 500;
    
    /**
     * A constructor for a Kirby that will place him at the given coordinates on the screen.
     */
    public Kirby(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * This method will update the frame for Kirby's current animation.
     * If the frame changes, it will return true (so that the receiving class
     * or function knows to repaint the entire display).
     */
    boolean updateFrame() {
        counter++; // update the counter every time this method is called
        switch (currAnimation) {
            case STANDING:
                if (currFrame == 1 && counter > BLINK_TIME) {
                    currFrame = 0; // open Kirby's eyes
                    counter = 0;
                    return true;
                } else if (currFrame == 0 && counter > noBlinkPeriod) {
                    currFrame = 1; // change to a blinking state (eyes closed)
                    noBlinkPeriod = (int) (Math.random() * 750) + 50;
                    counter = 0;
                    return true;
                } else {
                    currFrame = 0;
                    return true;
                }
            case WALKING:
                if (counter % FRAME_DELAY == 0) {
                    currFrame = (currFrame + 1) % currAnimation.getLength();
                    return true;
                } else { return false; }
            default:
                return false;
        }
    }
    
    /**
     * Draws Kirby on the screen, as one of the images from the spritesheet.
     * Kirby will be depicted in his present state, whatever that might be.
     * @param g2 a Graphics2D object used for painting
     */
    public void drawImage(Graphics2D g2) {
        int sx1 = currAnimation.getX() + currFrame * currAnimation.getFrameDist() 
                + (currAnimation.getFrameDist() - spriteWidth);
        int sy1 = currAnimation.getY();
        
        // Draw Kirby! Note: the 1s are offsets
        g2.drawImage(SPRITESHEET, x, y, x + spriteWidth, y + spriteWidth, 
                sx1 + 3, sy1, sx1 + spriteWidth + 3, sy1 + spriteWidth, null);
    }
    
    @Override
    public void keyPressed(KeyEvent evt) {
        super.keyPressed(evt);
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                currAnimation = Animation.WALKING;
                spriteWidth = Animation.WALKING.getSpriteWidth();
                dx = 1;
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent evt) {
        super.keyPressed(evt);
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                currAnimation = Animation.STANDING;
                spriteWidth = Animation.STANDING.getSpriteWidth();
                dx = 0;
                break;
        }
    }
}
