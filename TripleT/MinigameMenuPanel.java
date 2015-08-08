package TripleT;

import java.awt.Image;
import javax.swing.InputMap;
import javax.swing.ActionMap;
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
        // Input map bindings
        InputMap iMap = getInputMap();
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0), SWITCH_UP);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0), SWITCH_DOWN);
        iMap.put(KeyStroke.getKeyStroke("ENTER"), CONFIRM);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.pauseKey, 0), CONFIRM);
        iMap.put(KeyStroke.getKeyStroke("ESCAPE"), GO_BACK);
        iMap.put(KeyStroke.getKeyStroke("DELETE"), GO_BACK);
        iMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), GO_BACK);
        
        // Action map bindings
        ActionMap aMap = getActionMap();
        aMap.put(SWITCH_UP, new SwitchAction(-1));
        aMap.put(SWITCH_DOWN, new SwitchAction(1));
        aMap.put(CONFIRM, new ConfirmAction());
        aMap.put(GO_BACK, new EscapeAction());
    }
    
    @Override
    protected void confirm() {
        if (imgIndex == 0) {
            GameState.layout.show(GameState.contentPanel, "dodgePreG");
            GameState.dodgePreGPanel.activate();
        } else {
            GameState.layout.show(GameState.contentPanel, "kirbySMASH");
        }
    }
}
