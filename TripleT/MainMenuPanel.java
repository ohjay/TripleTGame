package TripleT;

import java.awt.Image;
import java.awt.event.KeyEvent;

/** 
 * The main menu panel for the game.
 * Does not contribute anything to or interact in any way with the game state, 
 * because the menu should always start up the same way.
 * @author Owen Jow
 */
public class MainMenuPanel extends MenuPanel {
    
    /** 
     * Constructs a main menu panel. Sets up game state data and initializes menu images.
     * @param state any relevant (and irrelevant) game data
     */
    public MainMenuPanel(GameState state) {
        super(state);
        
        // Initialize images
        menuImages = new Image[] { Images.get("storyH"), Images.get("minigamesH"),
                    Images.get("cutscenesH"), Images.get("controlsH"), Images.get("optionsH"), 
                    Images.get("creditsH") };
    }
    
    /**
     * Activates the main menu panel.
     * After this method is executed, the user will be able to switch between menu options
     * with the keyboard.
     */
    public void activate() {
        activate(new KeyListener());
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
            
            if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT) {
                decrementIndex();
            } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_RIGHT) {
                incrementIndex();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                deactivate();
                switch (imgIndex) {
                    case 0:
                        state.layout.show(state.contentPanel, "storyMenu");
                        break;
                    case 1:
                        state.layout.show(state.contentPanel, "minigameMenu");
                        state.minigameMPanel.activate();
                        break;
                    case 2:
                        state.layout.show(state.contentPanel, "cutsceneMenu");
                        break;
                    case 3:
                        state.layout.show(state.contentPanel, "controlMenu");
                        break;
                    case 4:
                        state.layout.show(state.contentPanel, "optionMenu");
                        break;
                    default:
                        state.layout.show(state.contentPanel, "credits");
                        state.creditsPanel.activate();
                        break;
                }
            }
            
            repaint();
        }
    }
}
