package TripleT;

import java.awt.Image;
import java.awt.Graphics2D;

/**
 * An abstract representation of a sprite that will be extended by all sprites
 * (Google's definition of a sprite: a computer graphic that may be moved on-screen 
 * and otherwise manipulated as a single entity).
 * 
 * For example, Kirby would be a sprite, a Waddle Dee would be a sprite, and an item
 * sitting on the ground would be a sprite.
 * @author Owen Jow
 */
abstract class Sprite {
    protected int x, y, dx, dy; // positional and delta values
    int spriteWidth, spriteHeight;
    
    /**
     * Gets the current x-coordinate of the sprite.
     * @return the sprite's x-coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the current y-coordinate of the sprite.
     * @return the sprite's y-coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Returns the sprite's horizontal delta value.
     * @return the sprite's dx value
     */
    public int getDX() {
        return dx;
    }
    
    /**
     * Returns the sprite's vertical delta value.
     * @return the sprite's dy value
     */
    public int getDY() {
        return dy;
    }
    
    /**
     * Setter method for the sprite's position on the x-axis.
     * @param x the position that the sprite should be set to
     */
    void setX(int x) {
        this.x = x;
    }
    
    /**
     * Setter method for the sprite's position on the y-axis.
     * @param y the y-coordinate for the sprite to take on
     */
    void setY(int y) {
        this.y = y;
    }
    
    /**
     * Setter method for the sprite's horizontal delta value.
     * To be used to control movement (ex. for animation).
     * @param dx the new delta-x value
     */
    void setDX(int dx) {
        this.dx = dx;
    }
    
    /**
     * Setter method for the sprite's vertical delta value.
     * @param dy the new delta-y value
     */
    void setDY(int dy) {
        this.dy = dy;
    }
    
    /**
     * Freezes the sprite momentarily (i.e. stops all positional movement).
     */
    void halt() {
        this.dx = 0;
        this.dy = 0;
    }
    
    /**
     * Move the sprite; many sprites will likely move in different patterns, 
     * so this method will often need to be overridden.
     */
    public void move() {
        x += dx;
        y += dy;
    }
    
    /**
     * Moves the sprite, but only within the boundaries given by X_MIN, X_MAX,
     * Y_MIN, and Y_MAX. These boundaries are inclusive!
     *
     * Also refuses to move the sprite in a certain direction if making such a move 
     * would result in a collision with some object in the foreground.
     */
    public void moveWithinBoundaries(int xMin, int xMax, int yMin, int yMax, Foreground foreground) {
        // Vertical movement
        if (y + dy >= yMin && y + dy + spriteHeight <= yMax 
                && !foreground.intersects(Math.min(20, spriteWidth), spriteHeight, x + 1, y + dy)) { y += dy; }
        // Horizontal movement
        if (x + dx >= xMin && x + dx + spriteWidth <= xMax 
                && !foreground.intersects(Math.min(22, spriteWidth), spriteHeight, x + dx, y)) { x += dx; }
    }
    
    /**
     * Does the same thing as moveWithinBoundaries, except it only moves the sprite vertically.
     */
    public void moveVertically(int yMin, int yMax, Foreground foreground) {
        if (y + dy >= yMin && y + dy + spriteHeight <= yMax
                && !foreground.intersects(Math.min(20, spriteWidth), spriteHeight, x + 1, y + dy)) { y += dy; }
    }
    
    /**
     * Draws the current image representation of the sprite, whatever that may be.
     */
    abstract void drawImage(Graphics2D g2);
}
