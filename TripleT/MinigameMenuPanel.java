package TripleT;

import java.awt.Image;
import java.awt.event.KeyEvent;

/** 
 * A menu that serves as a gateway to different minigames.
 * There are two such minigames; one is a survival-style evasion game (Dodge!)
 * and the other is a "Smash Bros"-inspired knockout game, in which the winner 
 * is the last man standing among the player and three CPUs.
 * @author Owen Jow
 */
public class MinigameMenuPanel extends MenuPanel {
    
    /** 
     * Constructs a minigame menu panel. Initializes images.
     */
    public MinigameMenuPanel() {
        // Initialize images
        menuImages = new Image[] { Images.get("dodgeH"), Images.get("kirbySMASHH") };
    }
    
    /**
     * Activates the minigame menu panel. 
     * After this method is executed, the menu should be able to respond to keyboard inputs.
     */
    public void activate() {
        imgIndex = 0;
        activate(new KeyListener());
    }
    
    /**
     * The KeyListener for the minigame menu.
     * Controls registered: LEFT, RIGHT, ENTER, and BACKSPACE.
     */
    public class KeyListener extends MenuPanel.KeyListener {
        /**
         * Handles the menu's response to keys being pressed.
         * @param KeyEvent evt the extraordinary event that is a key being pressed
         */
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                imgIndex = 1 - imgIndex;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                deactivate();
                if (imgIndex == 0) {
                    GameState.layout.show(GameState.contentPanel, "dodgePreG");
                    GameState.dodgePreGPanel.activate();
                } else {
                    GameState.layout.show(GameState.contentPanel, "kirbySMASH");
                }
            } else if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
                deactivate();
                GameState.layout.show(GameState.contentPanel, "mainMenu");
                GameState.menuPanel.activate();
            }
            
            repaint();
        }
    }
}
