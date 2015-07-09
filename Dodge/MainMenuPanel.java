package Dodge;

import java.awt.Image;
import java.awt.event.KeyEvent;

/** 
 * The main menu panel for the game.
 * @author Owen Jow
 */
public class MainMenuPanel extends MenuPanel {
    
    /** 
     * Constructs a minigame menu panel. Initializes images.
     */
    public MainMenuPanel() {
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
                }
            }
            
            repaint();
        }
    }
}
