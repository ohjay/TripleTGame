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
    int origTop, origLeft, length;
    double topOffset, leftOffset;
    Rectangle[] objects; // foreground objects in their original positions
    
    /**
     * This constructor assumes that origLeft should be initialized to 0.
     * @param img the foreground image
     * @param origTop the offset by which to originally draw the foreground image
     * @param length the length of the foreground in pixels
     * @param objects a collection of rectangles that represent objects in the foreground
     */
    public Foreground(Image img, int origTop, int length, Rectangle[] objects) {
        this.img = img;
        this.origTop = origTop;
        this.length = length;
        this.objects = objects;
    }
    
    /**
     * Shifts (moves) the foreground horizontally, by DX pixels.
     */
    void horizontalShift(double dx) {
        leftOffset += dx;
    }
    
    /**
     * Shifts (moves) the foreground vertically, by DY pixels.
     */
    void verticalShift(double dy) {
        topOffset += dy;
    }
    
    /**
     * Returns true if the given rectangle (or more precisely, the given rectangle COORDINATES)
     * intersects/collides with any of the objects in the foreground.
     */
    boolean intersects(int width, int height, int x, int y) {
        for (Rectangle obj : objects) {
            if (new Rectangle(x - (int) leftOffset, y - (int) topOffset, width, height).intersects(obj)) {
                return true;
            }
        }
        
        return false; // no collisions detected!
    }
}
