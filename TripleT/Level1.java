package TripleT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

/**
 * The first level of the game.
 * @author Owen Jow
 */
public class Level1 extends LevelPanel {
    private static final int FOREGROUND_Y = 260;
    private static final Image FOREGROUND_IMG = Images.get("demoForeground");
    
    /**
     * A no-argument constructor for Level1.
     * Constructs a Kirby to act as the controllable character for the level.
     */
    public Level1() {
        groundLevel = 330;
        kirby = new Kirby(0, groundLevel);
        backgroundImg = Images.get("level1Background");
    }
    
    @Override
    protected void reset() {
        super.reset();
        kirby = new Kirby(0, groundLevel);
    }
    
    @Override
    protected void drawForeground(Graphics2D g2) {
        g2.drawImage(FOREGROUND_IMG, 0, FOREGROUND_Y, null);
        kirby.drawImage(g2);
    }
}