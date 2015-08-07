package TripleT;

import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * The panel for the controls menu, in which the user will be able to customize controls.
 * @author Owen Jow
 */
public class ControlMenuPanel extends MenuPanel {
    private static final Image MENU_IMG = Images.get("controlsMenu");
    private static final Font NOVECENTO_20 = new Font("Novecento sans wide", Font.PLAIN, 20);
    private static final Font NOVECENTO_17 = new Font("Novecento sans wide", Font.PLAIN, 17);
    private static final int NAME_X = 146, KEY_X = 346, BASE_Y = 110, Y_OFFSET = 30, DESC_X = 84;
    private int controlSelected = -1; // the index of the control currently being changed
    
    // The name of each control
    private static final String[] CTRL_NAMES = new String[] { "Left", "Right", "Up", 
            "Down", "Jump/Attack", "Special Attack", "Pause/Confirm" };
    
    // The keys associated with each control
    private int[] keys = new int[] { GameState.pInfo.leftKey, 
            GameState.pInfo.rightKey, GameState.pInfo.upKey, GameState.pInfo.downKey,
            GameState.pInfo.jumpKey, GameState.pInfo.attackKey, GameState.pInfo.pauseKey };
    
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
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(MENU_IMG, 0, 0, null);
        
        // Set up text font and color
        g.setFont(NOVECENTO_20);
        g.setColor(Color.GRAY);
        
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
        g.setFont(NOVECENTO_17);
        g.setColor(Color.GRAY);
        g2.drawString(CTRL_DESCRIPTIONS[imgIndex], DESC_X, 
                BASE_Y + (keys.length + 1) * Y_OFFSET - 10);
    }
    
    public void activate() {
        activate(new KeyListener());
    }
    
    /**
     * Checks if the key specified by KEY_CODE is the same as any other control (or, to be precise,
     * any control that's not the one associated with KEY_INDEX). If so, we will select 
     * the offending duplicate (by altering the imgIndex variable) 
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
        
        return false;
    }
    
    /**
     * The KeyListener for the controls menu.
     * Controls registered: UP, DOWN, ENTER, DELETE
     */
    public class KeyListener extends MenuPanel.KeyListener {
        @Override
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            if (keyCode == KeyEvent.VK_UNDEFINED) { return; } // undefined? say whaaat?
            
            if (keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_BACK_SPACE
                    || keyCode == KeyEvent.VK_ESCAPE && controlSelected < 0) {
                // Back to the main menu for us!
                deactivate();
                TripleT.savePersistentInfo(GameState.pInfo);
                GameState.layout.show(GameState.contentPanel, "mainMenu");
                GameState.menuPanel.activate();
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
                
                keys[imgIndex] = keyCode;
                if (!runUniquenessCheck(keyCode, imgIndex)) {
                    controlSelected = -1; // no duplicates keys! We good
                }
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
