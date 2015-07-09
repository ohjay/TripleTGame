package Dodge; 

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;

/** 
 * The panel to display before playing the Dodge! minigame.
 * Includes an objective statement, controls info, and movement demonstrations.
 * @author Owen Jow
 */
public class DodgePreGPanel extends JPanel implements ActionListener {
    private KeyListener kl;
    private Timer timer;
    private static final Image PRE_G_IMG = Images.get("dodgePreG");
    private DemoStarKirby moveKirby, powerupKirby;
    private DemoGhost moveGhost, powerupGhost;
    private static final int K_XMIN = 293, MOVE_K_Y = 142, POWERUP_K_Y = 220;
    private static final int K_XMAX = K_XMIN + 151, G_X = (int) ((K_XMIN + K_XMAX) / 2), 
            MOVE_G_Y = MOVE_K_Y - 8, POWERUP_G_Y = POWERUP_K_Y - 8; // K = Kirby, G = Ghost
    private boolean powerupGhostDisplayed = true;
    
    /**
     * Constructs a Dodge! pre-game panel.
     * Initializes the Kirbys and the ghosts that will be animated, and powers up
     * the powerup demo Kirby.
     */
    public DodgePreGPanel() {
        reset();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
        
        // Handle the move demo behavior
        moveKirby.move();
        if (moveKirby.getRectangle().intersects(moveGhost.getRectangle()) 
                && !moveKirby.isStartled()) {
            moveKirby.startle();
            moveKirby.repelFromGhost(moveGhost.getX());
        } else if (moveKirby.getX() == K_XMIN) {
            moveKirby.normalize();
        }
        
        // Handle the powerup demo behavior
        powerupKirby.move();
        if (powerupKirby.getRectangle().intersects(powerupGhost.getRectangle())) {
            powerupGhostDisplayed = false;
        } else if (powerupKirby.getX() == K_XMIN) {
            powerupGhostDisplayed = true;
        }
        
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(PRE_G_IMG, 0, 0, null);
        
        // Draw ghosts
        g2.drawImage(moveGhost.getImage(), moveGhost.getX(), moveGhost.getY(), null);
        if (powerupGhostDisplayed) {
            g2.drawImage(powerupGhost.getImage(), powerupGhost.getX(), powerupGhost.getY(), null);
        }
        
        // Draw Kirbys
        g2.drawImage(moveKirby.getImage(), moveKirby.getX(), moveKirby.getY(), null);
        g2.drawImage(powerupKirby.getImage(), powerupKirby.getX(), powerupKirby.getY(), null);
    }
    
    /** 
     * Activates the Dodge! pre-game panel.
     * After this method is executed, the panel will be able to respond to keyboard input.
     */
    public void activate() {
        // Reset all values so that animations start at their beginning
        reset();
        
        //  Handle key listener registration
        kl = new KeyListener();
        addKeyListener(kl);
        
        // This timer runs at identical pace to Dodge! so that the animations will be same-speed
        timer = new Timer(20, this);
        timer.start();
    }
    
    /**
     * Deactivates the Dodge! pre-game panel.
     */
    public void deactivate() {
        removeKeyListener(kl);
        timer.stop();
    }
    
    /**
     * Resets all values/objects so that animations start at their beginning.
     */
    private void reset() {
        moveKirby = new DemoStarKirby(K_XMIN, K_XMAX, MOVE_K_Y);
        powerupKirby = new DemoStarKirby(K_XMIN, K_XMAX, POWERUP_K_Y);
        moveGhost = new DemoGhost(G_X, MOVE_G_Y);
        powerupGhost = new DemoGhost(G_X, POWERUP_G_Y);
        powerupKirby.powerUp();
    }

    public class KeyListener extends KeyAdapter {
        /**
         * If any key is pressed, the game should transition to the actual Dodge! minigame.
         * @param evt a KeyEvent that we won't actually use (we don't care which key was pressed)
         */
        public void keyPressed(KeyEvent evt) {
            deactivate();
            GameState.layout.show(GameState.contentPanel, "dodge");
            GameState.dodgePanel.reset();
            GameState.dodgePanel.activate();
        }
    }
}
