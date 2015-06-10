package TripleT; 

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * A Kirby to serve as the controllable model for Dodge!.
 * Visually, StarKirby will be represented by Kirby on a star, surfing over sky or space.
 * @author Owen Jow
 */
public class StarKirby {
    int numLives = 2, numPowerUps = 2;
    protected double xPos = 3, yPos = 3;
    protected int dx, dy;
    private boolean isFacingRight = true, rightKeyPressed, leftKeyPressed, 
            upKeyPressed, downKeyPressed;
    protected enum State { NORMAL, STARTLED, POWERED_UP, COOLING_DOWN };
    protected State state = State.NORMAL;
    private static final int HORIZ_OFFSET = 8, VERT_OFFSET = 5, WIDTH = 17, HEIGHT = 25,
            REPEL_DIST = 55;
    private static final int RIGHT_BORDER = DodgePanel.HORIZ_RIGHT_BORDER - WIDTH,
            LOWER_BORDER = DodgePanel.VERT_LOWER_BORDER - HEIGHT;
    private static final double SQRT_2_APPROX = 1.5;
    
    /**
     * Returns the graphical representation of Kirby's present condition.
     * @return the graphical representation of Kirby's present condition
     */
    public Image getImage() {
        if (isFacingRight) {
            switch (state) {
                case NORMAL:
                    return Images.get("rightStarKirby");
                case STARTLED:
                    return Images.get("rightStarTled");
                case POWERED_UP:
                    return Images.get("redStarRight");
                default:
                    return Images.get("orangeStarRight");
            }
        } else {
            switch (state) {
                case NORMAL:
                    return Images.get("leftStarKirby");
                case STARTLED:
                    return Images.get("leftStarTled");
                case POWERED_UP:
                    return Images.get("redStarLeft");
                default:
                    return Images.get("orangeStarLeft");
            }
        }
    }
    
    /**
     * Returns Kirby's x-coordinate as an integer.
     * @return the x-coordinate of this Star Kirby
     */
    public int getX() {
        return (int) xPos;
    }
    
    /**
     * Returns Kirby's y-coordinate as an integer.
     * @return the y-coordinate of this Star Kirby
     */
    public int getY() {
        return (int) yPos;
    }
    
    /**
     * Startles Kirby (graphically, Kirby will appear startled for a certain duration).
     * In terms of game mechanics, Kirby will also be invincible for this limited time.
     */
    public void startle() {
        state = State.STARTLED;
    }
    
    /** 
     * Returns true if Kirby is currently in a startled state.
     * @return a boolean representing Kirby's "startled" status
     */
    public boolean isStartled() {
        return (state == State.STARTLED);
    }
    
    /** 
     * Returns true if Kirby is currently powered up.
     * This includes red Kirby AND orange Kirby.
     * @return an indicator of whether or not Kirby is (especially) powerful right now
     */
    public boolean isPoweredUp() {
        return (state == State.POWERED_UP || state == State.COOLING_DOWN);
    }
    
    /**
     * Begins to cool Kirby down (also known as turning Kirby orange).
     * Note: Kirby is always cool.
     */
    public void coolDown() {
        state = State.COOLING_DOWN;
    }
    
    /**
     * Returns Kirby to a normal state of being.
     * (Note: this is still eons past our own normal states of being)
     */
    public void normalize() {
        state = State.NORMAL;
    }
    
    /**
     * Repels Kirby a certain distance away from the ghost situated at the given coordinates.
     * @param ghostX the x-coordinate of the ghost in question
     */
    public void repelFromGhost(int ghostX) {
        if (isFacingRight) {
            if (xPos - REPEL_DIST > 0) {
                xPos -= REPEL_DIST;
            } else {
                xPos += REPEL_DIST;
                isFacingRight = false;
            }
        } else {
            if (xPos + REPEL_DIST < RIGHT_BORDER) {
                xPos += REPEL_DIST;
            } else {
                xPos -= REPEL_DIST;
                isFacingRight = true;
            }
        }
    }
    
