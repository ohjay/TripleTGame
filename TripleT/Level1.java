package TripleT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

public class Level1 extends LevelPanel {
    private static final int START_Y = 325;
    private static final Image FOREGROUND_IMG = Images.get("demoForeground");
    Kirby kirby;
    
    /**
     * A no-argument constructor for Level1.
     * Constructs a Kirby to act as the controllable character for the level.
     */
    public Level1() {
        kirby = new Kirby(0, START_Y);
        backgroundImg = Images.get("level1Background");
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        super.actionPerformed(evt);
        if (kirby.updateFrame()) {
            // Kirby's current frame has changed, so we'll need to repaint
            repaint();
        }
    }
    
    @Override
    protected void drawForeground(Graphics2D g2) {
        g2.drawImage(FOREGROUND_IMG, 0, 260, null);
        kirby.drawImage(g2);
    }
}