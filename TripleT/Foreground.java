package TripleT;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

/**
 * The foreground; that is, a collection of all the objects 
 * that exist one layer above the background.
 * @author Owen Jow
 */
public class Foreground {
    Image img;
    int origTop, origLeft, topOffset, leftOffset, groundLevel;
    Rectangle[] objects; // foreground objects in their original positions
    
    /**
     * This constructor assumes that origLeft should be initialized to 0.
     */
    public Foreground(Image img, int origTop, int groundLevel, Rectangle[] objects) {
        this.img = img;
        this.origTop = origTop;
        this.groundLevel = groundLevel;
        this.objects = objects;
    }
    
    /**
     * Shifts (moves) the foreground horizontally, by DX pixels.
     */
    void horizontalShift(int dx) {
        leftOffset += dx;
    }
    
    /**
     * Shifts (moves) the foreground vertically, by DY pixels.
     */
    void verticalShift(int dy) {
        topOffset += dy;
        groundLevel += dy;
    }
    
    /**
     * Returns an integer representation of the manner by which the given rectangle
     * (technically rectangle coordinates) intersects any of the objects in the foreground:
     * - If the rectangle collides b/c of both horizontal and vertical factors, returns 3 (0b11).
     * - If the rectangle collides because of its horizontal position, returns 2 (0b10).
     * - If the rectangle collides because of its vertical position, returns 1 (0b01).
     * - If the rectangle does not collide with any objects, returns 0 (0b00).
     */
    int intersects(int spriteWidth, int spriteHeight, int x, int y) {
        for (Rectangle obj : objects) {
            if (new Rectangle(x, y, spriteWidth, spriteHeight).intersects(obj)) {
                // Check if the collision is because of the x-position or the y-position (or both)
                if (new Line2D.Double(x, y, x + spriteWidth, y).intersects(obj)
                        || new Line2D.Double(x, y + spriteHeight, x + spriteWidth, y).intersects(obj)) {
                    if (new Line2D.Double(x, y, x, y + spriteHeight).intersects(obj)
                            || new Line2D.Double(x + spriteWidth, y, x, y + spriteHeight).intersects(obj)) {
                        return 3;
                    } else {
                        return 2;
                    }
                } else { return 1; }
            }
        }
        
        return 0; // no collisions detected!
    }
}
