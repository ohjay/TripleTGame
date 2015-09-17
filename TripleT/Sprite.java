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
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the current y-coordinate of the sprite.
     */
    public int getY() {
        return y;
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
     * Also refuses to move the sprite in a certain direction if making a move 
     * in that direction would result in a collision with some object in the foreground.
     */
    public void moveWithinBoundaries(int xMin, int xMax, int yMin, int yMax, Foreground foreground) {
        int collisionStatus = foreground.intersects(spriteWidth, spriteHeight, x + dx, y + dy);
        
        if (x + dx >= xMin && x + dx + spriteWidth <= xMax && (collisionStatus & 0b10) == 0) { x += dx; }
        if (y + dy >= yMin && y + dy + spriteHeight <= yMax && (collisionStatus & 0b01) == 0) { y += dy; } 
    }
    
    /**
     * Draws the current image representation of the sprite, whatever that may be.
     */
    abstract void drawImage(Graphics2D g2);
}
