package TripleT;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

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
    //================================================================================
    // Properties
    //================================================================================
    
    private static final Image R_SPRITESHEET = Images.get("kirbySS"), 
            L_SPRITESHEET = Images.get("kirbySS-1");
    
    // Animation data: for each animation, this enum contains information about the length 
    // of each animation sequence and its position (measured by pixels) on the spritesheet.
    // It also keeps track of the sprite width and the distance between different frames.
    static enum Animation { 
        STANDING(2, 0, 1, 25, 25), WALKING(10, 1, 97, 22, 24), RUNNING(8, 3, 121, 28, 25),
                CROUCHING(2, 4, 23, 28, 25), SLIDING(2, 4, 47, 30, 28), HOPPING(10, 2, 68, 25, 25),
                FLOATING(8, 2, 173, 28, 25);
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
    
    private static final int BLINK_TIME = 14, FRAME_DELAY = 12, SS_WIDTH = 641;
    private Animation currAnimation = Animation.STANDING;
    int spriteWidth = currAnimation.getSpriteWidth();
    private int currFrame = 0, counter = 0, noBlinkPeriod = 500;
    private boolean facingLeft, inAir;
    
    //================================================================================
    // Constructors
    //================================================================================
    
    /**
     * A constructor for a Kirby that will place him at the given coordinates on the screen.
     */
    public Kirby(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    //================================================================================
    // Accessors (for external management or animation)
    //================================================================================
    
    /**
     * Setter method for Kirby's position on the x-axis.
     * @param x the position that Kirby should be set to
     */
    void setX(int x) {
        this.x = x;
    }
    
    /**
     * Setter method for Kirby's position on the y-axis.
     * @param y the y-coordinate for Kirby to take on
     */
    void setY(int y) {
        this.y = y;
    }
    
    /**
     * Setter method for Kirby's horizontal delta value.
     * To be used to control movement (ex. for animation).
     * @param dx the new delta-x value
     */
    void setDX(int dx) {
        this.dx = dx;
    }
    
    /**
     * Setter method for Kirby's vertical delta value.
     * @param dy the new delta-y value
     */
    void setDY(int dy) {
        this.dy = dy;
    }
    
    /**
     * This method sets whether or not Kirby is facing left.
     * @param facingLeft if true, Kirby will be facing left after this method is executed
     */
    void setOrientation(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }
    
    /**
     * Updates Kirby's current animation to ANIMATION.
     * @param animation Kirby's new animation
     */
    void setAnimation(Animation animation) {
        this.currAnimation = animation;
    }
    
    //================================================================================
    // Drawing and animation methods
    //================================================================================
    
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
        // Draw Kirby! Note: the 3s are offsets
        int sy1 = currAnimation.getY(); // the source y-coordinate (from the spritesheet)
        if (!facingLeft) { // aka... facing right
            int sx1 = currAnimation.getX() + currFrame * currAnimation.getFrameDist() 
                    + (currAnimation.getFrameDist() - spriteWidth);
            g2.drawImage(R_SPRITESHEET, x, y, x + spriteWidth, y + spriteWidth, 
                    sx1 + 3, sy1, sx1 + spriteWidth + 3, sy1 + spriteWidth, null);
        } else {
            // Draw the reversed version of Kirby
            int sx1 = SS_WIDTH - currAnimation.getX() 
                    - (currFrame + 1) * currAnimation.getFrameDist()
                    - (currAnimation.getFrameDist() - spriteWidth);
            g2.drawImage(L_SPRITESHEET, x, y, x + spriteWidth, y + spriteWidth,
                    sx1 - 1, sy1, sx1 + spriteWidth - 1, sy1 + spriteWidth, null);
        }
    }
    
    //================================================================================
    // Key input handlers
    //================================================================================
    
    @Override
    public void keyPressed(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        
        if (keyCode == GameState.pInfo.rightKey) {
            rightKeyPressed = true;
            currAnimation = Animation.WALKING;
            spriteWidth = Animation.WALKING.getSpriteWidth();
            facingLeft = false;
            dx = 1;
        } else if (keyCode == GameState.pInfo.leftKey) {
            leftKeyPressed = true;
            currAnimation = Animation.WALKING;
            spriteWidth = Animation.WALKING.getSpriteWidth();
            facingLeft = true;
            dx = -1;
        } else if (keyCode == GameState.pInfo.downKey) {
            downKeyPressed = true;
            if (!inAir) {
                currAnimation = Animation.CROUCHING;
                spriteWidth = Animation.CROUCHING.getSpriteWidth();
                dx = 0;
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        
        if (keyCode == GameState.pInfo.leftKey) {
            leftKeyPressed = false;
            if (rightKeyPressed) {
                dx = 1;
                facingLeft = false;
            } else {
                currAnimation = Animation.STANDING;
                spriteWidth = Animation.STANDING.getSpriteWidth();
                dx = 0;
            }
        } else if (keyCode == GameState.pInfo.rightKey) {
            rightKeyPressed = false;
            if (leftKeyPressed) {
                dx = -1;
                facingLeft = true;
            } else {
                currAnimation = Animation.STANDING;
                spriteWidth = Animation.STANDING.getSpriteWidth();
                dx = 0; 
            }
        } else if (keyCode == GameState.pInfo.downKey) {
            downKeyPressed = false;
            if (currAnimation == Animation.CROUCHING) {
                currAnimation = Animation.STANDING;
                spriteWidth = Animation.STANDING.getSpriteWidth();
            }
        }
    }
}
