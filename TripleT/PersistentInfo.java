package TripleT;

import java.io.Serializable;
import java.awt.event.KeyEvent;

/** 
 * A collection of data that should persist through different play sessions.
 * In other words, it represents the data that will be saved.
 * This will also serve as a container for individual save file data.
 * @author Owen Jow
 */
public class PersistentInfo implements Serializable {
    private static final long serialVersionUID = 7042866136132959357L; // version identifier
    
    // Minigame data
    int dodgeHighScore;
    // Controls for the main game (can be customized, which is why they're in here)
    int leftKey, rightKey, upKey, downKey, jumpKey, attackKey, pauseKey;
    
    // Save files
    SaveFileInfo saveFile1, saveFile2, saveFile3;
    
    /**
     * No-argument constructor that initializes controls to their standard options.
     * It also creates empty save files.
     */
    public PersistentInfo() {
        leftKey = KeyEvent.VK_LEFT;
        rightKey = KeyEvent.VK_RIGHT;
        upKey = KeyEvent.VK_UP;
        downKey = KeyEvent.VK_DOWN;
        jumpKey = KeyEvent.VK_A;
        attackKey = KeyEvent.VK_S;
        pauseKey = KeyEvent.VK_SHIFT;
        saveFile1 = new SaveFileInfo();
        saveFile2 = new SaveFileInfo();
        saveFile3 = new SaveFileInfo();
    }
}
