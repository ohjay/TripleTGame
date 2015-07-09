package Dodge;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

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
        menuImages = new Image[] { Images.get("mmControls"), Images.get("mmCredits"),
                 Images.get("mmMusic"), Images.get("mmPlay") };
        this.kl = new KeyListener();
    }
    
    /**
     * The KeyListener for the minigame menu.
     * Controls registered: LEFT, RIGHT, ENTER, and BACKSPACE.
     */
    public class KeyListener extends KeyAdapter {
        /**
         * Handles the menu's response to keys being pressed.
         * @param KeyEvent evt the extraordinary event that is a key being pressed
         */
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                imgIndex = (imgIndex == 3) ? 0 : 3;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                if (imgIndex < 2) { 
                    imgIndex++; // highlight the option below the current option
                } else if (imgIndex == 2) {
                    imgIndex = 0; // wrap around to the top
                }
            } else if (keyCode == KeyEvent.VK_UP) {
                if (imgIndex > 0 && imgIndex < 3) { 
                    imgIndex--; // highlight the option above the current option
                } else if (imgIndex == 0) {
                    imgIndex = 2; // wrap around to the bottom
                }
            } else if (keyCode == KeyEvent.VK_ENTER) {
                deactivate();
                switch (imgIndex) {
                    case 3: 
                        GameState.layout.show(GameState.contentPanel, "dodgePreG");
                        GameState.dodgePreGPanel.activate();
                        break;
                    case 2:
                    case 1:
                        GameState.layout.show(GameState.contentPanel, "credits");
                        GameState.creditsPanel.activate();
                        break;
                    default:
                }
            }
            
            repaint();
        }
    }
}
