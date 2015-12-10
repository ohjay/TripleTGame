package TripleT;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * A menu that can be escaped from (i.e. a menu with a "back" option).
 * @author Owen Jow
 */
public class EscapableMenuPanel extends MenuPanel {
    protected static final String GO_BACK = "go back";
    protected boolean usesArrows; // (whether or not the panel has directional controls)
    
    /**
     * Activates the panel by resetting the menu and requesting focus.
     * In general, escapable menus should be reset upon entry, which is 
     * why this method is located in the EscapableMenuPanel class.
     */
    public void activate() {
        imgIndex = 0;
        requestFocus();
    }
    
    @Override
    void setKeyBindings() {
        super.setKeyBindings();
        getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), GO_BACK);
        getActionMap().put(GO_BACK, new EscapeAction());
        
        if (!usesArrows) {
            // Remove mappings for UP / LEFT / DOWN / RIGHT
            getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.upKey, 0), "none");
            getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0), "none");
            getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.downKey, 0), "none");
            getInputMap().put(KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0), "none");
        }
    }
    
    /**
     * A method containing escape/back behavior for the menu.
     */
    protected void escape() {
        // Return to the main menu
        GameState.layout.show(GameState.contentPanel, "mainMenu");
        GameState.menuPanel.requestFocus();
    }
    
    /**
     * (Presumably) goes back a menu level.
     */
    protected class EscapeAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            escape();
        }
    }
}
