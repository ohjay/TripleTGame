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
     * @param state the current game state
     */
    public DodgePausePanel(GameState state) {
        super(state);
        
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
            
            if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                    || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                imgIndex = 1 - imgIndex;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                deactivate();
                if (imgIndex == 0) {
                    state.layout.show(state.contentPanel, "dodge");
                    state.dodgePanel.activate();
                } else {
                    state.layout.show(state.contentPanel, "mainMenu");
                    state.menuPanel.activate();
                }
            } else if (keyCode == KeyEvent.VK_SHIFT) {
                deactivate();
                state.layout.show(state.contentPanel, "dodge");
                state.dodgePanel.activate();
            }
            
            repaint();
        }
    }
}
