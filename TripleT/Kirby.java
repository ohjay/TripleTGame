package TripleT;

import java.awt.Image;
import java.awt.Graphics2D;

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
                CROUCHING(2, 4, 23, 28, 25), SLIDING(2, 4, 46, 30, 25, 28), HOPPING(10, 2, 68, 25, 25),
                FLOATING(8, -3, 173, 28, 30), FALLING(7, 1, 2, 262, 25, 26, 25);
        private final int length, rightX, leftX, y, spriteWidth, spriteHeight, frameDist;
        
        /**
         * In this constructor, spriteHeight is assumed to be the same as spriteWidth.
         */
        private Animation(int length, int x, int y, int spriteWidth, int frameDist) {
            this.length = length;
            this.rightX = x;
            this.leftX = x;
            this.y = y;
            this.spriteWidth = spriteWidth;
            this.spriteHeight = spriteWidth;
            this.frameDist = frameDist;
        }
        
        private Animation(int length, int x, int y, int spriteWidth, int spriteHeight, int frameDist) {
            this.length = length;
            this.rightX = x;
            this.leftX = x;
            this.y = y;
            this.spriteWidth = spriteWidth;
            this.spriteHeight = spriteHeight;
            this.frameDist = frameDist;
        }
        
        private Animation(int length, int rightX, int leftX, int y, int spriteWidth, int spriteHeight, int frameDist) {
            this.length = length;
            this.rightX = rightX;
            this.leftX = leftX;
            this.y = y;
            this.spriteWidth = spriteWidth;
            this.spriteHeight = spriteHeight;
            this.frameDist = frameDist;
        }
        
        private int getLength() { return length; }
        private int getRightX() { return rightX; }
        private int getLeftX() { return leftX; }
        private int getY() { return y; }
        private int getSpriteWidth() { return spriteWidth; }
        private int getSpriteHeight() { return spriteHeight; }
        private int getFrameDist() { return frameDist; }
    };
    
    private static final int BLINK_TIME = 20, SLIDING_TIME = 50, FRAME_DELAY = 12, SS_WIDTH = 641;
    private Animation currAnimation = Animation.STANDING;
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
        spriteWidth = currAnimation.getSpriteWidth();
        spriteHeight = currAnimation.getSpriteHeight();
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
     * Freezes Kirby momentarily (i.e. stops all positional movement).
     */
    void halt() {
        this.dx = 0;
        this.dy = 0;
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
        currAnimation = animation;
        spriteWidth = animation.getSpriteWidth();
        spriteHeight = animation.getSpriteHeight();
    }
    
    /**
     * Sets the "in air" indicator to be true if Kirby is in the air,
     * and false if Kirby is not (i.e. Kirby is on the ground). This method
     * should be called by the level panel (since only the level panel knows
     * at what height the ground is located).
     */
    void toggleInAir() {
        inAir = !inAir;
    }
    
    /**
     * Returns true if Kirby is in the air, and false otherwise.
     */
    boolean isInAir() {
        return inAir;
    }
    
    //================================================================================
    // Drawing and animation methods
    //================================================================================
    
    /**
     * Switches to the next frame in the current animation (if the time is right).
     * Returns true if the switch actually happens.
     */
    private boolean nextFrame() {
        if (counter % FRAME_DELAY == 0) {
            currFrame = (currFrame + 1) % currAnimation.getLength();
            return true;
        } else { return false; }
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
                if (currFrame == 1) {
                    if (counter > BLINK_TIME) {
                        currFrame = 0; // open Kirby's eyes
                        counter = 0;
                        return true;
                    } else {
                        return false;
                    }
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
                return nextFrame();
            case FLOATING:
                if (inAir) {
                    return nextFrame();
                } else {
                    dy = 0;
                    if (rightKeyPressed || leftKeyPressed) {
                        setAnimation(Animation.WALKING);
                    } else {
                        setAnimation(Animation.STANDING);
                    }
                    return true;
                }
            case FALLING:
                if (inAir) {
                    if (currFrame < 6) { 
                        // 6 is the last frame of the falling animation
                        return nextFrame(); 
                    } else { return false; }
                } else {
                    dy = 0;
                    if (rightKeyPressed || leftKeyPressed) {
                        setAnimation(Animation.WALKING);
                    } else {
                        setAnimation(Animation.STANDING);
                    }
                    return true;
                }
            case SLIDING:
                if (counter > SLIDING_TIME) {
                    dx = 0;
                    if (downKeyPressed) {
                        setAnimation(Animation.CROUCHING);
                    } else if (rightKeyPressed || leftKeyPressed) {
                        setAnimation(Animation.WALKING);
                    } else {
                        setAnimation(Animation.STANDING);
                    }
                    
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
            int sx1 = currAnimation.getRightX() + currFrame * currAnimation.getFrameDist() 
                    + (currAnimation.getFrameDist() - spriteWidth);
            g2.drawImage(R_SPRITESHEET, x, y, x + spriteWidth, y + spriteHeight, 
                    sx1 + 3, sy1, sx1 + spriteWidth + 3, sy1 + spriteHeight, null);
        } else {
            // Draw the reversed version of Kirby
            int sx1 = SS_WIDTH - currAnimation.getLeftX() 
                    - (currFrame + 1) * currAnimation.getFrameDist()
                    - (currAnimation.getFrameDist() - spriteWidth);
            g2.drawImage(L_SPRITESHEET, x, y, x + spriteWidth, y + spriteHeight,
                    sx1 - 1, sy1, sx1 + spriteWidth - 1, sy1 + spriteHeight, null);
        }
    }
    
    //================================================================================
    // Action methods for input response
    //================================================================================
    
    @Override
    public void aPressed() {
        if (currAnimation == Animation.CROUCHING) {
            currFrame = 0;
            counter = 0; // (to begin the sliding time count)
            setAnimation(Animation.SLIDING);
            dx = (facingLeft) ? -2 : 2;
        }
    }
    
    @Override
    public void rightPressed() {
        rightKeyPressed = true;
        facingLeft = false;
        dx = 1;
        
        if (!inAir) {
            setAnimation(Animation.WALKING);
        }
    }
    
    @Override
    public void leftPressed() {
        leftKeyPressed = true;
        facingLeft = true;
        dx = -1;
        
        if (!inAir) {
            setAnimation(Animation.WALKING);
        }
    }
    
    @Override
    public void downPressed() {
        downKeyPressed = true;
        if (!inAir) {
            currFrame = 0;
            setAnimation(Animation.CROUCHING);
            dx = 0;
        }
    }
    
    @Override
    public void upPressed() {
        upKeyPressed = true;
        setAnimation(Animation.FLOATING);
        dy = -1;
        if (!inAir) {
            inAir = true;
            y -= 1;
        }
    }
    
    @Override
    public void rightReleased() {
        rightKeyPressed = false;
        if (leftKeyPressed) {
            dx = -1;
            facingLeft = true;
        } else {
            currFrame = 0;
            dx = 0;
            if (!inAir) {
                if (downKeyPressed) {
                    setAnimation(Animation.CROUCHING);
                } else {
                    setAnimation(Animation.STANDING);
                }
            }
        }
    }
    
    @Override
    public void leftReleased() {
        leftKeyPressed = false;
        if (rightKeyPressed) {
            dx = 1;
            facingLeft = false;
        } else {
            currFrame = 0;
            dx = 0;
            if (!inAir) {
                if (downKeyPressed) {
                    setAnimation(Animation.CROUCHING);
                } else {
                    setAnimation(Animation.STANDING);
                }
            }
        }
    }
    
    @Override
    public void downReleased() {
        downKeyPressed = false;
        if (currAnimation == Animation.CROUCHING) {
            if (rightKeyPressed || leftKeyPressed) {
                setAnimation(Animation.WALKING);
                dx = (facingLeft) ? -1 : 1;
            } else {
                setAnimation(Animation.STANDING);
            }
        }
    }
    
    @Override
    public void upReleased() {
        upKeyPressed = false;
        if (inAir) {
            currFrame = 0;
            counter = 0;
            setAnimation(Animation.FALLING);
            dy = 1;
        }
    }
}
