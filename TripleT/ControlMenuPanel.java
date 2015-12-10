package TripleT;

import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.KeyStroke;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.io.IOException;

/**
 * The panel for the controls menu, in which the user will be able to customize controls.
 * @author Owen Jow
 */
public class ControlMenuPanel extends EscapableMenuPanel {
    private static final Image MENU_IMG = Images.get("controlsMenu");
    private static Font novecento20;
    private static Font novecento17;
    private static final int NAME_X = 146, KEY_X = 346, BASE_Y = 100, Y_OFFSET = 30, DESC_X = 84;
    private int controlSelected = -1; // the index of the control currently being changed
    private KeyAdapter kl;
    private LinkedList<ArrayList<String>> oldActions;
    private HashSet<KeyStroke> newKeys;
    
    // The name of each control
    private static final String[] CTRL_NAMES = new String[] { "Left", "Right", "Up", 
            "Down", "Jump/Attack", "Special Attack", "Pause/Confirm" };
    
    // The keys associated with each control
    private int[] keys;
    
    // Descriptions of the controls
    private static final String[] CTRL_DESCRIPTIONS = new String[] { 
        "moves kirby left; switches left on menus", // left
        "moves kirby right; switches right on menus", // right
        "jumps/floats; switches up on menus", // up
        "drops/crouches; switches down on menus", // down
        "jumps; attacks (slides) while crouching", // jump/attack
        "uses a copy ability/special attack", // special attack
        "pauses the game; confirms on menus", // pause/confirm
    };
    
    public void activate() {
        imgIndex = 0;
        requestFocus();
        
        kl = new KeyListener();
        addKeyListener(kl);
    }
    
