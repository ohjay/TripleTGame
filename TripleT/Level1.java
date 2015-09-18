package TripleT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

/**
 * The first level of the game.
 * @author Owen Jow
 */
public class Level1 extends LevelPanel {
    private static final int KIRBY_INITIAL_Y = 330;
    
    /**
     * A no-argument constructor for Level1.
     * Constructs a Kirby to act as the controllable character for the level.
     */
    public Level1() {
        // Foreground data (incl. construction of foreground Rectangle objects)
        foreground = new Foreground(Images.get("demoForeground"), 260, new Rectangle[] {
            new Rectangle(0, 355, 1006, 82), /* the ground (to begin with) */
            new Rectangle(282, 339, 28, 20), /* first bump in the road */
            new Rectangle(537, 339, 96, 20), /* second bump in the road */
        });
        fgImage = foreground.img;
        
        kirby = new Kirby(0, KIRBY_INITIAL_Y);
        backgroundImg = Images.get("demoBackground");
    }
    
    @Override
    protected void reset() {
        super.reset();
        kirby = new Kirby(0, KIRBY_INITIAL_Y);
    }
    
    @Override
    protected void drawForeground(Graphics2D g2) {
        g2.drawImage(fgImage, 0, foreground.origTop + foreground.topOffset, null);
        kirby.drawImage(g2);
    }
}
