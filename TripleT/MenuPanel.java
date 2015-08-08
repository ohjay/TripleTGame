package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.InputMap;
import javax.swing.ActionMap;

/** 
 * An abstract representation of a menu panel.
 * Includes methods for key bindings, along with the ability 
 * to switch between options (which will tend to be images).
 * @author Owen Jow
 */
public abstract class MenuPanel extends JPanel {
    protected Image[] menuImages;
    protected int imgIndex = 0;
    
    // Action names (for key bindings)
    protected static final String SWITCH_UP = "switch up", SWITCH_DOWN = "switch down", 
            CONFIRM = "confirm";
    
    /**
     * Paints the menu's background image.
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuImages[imgIndex], 0, 0, null);
    }
    
    /** 
     * Increments the images index.
     * After this operation, the next image displayed will be the one 
     * following the current image in the internal images array. 
     */
    protected void incrementIndex() {
        if (imgIndex < menuImages.length - 1) {
            imgIndex++;
        } else {
            imgIndex = 0;
        }
    }
    
    /** 
     * Decrements the images index.
     * After this operation, the next image displayed will be the one
     * preceding the current image in the internal images array.
     */
    protected void decrementIndex() {
        if (imgIndex > 0) {
            imgIndex--;
        } else {
            imgIndex = menuImages.length - 1;
        }
    }
    
    /**
     * An initialization of default key bindings. This method should always be overridden 
     * if different key bindings are required.
     */
    void setKeyBindings() {
        // Input map bindings
        InputMap iMap = getInputMap();
        iMap.put(KeyStroke.getKeyStroke(GameState.pInfo.upKey, 0), SWITCH_UP);
        iMap.put(KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0), SWITCH_UP);
        iMap.put(KeyStroke.getKeyStroke(GameState.pInfo.downKey, 0), SWITCH_DOWN);
        iMap.put(KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0), SWITCH_DOWN);
        iMap.put(KeyStroke.getKeyStroke("ENTER"), CONFIRM);
        iMap.put(KeyStroke.getKeyStroke(GameState.pInfo.pauseKey, 0), CONFIRM);
    
        // Action map bindings
        ActionMap aMap = getActionMap();
        aMap.put(SWITCH_UP, new SwitchAction(-1));
        aMap.put(SWITCH_DOWN, new SwitchAction(1));
        aMap.put(CONFIRM, new ConfirmAction());
    }
    
    /**
     * Here, we impeach OLD_KEY and give its function to a fresh young NEW_KEY.
     * @param shouldRemove a boolean specifying whether to remove the old key's binding
     */
    void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, boolean shouldRemove) {
        InputMap iMap = getInputMap();
        iMap.put(newKey, iMap.get(oldKey));
        if (shouldRemove) { iMap.remove(oldKey); }
    }
    
    /**
     * We will again impeach OLD_KEY and give OLD_ACTION to NEW_KEY.
     * @param shouldRemove a boolean specifying whether to remove the old key's binding
     */
    void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, String oldAction, 
            boolean shouldRemove) {
        getInputMap().put(newKey, oldAction);
        if (shouldRemove) { getInputMap().remove(oldKey); }
    }
    
    /**
     * Switches between menu options. For custom switch behavior, this is the method
     * to override.
     */
    protected void switchOptions(int direction) {
        if (direction == -1) { decrementIndex(); }
        else { incrementIndex(); }
        repaint();
    }
    
    /**
     * In general, this method will involve selecting an option and entering some other panel.
     * Right now, though, it's a tabula rasa!
     */
    protected void confirm() {
        /* Every menu will have different confirm behavior. If a subclass has any 
           confirm behavior at all, override this! */
    }
    
    protected class SwitchAction extends AbstractAction {
        int direction;
        
        SwitchAction(int direction) {
            // For directions: -1 indicates left or up, and 1 indicates right or down
            this.direction = direction;
        }
        
        @Override
        public void actionPerformed(ActionEvent evt) {
            switchOptions(direction);
        }
    }
    
    /**
     * Selects an option or, if currently on the title screen, enters the main menu.
     */
    protected class ConfirmAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            confirm();
        }
    }
}
