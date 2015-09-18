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
    int origTop, origLeft, topOffset, leftOffset;
    Rectangle[] objects; // foreground objects in their original positions
    
    /**
     * This constructor assumes that origLeft should be initialized to 0.
     */
    public Foreground(Image img, int origTop, Rectangle[] objects) {
        this.img = img;
        this.origTop = origTop;
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
    }
    
    /**
     * Returns true if the given rectangle (or more precisely, the given rectangle COORDINATES)
     * intersects/collides with any of the objects in the foreground.
     */
    boolean intersects(int width, int height, int x, int y) {
        for (Rectangle obj : objects) {
            if (new Rectangle(x, y, width, height).intersects(obj)) {
                return true;
            }
        }
        
        return false; // no collisions detected!
    }
}
