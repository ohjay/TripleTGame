package TripleT;

import java.util.LinkedHashSet;
import java.util.HashSet;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A panel containing all of the topmost logic for Dodge!.
 * @author Owen Jow
 */
public class DodgePanel extends JPanel implements ActionListener {
    private KeyListener kl;
    private Timer timer;
    private static final Image BACKGROUND_IMG = Images.get("dodgeBackground");
    private static final Image LIFE_CT_IMG = Images.get("lifeCount");
    static final int HORIZ_RIGHT_BORDER = 496, VERT_LOWER_BORDER = 381;
    private static final int LIFE_CT_X = 382, LIFE_CT_Y = 5, NUM_X = 425, NUM_Y = 6,
            INFO_X = 386, SCORE_Y = 46, BONUS_Y = 61, POWERUP_Y = 76, FLASHING_Y = 91,
            INITIAL_BONUS = 500;
    private int powerUptime, startleTime, treasureBonus = INITIAL_BONUS, chestsCollected, score,
            randX, randY, whiteCount;
    LinkedHashSet<MinigameGhost> ghostSet = new LinkedHashSet<MinigameGhost>();
    HashSet<MinigameGhost> ghostsToRemove = new HashSet<MinigameGhost>();
    private StarKirby kirby;
    private TreasureChest treasure;
    
    /**
     * Constructor for a Dodge! panel.
     * @param contentPanel the base JPanel, to be used for switching between screens
     */
    public DodgePanel() {
        kirby = new StarKirby();
        treasure = new TreasureChest();
        
        for (int i = 0; i < 2; i++) {
            ghostSet.add(new MinigameGhost()); // add two ghosts just for starters
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
        
        if (kirby.numLives < 0) {
            deactivate();
            GameState.dodgePostGPanel.resetWithScore(score);
            GameState.layout.show(GameState.contentPanel, "dodgePostG");
            GameState.dodgePostGPanel.activate();
        }
        
        if (kirby.isPoweredUp()) {
            powerUptime++;
            if (powerUptime > 150) {
                kirby.normalize();
                powerUptime = 0;
            } else if (powerUptime > 100) {
                kirby.coolDown();
            }
        } else if (kirby.isStartled()) {
            startleTime++;
            if (startleTime > 75) {
                kirby.normalize();
                startleTime = 0;
            }
        }
        
        // Bust ghosts [gtfo of Kirby's rectangle!] or get busted [not invincible oops...]
        // Also, move each ghost
        for (MinigameGhost ghost : ghostSet) {
            if (ghost.getRectangle().intersects(kirby.getRectangle())) {
                if (kirby.isPoweredUp()) {
                    ghostsToRemove.add(ghost);
                } else if (!kirby.isStartled()) {
                    kirby.numLives--;
                    kirby.startle();
                    kirby.repelFromGhost(ghost.getX());
                }
            }
            
            ghost.move();
        }
        
        // Are there actually any ghosts to bust?
        if (ghostsToRemove.size() > 0) {
            for (MinigameGhost doomedGhost : ghostsToRemove) {
                ghostSet.remove(doomedGhost);
            }
            
            ghostsToRemove.clear();
        }
        
        kirby.move(); // move Kirby
        
        // Check if Kirby has found the treasure!
        if (kirby.getRectangle().intersects(treasure.getRectangle())) {
            treasure.relocate();
            chestsCollected++;
            score += treasureBonus;
            treasureBonus = INITIAL_BONUS + (int) Math.pow(chestsCollected, 1.68);
            
            for (int i = 0; i < 1 + chestsCollected / 50; i++) {
                // Find a spot for a new ghost
                do {
                    randX = (int) (Math.random() * MinigameGhost.RIGHT_BORDER);
                } while (Math.abs(kirby.getX() - randX) < 31);
            
                do {
                    randY = (int) (Math.random() * MinigameGhost.LOWER_BORDER);
                } while (Math.abs(kirby.getY() - randY) < 31);
                
                ghostSet.add(new MinigameGhost(randX, randY));
            }
            
            // Give Kirby some power boosts
            if (chestsCollected % 30 == 0) {
                kirby.numLives++;
            } else if (chestsCollected % 15 == 0) {
                kirby.numPowerUps++;
            }
        } else if (treasureBonus > 0) {
            treasureBonus--;
        }
        
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BACKGROUND_IMG, 0, 0, null);
        g2.drawImage(treasure.getImage(), treasure.getX(), treasure.getY(), null);
        
        for (MinigameGhost ghost: ghostSet) {
            g2.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), null);
        }
        
        // Draw Kirby himself
        g2.drawImage(kirby.getImage(), kirby.getX(), kirby.getY(), null);
        
        // Draw the life counter graphic
        g2.drawImage(LIFE_CT_IMG, LIFE_CT_X, LIFE_CT_Y, null);
        if (kirby.numLives >= 0) {
            g2.drawImage(Images.numbers[kirby.numLives], NUM_X, NUM_Y, null); // assumes 0-9 lives
        }
        
        // Display score / powerup count / other relevant information
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        g2.drawString("Score: " + score, INFO_X, SCORE_Y);
        g2.drawString("Bonus: " + treasureBonus, INFO_X, BONUS_Y);
        g2.drawString("PowerUps: " + kirby.numPowerUps, INFO_X, POWERUP_Y);
        g.setColor(Color.WHITE);
        g2.drawString("Score: " + score, INFO_X - 1, SCORE_Y - 1);
        g2.drawString("Bonus: " + treasureBonus, INFO_X - 1, BONUS_Y - 1);
        g2.drawString("PowerUps: " + kirby.numPowerUps, INFO_X - 1, POWERUP_Y - 1);
        
        // Flash if Kirby is powered up
        if (kirby.isPoweredUp()) {
            g.setColor(Color.BLACK);
            g2.drawString("POWERED UP!", INFO_X, FLASHING_Y);
            g.setColor(Color.RED);
            g2.drawString("POWERED UP!", INFO_X - 1, FLASHING_Y - 1);
            whiteCount++;
            
            if (g.getColor() == Color.RED && whiteCount % 4 == 0) {
                g.setColor(Color.WHITE);
                g2.drawString("POWERED UP!", INFO_X - 1, FLASHING_Y - 1);
            }
        }
    }
    
    /** 
     * Activates the Dodge! panel.
     * This method enables a key listener so that the game can respond to keyboard inputs.
     */
    public void activate() {
        kl = new KeyListener();
        addKeyListener(kl);
        
        // Start the timer that will continually request focus for this panel
        timer = new Timer(20, this);
        timer.start();
    }
    
    /**
     * Deactivates the Dodge! panel. Key listeners and timers will no longer function.
     */
    public void deactivate() {
        removeKeyListener(kl);
        timer.stop();
    }
    
    /**
     * Resets all relevant values for the Dodge! panel, so the game can be played more than once.
     */
    public void reset() {
        kirby = new StarKirby();
        ghostSet.clear();
        
        for (int i = 0; i < 2; i++) {
            ghostSet.add(new MinigameGhost()); // add two ghosts again
        }
        
        treasureBonus = INITIAL_BONUS;
        chestsCollected = 0;
        score = 0;
    }

    public class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            kirby.keyPressed(evt);
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                kirby.nullifyKeyPresses();
                deactivate();
                GameState.layout.show(GameState.contentPanel, "dodgePause");
                GameState.dodgePausePanel.activate();
            }
        }
        
        public void keyReleased(KeyEvent evt) {
            kirby.keyReleased(evt);
        }
    }
}