    /**
     * Initializes a bunch of attributes, most notably the pInfo's keys.
     * Should be called basically ASAP.
     */
    public void initialize() {
        oldActions = new LinkedList<ArrayList<String>>();
        newKeys = new HashSet<KeyStroke>();
        keys = new int[] { GameState.pInfo.leftKey, 
                GameState.pInfo.rightKey, GameState.pInfo.upKey, GameState.pInfo.downKey,
                GameState.pInfo.jumpKey, GameState.pInfo.attackKey, GameState.pInfo.pauseKey };
             
        // Fonts
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, 
                    getClass().getResourceAsStream("/fonts/Novecento.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        
        novecento20 = new Font("Novecento sans wide", Font.PLAIN, 20);
        novecento17 = new Font("Novecento sans wide", Font.PLAIN, 17);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(MENU_IMG, 0, 0, null);
        
        // Set up text font and color
        g.setFont(novecento20);
        g.setColor(Color.GRAY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        
        for (int i = 0; i < keys.length; i++) {
            // Change color if the current key should be highlighted
            g2.setColor((i == imgIndex) ? Color.WHITE : Color.GRAY);
            
            // Draw the control name
            g2.drawString(CTRL_NAMES[i], NAME_X, BASE_Y + i * Y_OFFSET);
            if (i == controlSelected) {
                g2.drawString("...", KEY_X, BASE_Y + i * Y_OFFSET);
            } else {
                g2.drawString(KeyEvent.getKeyText(keys[i]), KEY_X, BASE_Y + i * Y_OFFSET);
            }
        }
        
        // Draw the currently highlighted key's description on the bottom
        g.setFont(novecento17);
        g.setColor(Color.GRAY);
        g2.drawString(CTRL_DESCRIPTIONS[imgIndex], DESC_X, 
                BASE_Y + (keys.length + 1) * Y_OFFSET - 10);
    }
    
    /**
     * Checks if the key specified by KEY_CODE is the same as any other control (or, to be precise,
     * any control that's not the one associated with KEY_INDEX). If so, we will select 
     * the offending duplicate (by altering the imgIndex and controlSelected variables) 
     * and thereby force the user to bind a new key for that control.
     * @param keyCode the key that we'll be checking for duplicates against
     * @param keyIndex the index of the control that we don't need to check against
     * @return true if the specified key has a duplicate
     */
    private boolean runUniquenessCheck(int keyCode, int keyIndex) {
        for (int i = 0; i < keys.length; i++) {
            if (i != keyIndex && keyCode == keys[i]) {
                imgIndex = i;
                controlSelected = i; // make the user change the duplicate to something else
                return true;
            }
        }
        
        // We didn't find a duplicate, so the new control must be unique
        return false;
    }
    
    /**
     * The KeyListener for the controls menu.
     * Controls registered: UP, DOWN, ENTER, DELETE
     */
    public class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            if (keyCode == KeyEvent.VK_UNDEFINED) { return; } // undefined? say whaaat?
            
            if (keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_BACK_SPACE
                    || keyCode == KeyEvent.VK_ESCAPE) {
                // If the user is changing a control, we don't want to leave. Otherwise...
                if (controlSelected < 0) {
                    // Back to the main menu for us!
                    removeKeyListener(kl);
                    TripleT.savePersistentInfo(GameState.pInfo);
                    GameState.layout.show(GameState.contentPanel, "mainMenu");
                    GameState.menuPanel.requestFocus();
                }
            } else if (controlSelected >= 0) {
                // Update the key associated with whatever control is selected
                switch (imgIndex) {
                    case 0:
                        GameState.pInfo.leftKey = keyCode;
                        break;
                    case 1:
                        GameState.pInfo.rightKey = keyCode;
                        break;
                    case 2:
                        GameState.pInfo.upKey = keyCode;
                        break;
                    case 3:
                        GameState.pInfo.downKey = keyCode;
                        break;
                    case 4:
                        GameState.pInfo.jumpKey = keyCode;
                        break;
                    case 5:
                        GameState.pInfo.attackKey = keyCode;
                        break;
                    default:
                        GameState.pInfo.pauseKey = keyCode;
                        break;
                }
                
                int currIndex = imgIndex; // saving this here, in case the uniqueness check mutates it
                KeyStroke oldKey = KeyStroke.getKeyStroke(keys[imgIndex], 0);
                KeyStroke newKey = KeyStroke.getKeyStroke(keyCode, 0);
                boolean shouldRemove = !newKeys.contains(oldKey);
                    
                // Check if the new control is the same as any other control
                if (!runUniquenessCheck(keyCode, imgIndex)) {
                    controlSelected = -1; // no duplicates keys! We good
                    
                    // Put changes into effect
                    if (oldActions.size() > 0) {
                        // There was a conflict earlier, so we'll need to pass in the old actions explicitly
                        TripleTWindow.updateKeyBindings(oldKey, newKey, oldActions.removeFirst(), shouldRemove);
                    } else {
                        TripleTWindow.updateKeyBindings(oldKey, newKey, shouldRemove);
                    }
                    
                    newKeys.clear(); // we don't have to worry about overwriting new keys anymore
                } else {
                    // Save the function of the new key so that the bindings don't get confused
                    oldActions.add(TripleTWindow.getActionsForKey(newKey));
                    if (oldActions.size() > 1) {
                        TripleTWindow.updateKeyBindings(oldKey, newKey, oldActions.removeFirst(), shouldRemove);
                    } else {
                        TripleTWindow.updateKeyBindings(oldKey, newKey, shouldRemove);
                    }
                    
                    newKeys.add(newKey);
                }
                
                keys[currIndex] = keyCode;
            } else if (keyCode == GameState.pInfo.upKey) {
                if (imgIndex > 0) { imgIndex--; }
            } else if (keyCode == GameState.pInfo.downKey) {
                if (imgIndex < keys.length - 1) { imgIndex++; }
            } else if (keyCode == KeyEvent.VK_ENTER) {
                controlSelected = imgIndex;
            }
            
            repaint();
        }
    }
}
