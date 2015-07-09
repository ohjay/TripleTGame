package Dodge;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * A Tedhaun, serving as a generic ghost in the Dodge! minigame.
 * Includes positional state values, alongside image, collision detection, and movement behavior.
 * @author Owen Jow
 */
public class MinigameGhost {
    private static int idCounter = Integer.MIN_VALUE;
    private int id;
    protected double xPos, yPos;
    private int dx, dy;
    protected boolean isFacingRight;
    private static final double DIAGONAL_DIVISOR = 1 / Math.sqrt(2);
    // Note: off = offset
    private static final int SIDE_LEN = 27, R_HORIZ_OFF = 11, L_HORIZ_OFF = 8, VERT_OFF = 10;
    static final int RIGHT_BORDER = DodgePanel.HORIZ_RIGHT_BORDER - SIDE_LEN - 3, 
            LOWER_BORDER = DodgePanel.VERT_LOWER_BORDER - SIDE_LEN - 6;
    
    /**
     * No-argument constructor that randomly positions the ghost.
     */
    public MinigameGhost() {
        this(Math.random() * RIGHT_BORDER, Math.random() * LOWER_BORDER);
    }
    
    /**
     * Constructs a ghost based on some starting position.
     * Randomly determines whether the ghost is facing left or right, 
     * and in which direction it will initially move.
     * @param xPos initial x-coordinate
     * @param yPos initial y-coordinate
     */
    public MinigameGhost(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        
        // Assign this ghost a unique ID value and update the shared ID counter
        id = idCounter;
        idCounter++;
        
        // Handle the orientation of the ghost (should be randomly determined)
        if (Math.random() < 0.5) {
            isFacingRight = true;
            dx = 1;
        } else {
            dx = -1;
        }
        
        // Handle the velocity (represented as dx and dy variables) of the ghost 
        // The eight possible directions are those of a basic compass rose
        if (Math.random() < 0.25) {
            dx = 0; // first, give the horizontal delta a chance to be zero
        }
        
        // Next, randomly determine the vertical positional delta value
        // (Slighly higher weight on -1 because diagonally-moving ghosts are cooler)
        switch ((int) (Math.random() * 4)) {
            case 0: 
                dy = 1;
                break;
            case 1:
                dy = (dx != 0) ? 0 : 1;
                break;
            default:
                dy = -1;
                break;
        }
    }
    
    /**
     * A getter method for the x-coordinate of this ghost.
     * @return the x-coordinate of this ghost
     */
    public int getX() {
        return (int) xPos;
    }
    
    /**
     * Like its brother getX, this is a getter method for the y-coordinate of this ghost.
     * @return the y-coordinate of this ghost
     */
    public int getY() {
        return (int) yPos;
    }
    
    /**
     * Returns the ghost's current image, based on whether it is facing left or right.
     * @return an Image representation of this ghost
     */
    public Image getImage() {
        return (isFacingRight) ? Images.get("rightTedhaun") : Images.get("leftTedhaun");
    }
    
    /**
     * If the ghost is about to collide with a horizontal border, returns true.
     * (Otherwise, returns false.)
     * @return a boolean representing whether or not the ghost is colliding with a horizontal wall
     */
    private boolean collisionWithHorizontalWall() {
        return (xPos + dx > RIGHT_BORDER || xPos + dx < 0);
    }
    
    /**
     * If the ghost is about to collide with a vertical border, returns true.
     * (Otherwise, returns false).
     * @return a boolean representing whether or not the ghost is colliding with a vertical wall
     */
    private boolean collisionWithVerticalWall() {
        return (yPos + dy > LOWER_BORDER || yPos + dy < 0);
    }
    
    /**
     * Returns the rectangular representation of this ghost, to be used in collision detection.
     * @return the rectangular representation of this ghost, to be used in collision detection
     */
    public Rectangle getRectangle() {
        if (isFacingRight) {
            return new Rectangle(getX() + R_HORIZ_OFF, getY() + VERT_OFF, SIDE_LEN, SIDE_LEN);
        } else {
            return new Rectangle(getX() + L_HORIZ_OFF, getY() + VERT_OFF, SIDE_LEN, SIDE_LEN);
        }
    }
    
    /**
     * "Moves" the ghost by changing its position.
     */
    public void move() {
        // Check for collisions and reverse deltas accordingly
        if (collisionWithHorizontalWall()) {
            isFacingRight = !isFacingRight;
            dx *= -1;
        }
        if (collisionWithVerticalWall()) {
            dy *= -1;
        }
        
        // Handle actual movement by updating positional values
        if (dx == 0 || dy == 0) {
            xPos += dx;
            yPos += dy;
        } else {
            // Turn diagonal delta movement into 1 instead of sqrt(2)
            xPos += DIAGONAL_DIVISOR * dx;
            yPos += DIAGONAL_DIVISOR * dy;
        }
    }
    
    public boolean equals(Object obj) {
        return (obj instanceof MinigameGhost && this.id == ((MinigameGhost) obj).id);
    }
    
    public int hashCode() {
        return id;
    }
}