    /**
     * Returns the rectangular representation of Kirby in his present location.
     * @return the rectangular representation of Kirby in his present location
     */
    public Rectangle getRectangle() {
        return new Rectangle((int) xPos + HORIZ_OFFSET, (int) yPos + VERT_OFFSET, WIDTH, HEIGHT);
    }
    
    /**
     * Moves Kirby by updating positional coordinates. Also plays border patrol.
     */
    public void move() {
        // Update delta values if currently powered up. If not powered up, change them back
        // x values
        if (isPoweredUp() && Math.abs(dx) == 2) {
            dx = dx * 5 / 2;
        } else if (!isPoweredUp() && Math.abs(dx) == 5) {
            dx = dx * 2 / 5;
        }
        
        // y values
        if (isPoweredUp() && Math.abs(dy) == 2) {
            dy = dy * 5 / 2;
        } else if (!isPoweredUp() && Math.abs(dy) == 5) {
            dy = dy * 2 / 5;
        }
        
        // Perform actual movement
        if (dx == 0 || dy == 0) {
            if (withinHorizRange(xPos + dx) && withinVerticalRange(yPos + dy)) {
                xPos += dx;
                yPos += dy;
            }
        } else {
            // Prevent diagonal movement from being overpowered
            if (withinHorizRange(xPos + ((double) dx) / SQRT_2_APPROX)) 
                xPos += ((double) dx) / SQRT_2_APPROX; 
            if (withinVerticalRange(yPos + ((double) dy) / SQRT_2_APPROX)) 
                yPos += ((double) dy) / SQRT_2_APPROX;
        }
    }
    
    /**
     * Nullifies all key presses so that none of the keys are registered as pressed.
     * After this method is executed, Kirby should have no momentum whatsoever.
     */
    public void nullifyKeyPresses() {
        dx = 0;
        dy = 0;
        rightKeyPressed = false;
        leftKeyPressed = false;
        upKeyPressed = false;
        downKeyPressed = false;
    }
    
    /**
     * Checks whether or not the given x-coordinate is appropriate 
     * (i.e. within horizontal bounds for a Star Kirby).
     * @param xCoord an x-coordinate
     * @return true if the x-coordinate is contained in the max horizontal range
     */
    private boolean withinHorizRange(double xCoord) {
        return (xCoord > 0 && xCoord < RIGHT_BORDER);
    }
    
    /**
     * Checks whether or not the given y-coordinate is appropriate 
     * (i.e. within vertical bounds for a Star Kirby).
     * @param yCoord a y-coordinate
     * @return true if the y-coordinate is contained in the max vertical range
     */
    private boolean withinVerticalRange(double yCoord) {
        return (yCoord > 0 && yCoord < LOWER_BORDER);
    }
    
    public void keyPressed(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        
        // Check if the player is attempting to power up
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_SPACE) {
            if (numPowerUps > 0 && !isPoweredUp()) {
                numPowerUps--;
                state = State.POWERED_UP;
            }
        }
        
        // Movement handling
        // Horizontal
        if (keyCode == KeyEvent.VK_RIGHT) {
            isFacingRight = true;
            rightKeyPressed = true;
            dx = 2;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            isFacingRight = false;
            leftKeyPressed = true;
            dx = -2;
        }
        
        // Vertical
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = true;
            dy = -2;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downKeyPressed = true;
            dy = 2;
        }
    }
    
    public void keyReleased(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        
        // Horizontal movement
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false;
            if (!leftKeyPressed) {
                dx = 0;
            } else {
                isFacingRight = false;
                dx = -2;
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = false;
            if (!rightKeyPressed) {
                dx = 0;
            } else {
                isFacingRight = true;
                dx = 2;
            }
        }
        
        // Vertical movement
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = false;
            dy = (!downKeyPressed) ? 0 : 2;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            downKeyPressed = false;
            dy = (!upKeyPressed) ? 0 : -2;
        }
    }
}
