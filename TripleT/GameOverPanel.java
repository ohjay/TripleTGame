package TripleT;

import java.awt.Image;

/**
 * The panel that losers are shown.
 * Just kidding. By playing this game at all, you're a winner in my heart.
 * @author Owen Jow
 */
public class GameOverPanel extends EscapableMenuPanel {
    SaveFileInfo file;
    
    /** 
     * Constructs a Dodge! pause panel, adding the proper backgrounds to this image set.
     */
    public GameOverPanel() {
        menuImages = new Image[] { Images.get("gameOver1"), Images.get("gameOver2") };
    }
    
    /**
     * Activates the panel with FILE as the current save file.
     */
    public void activate(SaveFileInfo file) {
        super.activate();
        this.file = file;
    }
    
    /**
     * Escape shouldn't do anything. Because there is no escape.
     * (Damn, I am funny.)
     */
    @Override
    protected void escape() {}
    
    @Override
    protected void confirm() {
        if (imgIndex == 0) {
            // We know it's level 2 because I didn't implement anything else
            GameState.layout.show(GameState.contentPanel, "level2");
            GameState.level2.activate(file);
        } else {
            GameState.layout.show(GameState.contentPanel, "mainMenu");
            GameState.menuPanel.requestFocus();
        }
    }
}
