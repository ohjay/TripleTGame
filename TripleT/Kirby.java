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
        STANDING(2, 0, 1, 25, 25), WALKING(10, 1, 97, 22, 24), RUNNING(8, 3, 6, 121, 25, 25, 24),
                CROUCHING(2, 1, 26, 28, 25, 30), SLIDING(2, 4, 46, 30, 25, 28), HOPPING(10, 2, 68, 25, 25),
                FLOATING(8, -3, 173, 28, 30), FALLING(7, 1, 2, 262, 25, 26, 25),
                ENTERING(4, 2, 347, 22, 24), SOMERSAULTING(12, 50, 266, 25, 26);
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
        
        private Animation(int length, int rightX, int leftX, int y, int spriteWidth, 
                int spriteHeight, int frameDist) {
            this.length = length;
            this.rightX = rightX;
            this.leftX = leftX;
            this.y = y;
            this.spriteWidth = spriteWidth;
            this.spriteHeight = spriteHeight;
            this.frameDist = frameDist;
        }
        
        protected int getLength() { return length; }
        private int getRightX() { return rightX; }
        private int getLeftX() { return leftX; }
        private int getY() { return y; }
        protected int getSpriteWidth() { return spriteWidth; }
        protected int getSpriteHeight() { return spriteHeight; }
        private int getFrameDist() { return frameDist; }
    };
    
    private static final int BLINK_TIME = 20, SLIDING_TIME = 50, FRAME_DELAY = 12, 
            SS_WIDTH = 641, DASH_TIME_LIMIT = 100;
    protected Animation currAnimation = Animation.STANDING;
    protected int currFrame = 0, counter = 0, noBlinkPeriod = 500;
    private long lastDashKeyPress; // information about the last left/right key press
    protected boolean facingLeft, inAir, entered;
    int prevHeight = currAnimation.getSpriteHeight();
    
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
    
    /**
     * Does the same as the above, while also explicitly setting the current animation.
     */
    public Kirby(int x, int y, Animation animation) {
        this.x = x;
        this.y = y;
        currAnimation = animation;
        spriteWidth = currAnimation.getSpriteWidth();
        spriteHeight = currAnimation.getSpriteHeight();
    }
    
    //================================================================================
    // Accessors (for external management or animation)
    //================================================================================
    
    /**
     * This method sets whether or not Kirby is facing left.
     * @param facingLeft if true, Kirby will be facing left after this method is executed
     */
    void setOrientation(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }
    
    /**
     * Returns Kirby's current animation.
     */
    Animation getAnimation() {
        return currAnimation;
    }
    
    /**
     * Updates Kirby's current animation to ANIMATION.
     * @param animation Kirby's new animation
     */
    void setAnimation(Animation animation) {
        prevHeight = spriteHeight;
        currAnimation = animation;
        spriteWidth = animation.getSpriteWidth();
        spriteHeight = animation.getSpriteHeight();
    }
    
    /**
     * Returns true if Kirby's current animation is ANIMATION.
     */
    boolean animationEquals(Animation animation) {
        return (animation == currAnimation);
    }
    
    /**
     * Getter method for Kirby's current animation frame.
     */
    int getFrame() {
        return currFrame;
    }
    
    /**
     * Sets the current frame of the animation to be FRAME_NUM.
     * Numbering begins at 0.
     */
    void setCurrentFrame(int frameNum) {
        this.currFrame = frameNum;
    }
    
    void enterDoor() {
        setAnimation(Animation.ENTERING);
        setCurrentFrame(0);
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
    
    /**
     * Changes the "entered" attribute to false if currently true,
     * and true if currently false.
     * 
     * Should be called after Kirby enters a door.
     */
    void toggleEntered() {
        entered = !entered;
    }
    
    /**
     * Under the assumption that Kirby is/was entering a door (or SOME enter-able object),
     * this method indicates whether or not Kirby has completed the entering animation.
     */
    boolean hasEntered() {
        return entered;
    }
    
    /**
     * For flying upward, if any subclasses of Kirby desire this behavior.
     */
    public void flyUpward() {
        /* Implement "fly upward" behavior if you want it! */
    }
    
    //================================================================================
    // Drawing and animation methods
    //================================================================================
    
    /**
     * Switches to the next frame in the current animation (if the time is right).
     * Returns true if the switch actually happens.
     */
    protected boolean nextFrame() {
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
            case CROUCHING:
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
            case RUNNING:
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
                    if (downKeyPressed) {
                        setAnimation(Animation.CROUCHING);
                        currFrame = 0;
                    } else if (rightKeyPressed || leftKeyPressed) {
                        setAnimation(Animation.WALKING);
                    } else {
                        dx = 0;
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
            case ENTERING:
                if (currFrame < 3) {
                    // 3 being the final frame of the ENTERING animation
                    return nextFrame();
                } else if (currFrame == 3) {
                    entered = true; // signal that Kirby has entered the door
                    return false; // then return false, since the animation didn't change
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
            if (currAnimation == Animation.CROUCHING) {
                // Offset intervention
                g2.drawImage(R_SPRITESHEET, x, y + 4, x + spriteWidth, y + 4 + spriteHeight, 
                        sx1 + 3, sy1, sx1 + spriteWidth + 3, sy1 + spriteHeight, null);
            } else {
                g2.drawImage(R_SPRITESHEET, x, y, x + spriteWidth, y + spriteHeight, 
                        sx1 + 3, sy1, sx1 + spriteWidth + 3, sy1 + spriteHeight, null);
            }
        } else {
            // Draw the reversed version of Kirby
            int sx1 = SS_WIDTH - currAnimation.getLeftX() 
                    - (currFrame + 1) * currAnimation.getFrameDist()
                    - (currAnimation.getFrameDist() - spriteWidth);
            if (currAnimation == Animation.CROUCHING) {
                g2.drawImage(L_SPRITESHEET, x, y + 4, x + spriteWidth, y + 4 + spriteHeight,
                        sx1 - 1, sy1, sx1 + spriteWidth - 1, sy1 + spriteHeight, null);
            } else {
                g2.drawImage(L_SPRITESHEET, x, y, x + spriteWidth, y + spriteHeight,
                        sx1 - 1, sy1, sx1 + spriteWidth - 1, sy1 + spriteHeight, null);
            }
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
        
        if (dx <= 0) {
            // Was the right key just pressed? If so, Kirby should be running
            if (!inAir && (lastDashKeyPress & 1) == 1 &&
                    System.currentTimeMillis() - (lastDashKeyPress >> 1) < DASH_TIME_LIMIT) {
                // This is a dash input
                dx = 2;
                setAnimation(Animation.RUNNING);
            } else {
                dx = 1;
                if (!inAir) {
                    setAnimation(Animation.WALKING);
                }
            }
        }
    }
    
    @Override
    public void leftPressed() {
        leftKeyPressed = true;
        facingLeft = true;
        
        if (dx >= 0) {
            // Distinguish between walking and running
            if (!inAir && (lastDashKeyPress & 1) == 0 &&
                    System.currentTimeMillis() - (lastDashKeyPress >> 1) < DASH_TIME_LIMIT) {
                dx = -2;
                setAnimation(Animation.RUNNING);
            } else {
                dx = -1;
                if (!inAir) {
                    setAnimation(Animation.WALKING);
                }
            }
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
        if (Math.abs(dx) > 1) { dx /= 2; }
        if (!inAir) {
            y -= spriteHeight - prevHeight + 1;
            inAir = true;
        }
    }
    
    @Override
    public void rightReleased() {
        rightKeyPressed = false;
        lastDashKeyPress = (System.currentTimeMillis() << 1) | 1;
        // ^the 1 at the end signifies a right key release
        
        if (leftKeyPressed) {
            dx = -1;
            facingLeft = true;
            if (!inAir) {
                setAnimation(Animation.WALKING);
            }
        } else {
            dx = 0;
            if (!inAir) {
                currFrame = 0;
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
        lastDashKeyPress = System.currentTimeMillis() << 1;
        // ^the 0 at the end signifies a left key release
        
        if (rightKeyPressed) {
            dx = 1;
            facingLeft = false;
            if (!inAir) {
                setAnimation(Animation.WALKING);
            }
        } else {
            dx = 0;
            if (!inAir) {
                currFrame = 0;
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
