package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage((isTitleScr) ? TITLE_SCR : menuImages[imgIndex], 0, 0, null);
    }
    
    @Override
    protected void switchOptions(int direction) {
        if (!isTitleScr) {
            super.switchOptions(direction); // we only want to operate the menu if we're ON the menu
        }
    }
    
    /**
     * Selects an option or, if currently on the title screen, enters the main menu.
     */
    @Override
    protected void confirm() {
        if (isTitleScr) {
            isTitleScr = false; // a key was pressed, so it's time to go
        } else {
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
