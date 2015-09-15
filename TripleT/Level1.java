package TripleT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

/**
 * The first level of the game.
 * @author Owen Jow
 */
public class Level1 extends LevelPanel {
    private static final int START_Y = 330, FOREGROUND_Y = 260;
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
    }
    
    @Override
    protected void reset() {
        super.reset();
        counter = 0;
        kirby = new Kirby(0, START_Y);
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
            kirby.moveWithinBoundaries(kirby.spriteWidth, 0, TripleTWindow.SCR_WIDTH, 
                    0, TripleTWindow.SCR_HEIGHT);
        }
    }
    
    @Override
    protected void drawForeground(Graphics2D g2) {
        g2.drawImage(FOREGROUND_IMG, 0, FOREGROUND_Y, null);
        kirby.drawImage(g2);
    }
    
    //================================================================================
    // Overridden action methods
    //================================================================================
    
    @Override
    public void rightPressed() {
        if (!isPaused) {
            kirby.rightPressed();
            repaint();
        }
    }
    
    @Override
    public void leftPressed() {
        if (!isPaused) {
            kirby.leftPressed();
            repaint();
        }
    }
    
    @Override
    public void downPressed() {
        if (isPaused) {
            pauseIndex = 1 - pauseIndex;
        } else {
            kirby.downPressed();
        }
        
        repaint();
    }
    
    @Override
    public void upPressed() {
        if (isPaused) { 
            pauseIndex = 1 - pauseIndex; 
        } else {
            kirby.upPressed();
        }
        
        repaint();
    }
    
    @Override
    public void rightReleased() {
        if (!isPaused) {
            kirby.rightReleased();
            repaint();
        }
    }
    
    @Override
    public void leftReleased() {
        if (!isPaused) {
            kirby.leftReleased();
            repaint();
        }
    }
    
    @Override
    public void downReleased() {
        if (!isPaused) {
            kirby.downReleased();
            repaint();
        }
    }
    
    @Override
    public void upReleased() {
        if (!isPaused) {
            kirby.upReleased();
            repaint();
        }
    }
}