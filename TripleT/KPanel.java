package TripleT;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.InputMap;

/**
 * A JPanel that contains logic common to all panels with key binding inputs.
 * @author Owen Jow
 */
public class KPanel extends JPanel {
    /**
     * Associates the KeyStroke KEY with ACTION in the input map I_MAP.
     * This method is necessary because SHIFT and ALT have out-of-the-ordinary behavior 
     * as key bindings, and due to custom controls we most likely don't know beforehand what 
     * KEY will be.
     */
    void addToInputMap(InputMap iMap, KeyStroke key, String action) {
        if (key == KeyStroke.getKeyStroke("SHIFT")) {
            iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK), action);
        } else if (key == KeyStroke.getKeyStroke("ALT")) {
            iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.ALT_DOWN_MASK), action);
        } else {
            iMap.put(key, action);
        }
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
}
