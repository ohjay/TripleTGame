package TripleT;

import java.awt.Image;
import java.awt.event.KeyEvent;

/**  
 * A pause panel for the Dodge! minigame. 
 * @author Owen Jow 
 */
public class DodgePausePanel extends MenuPanel {
    
    /** 
     * Constructs a Dodge! pause panel, adding the proper backgrounds to this image set.
     */
    public DodgePausePanel() {
        // Initialize pause menu images
        menuImages = new Image[] { Images.get("dodgePause1"), Images.get("dodgePause2") };
    }
    
    /** 
     * Activates the Dodge! pause panel.
     */
    public void activate() {
        imgIndex = 0;
        activate(new KeyListener());
    }
    
    /**
     * The KeyListener for the dodge pause menu.
     * Controls registered: UP, DOWN, LEFT, RIGHT, SHIFT, and ENTER.
     */
    public class KeyListener extends MenuPanel.KeyListener {
        /**
         * Handles the menu's response to keys being pressed.
         * @param KeyEvent evt the extraordinary event that is a key being pressed
         */
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            
            if (keyCode == GameState.pInfo.upKey || keyCode == GameState.pInfo.downKey
                    || keyCode == GameState.pInfo.leftKey || keyCode == GameState.pInfo.rightKey) {
                imgIndex = 1 - imgIndex;
            } else if (keyCode == KeyEvent.VK_ENTER || keyCode == GameState.pInfo.pauseKey) {
                deactivate();
                if (imgIndex == 0) {
                    GameState.layout.show(GameState.contentPanel, "dodge");
                    GameState.dodgePanel.activate();
                } else {
                    GameState.layout.show(GameState.contentPanel, "mainMenu");
                    GameState.menuPanel.activate();
                }
            } else if (keyCode == GameState.pInfo.pauseKey) {
                deactivate();
                GameState.layout.show(GameState.contentPanel, "dodge");
                GameState.dodgePanel.activate();
            }
            
            repaint();
        }
    }
}
