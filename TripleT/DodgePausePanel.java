package TripleT;

import java.awt.Image;

/**  
 * A pause panel for the Dodge! minigame. 
 * @author Owen Jow 
 */
public class DodgePausePanel extends EscapableMenuPanel {
    
    /** 
     * Constructs a Dodge! pause panel, adding the proper backgrounds to this image set.
     */
    public DodgePausePanel() {
        // Initialize pause menu images
        menuImages = new Image[] { Images.get("dodgePause1"), Images.get("dodgePause2") };
    }
    
    @Override
    protected void switchOptions(int direction) {
        imgIndex = 1 - imgIndex;
        repaint();
    }
    
    @Override
    protected void confirm() {
        if (imgIndex == 0) {
            GameState.layout.show(GameState.contentPanel, "dodge");
            GameState.dodgePanel.activate();
        } else {
            GameState.layout.show(GameState.contentPanel, "mainMenu");
            GameState.menuPanel.requestFocus();
        }
    }
}
