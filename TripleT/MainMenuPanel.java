package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/** 
 * The main menu panel for the game.
 * @author Owen Jow
 */
public class MainMenuPanel extends MenuPanel {
    private boolean isTitleScr;
    private static final Image TITLE_SCR = Images.get("titleScr");
    
    /** 
     * Constructs a main menu panel by initializing menu images.
     */
    public MainMenuPanel() {
        // Initialize images
        menuImages = new Image[] { Images.get("storyH"), Images.get("minigamesH"),
                Images.get("cutscenesH"), Images.get("controlsH"), Images.get("optionsH"), 
                Images.get("creditsH") };
        isTitleScr = true;
    }
    
    /**
     * Activates the main menu panel.
     * After this method is executed, the user will be able to switch between menu options
     * with the keyboard.
     */
    public void activate() {
        activate(new KeyListener());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage((isTitleScr) ? TITLE_SCR : menuImages[imgIndex], 0, 0, null);
    }
    
    /**
     * The KeyListener for the main menu.
     * Controls registered: UP, DOWN, LEFT, RIGHT, and ENTER.
     */
    public class KeyListener extends MenuPanel.KeyListener {
        /**
         * Handles the menu's response to keys being pressed.
         * @param KeyEvent evt the extraordinary event that is a key being pressed
         */
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            
            if (isTitleScr) {
                isTitleScr = false; // a key was pressed, so it's time to go
            } else if (keyCode == GameState.pInfo.upKey || keyCode == GameState.pInfo.leftKey) {
                decrementIndex();
            } else if (keyCode == GameState.pInfo.downKey || keyCode == GameState.pInfo.rightKey) {
                incrementIndex();
            } else if (keyCode == KeyEvent.VK_ENTER || keyCode == GameState.pInfo.pauseKey) {
                deactivate();
                switch (imgIndex) {
                    case 0:
                        GameState.layout.show(GameState.contentPanel, "storyMenu");
                        GameState.storyMPanel.activate();
                        break;
                    case 1:
                        GameState.layout.show(GameState.contentPanel, "minigameMenu");
                        GameState.minigameMPanel.activate();
                        break;
                    case 2:
                        GameState.layout.show(GameState.contentPanel, "cutsceneMenu");
                        break;
                    case 3:
                        GameState.layout.show(GameState.contentPanel, "controlMenu");
                        GameState.controlMPanel.activate();
                        break;
                    case 4:
                        GameState.layout.show(GameState.contentPanel, "optionMenu");
                        break;
                    default:
                        GameState.layout.show(GameState.contentPanel, "credits");
                        GameState.creditsPanel.activate();
                        break;
                }
            }
            
            repaint();
        }
    }
}
