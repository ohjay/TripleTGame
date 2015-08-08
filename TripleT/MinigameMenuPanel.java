package TripleT;

import java.awt.Image;
import javax.swing.KeyStroke;

/** 
 * A menu that serves as a gateway to different minigames.
 * There are two such minigames; one is a survival-style evasion game (Dodge!)
 * and the other is a "Smash Bros"-inspired knockout game, in which the winner 
 * is the last man standing among the player and three CPUs.
 * @author Owen Jow
 */
public class MinigameMenuPanel extends EscapableMenuPanel {
    
    /** 
     * Constructs a minigame menu panel. Initializes images.
     */
    public MinigameMenuPanel() {
        // Initialize images
        menuImages = new Image[] { Images.get("dodgeH"), Images.get("kirbySMASHH") };
    }
    
    @Override
    void setKeyBindings() {
        // Input maps
        getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0), SWITCH_UP);
        getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0), SWITCH_DOWN);
        getInputMap().put(KeyStroke.getKeyStroke("ENTER"), CONFIRM);
        getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.pauseKey, 0), CONFIRM);
        getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), GO_BACK);
        getInputMap().put(KeyStroke.getKeyStroke("DELETE"), GO_BACK);
        getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), GO_BACK);
        
        // Action maps
        getActionMap().put(SWITCH_UP, new SwitchAction(-1));
        getActionMap().put(SWITCH_DOWN, new SwitchAction(1));
        getActionMap().put(CONFIRM, new ConfirmAction());
        getActionMap().put(GO_BACK, new EscapeAction());
    }
    
    @Override
    protected void confirm() {
        if (imgIndex == 0) {
            GameState.layout.show(GameState.contentPanel, "dodgePreG");
            GameState.dodgePreGPanel.activate();
        } else {
            GameState.layout.show(GameState.contentPanel, "kirbySMASH");
        }
        
        repaint();
    }
}
