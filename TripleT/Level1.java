package TripleT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Level1 extends LevelPanel {
    private static final int START_Y = 330;
    private static final Image FOREGROUND_IMG = Images.get("demoForeground");
    private int counter;
    Kirby kirby;
    
    /**
     * A no-argument constructor for Level1.
     * Constructs a Kirby to act as the controllable character for the level.
     */
    public Level1() {
        kirby = new Kirby(0, START_Y);
        backgroundImg = Images.get("level1Background");
        kl = new Level1KL();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        counter++; // update this counter every time the timer fires an ActionEvent
        super.actionPerformed(evt);
        if (kirby.updateFrame()) {
            // Kirby's current frame has changed, so we'll need to repaint
            repaint();
        }
        
        if (counter % 2 == 0) {
            // Because we don't want to move TOO fast!
            kirby.moveWithinBoundaries(kirby.spriteWidth, 0, TripleTWindow.SCREEN_WIDTH, 
                    0, TripleTWindow.SCREEN_HEIGHT);
        }
    }
    
    @Override
    protected void drawForeground(Graphics2D g2) {
        g2.drawImage(FOREGROUND_IMG, 0, 260, null);
        kirby.drawImage(g2);
    }
    
    /* To do: convert to key bindings! */
    public class Level1KL extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            kirby.keyPressed(evt);
            repaint();
        }
        
        public void keyReleased(KeyEvent evt) {
            kirby.keyReleased(evt);
            repaint();
        }
    }
}