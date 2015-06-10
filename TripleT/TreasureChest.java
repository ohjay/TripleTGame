package TripleT;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * A treasure chest, to be used as an objective in the Dodge! minigame.
 * @author Owen Jow
 */
public class TreasureChest {
    private int xPos, yPos;
    private static final int SIDE_LEN = 34, MAX_X = 440, MAX_Y = 330;
    
    /** 
     * Constructs a treasure chest and "hides" it somewhere random.
     */
    public TreasureChest() {
		relocate();
    }
    
    /**
     * Returns the x-coordinate for this treasure chest.
     */
    public int getX() {
        return xPos;
    }
    
    /**
     * Returns the y-coordinate for this treasure chest.
     */
    public int getY() {
        return yPos;
    }
    
    /**
     * Relocates the treasure chest to somewhere else (it was probably discovered!).
     * The relocation should be random, within a certain range.
     */
    public void relocate() {
        xPos = (int) (Math.random() * MAX_X);
        yPos = (int) (Math.random() * MAX_Y);
    }
    
    /**
     * Returns a rectangle representing the space taken up by this treasure chest.
     * @return a rectangle to be used for collision detection
     */
    public Rectangle getRectangle() {
        return new Rectangle(xPos, yPos, SIDE_LEN, SIDE_LEN);
    }
    
    /**
     * Returns an image of a treasure chest. The same treasure chest. Always.
     * @return a pretty picture of a chest (of treasure)
     */
    public Image getImage() {
        return Images.get("treasure");
    }
}
