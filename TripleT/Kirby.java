package TripleT;

import java.awt.Image;
import java.awt.Graphics2D;
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
    
    // Animation index numbers
    private static enum Animation { 
        STANDING(0), WALKING(4);
        private int index;
        
        private Animation(int index) {
            this.index = index;
        }
        
        public int getIndex() { return index; }
    };
    
    // The length of each animation sequence, numbered by their index values
    private static enum AniLengths { 
        STANDING(2), WALKING(10);
        private int length;
        
        private AniLengths(int length) {
            this.length = length;
        }
        
        private int getLength() { return length; }
    };
    
    private static final int FRAME_WIDTH = 25, BLINK_TIME = 12;
    private Animation currAnimation = Animation.STANDING;
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
                    noBlinkPeriod = (int) (Math.random() * 500) + 100;
                    counter = 0;
                    return true;
                } else {
                    return false;
                }
            case WALKING:
                return false;
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
        int dx1 = currFrame * FRAME_WIDTH;
        int dy1 = currAnimation.getIndex() * FRAME_WIDTH;
        
        // Draw Kirby!
        g2.drawImage(SPRITESHEET, x, y, x + FRAME_WIDTH, y + FRAME_WIDTH, 
                dx1, dy1, dx1 + FRAME_WIDTH, dy1 + FRAME_WIDTH, null);
    }
}
