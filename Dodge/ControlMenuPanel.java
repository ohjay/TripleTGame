package Dodge;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;

/**
 * The panel for the controls menu, in which the user will be able to customize controls.
 * @author Owen Jow
 */
public class ControlMenuPanel extends MenuPanel {
    private static final int X_COORD = 377, LEFT_Y = 153, RIGHT_Y = 192, UP_Y = 228, 
            DOWN_Y = 266, POWERUP_Y = 304, PAUSE_Y = 344;
    
    /** 
     * Constructs a control menu panel. 
     * Initializes images and assigns a value to a key listener variable.
     */
    public ControlMenuPanel() {
        // Initialize images
        menuImages = new Image[] { Images.get("controlLeft"), Images.get("controlRight"),
                 Images.get("controlUp"), Images.get("controlDown"), Images.get("controlPowerup"),
                 Images.get("controlPause") };
        this.kl = new KeyListener();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuImages[(imgIndex > 5) ? 5 : imgIndex], 0, 0, null);
        
        // Now we have to display the actual keys that the controls are mapped to
        // We'll draw these keys as strings:
        g.setFont(new Font("Verdana", Font.BOLD, 15));
        g.setColor(Color.BLACK);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.leftKey), X_COORD, LEFT_Y);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.rightKey), X_COORD, RIGHT_Y);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.upKey), X_COORD, UP_Y);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.downKey), X_COORD, DOWN_Y);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.powerupKey), X_COORD, POWERUP_Y);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.pauseKey), X_COORD, PAUSE_Y);
        g.setColor(Color.WHITE);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.leftKey), X_COORD - 1, LEFT_Y - 1);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.rightKey), X_COORD - 1, RIGHT_Y - 1);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.upKey), X_COORD - 1, UP_Y - 1);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.downKey), X_COORD - 1, DOWN_Y - 1);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.powerupKey), X_COORD - 1, POWERUP_Y - 1);
        g2.drawString(KeyEvent.getKeyText(GameState.pInfo.pauseKey), X_COORD - 1, PAUSE_Y - 1);
    }
    
    public class KeyListener extends KeyAdapter {
        /**
         * Handles the menu's response to keys being pressed.
         * @param KeyEvent evt the extraordinary event that is a key being pressed
         */
        public void keyPressed(KeyEvent evt) {
            switch (imgIndex) {
                case 0:
                    GameState.pInfo.leftKey = evt.getKeyCode();
                    break;
                case 1:
                    GameState.pInfo.rightKey = evt.getKeyCode();
                    break;
                case 2:
                    GameState.pInfo.upKey = evt.getKeyCode();
                    break;
                case 3:
                    GameState.pInfo.downKey = evt.getKeyCode();
                    break;
                case 4:
                    GameState.pInfo.powerupKey = evt.getKeyCode();
                    break;
                case 5:
                    GameState.pInfo.pauseKey = evt.getKeyCode();
                    break;
                default:
                    deactivate();
                    GameState.layout.show(GameState.contentPanel, "mainMenu");
                    GameState.mainMenuPanel.activate();
                    break;
            }
            
            imgIndex++;
            repaint();
        }
    }
}
