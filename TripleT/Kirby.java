package TripleT;

import java.awt.Image;

/**
 * This is story-mode Kirby in all his glory. As a controllable sprite, 
 * Kirby can be directed with the keyboard and will display movement
 * animations based on his spritesheet.
 * @author Owen Jow
 */
public class Kirby extends ControllableSprite {
    private static final Image SPRITESHEET = Images.get("kirbySS");
    
    /**
     * A constructor for a Kirby that will place him at the given coordinates on the screen.
     */
    public Kirby(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Image getImage() {
        /* To do: write getImage() method */
        return null;
    }
}
