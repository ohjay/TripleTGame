package TripleT;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        kl = new Level1KL();
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
    
    /* To do: convert to key bindings! */
    public class Level1KL extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            if (isPaused) {
                if (keyCode == GameState.pInfo.upKey || keyCode == GameState.pInfo.downKey) {
                    pauseIndex = 1 - pauseIndex;
                } else if (keyCode == GameState.pInfo.pauseKey || keyCode == KeyEvent.VK_ENTER) {
                    if (pauseIndex == 0) {
                        isPaused = false;
                    } else {
                        deactivate();
                        GameState.layout.show(GameState.contentPanel, "mainMenu");
                        GameState.menuPanel.requestFocus();
                    }
                }
            } else if (keyCode == GameState.pInfo.pauseKey) {
                isPaused = true;
            } else {
                kirby.keyPressed(evt);
            }
            
            repaint();
        }
        
        public void keyReleased(KeyEvent evt) {
            if (!isPaused) {
                kirby.keyReleased(evt);
            }
            
            repaint();
        }
    }
}