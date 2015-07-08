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
    // Minigame data
    int dodgeHighScore;
    // Controls for the main game (can be customized, which is why they're in here)
    int leftKey, rightKey, upKey, downKey, jumpKey, attackKey, pauseKey;
    
    /**
     * No-argument constructor that initializes controls to their standard options.
     */
    public PersistentInfo() {
        leftKey = KeyEvent.VK_LEFT;
        rightKey = KeyEvent.VK_RIGHT;
        upKey = KeyEvent.VK_UP;
        downKey = KeyEvent.VK_DOWN;
        jumpKey = KeyEvent.VK_A;
        attackKey = KeyEvent.VK_S;
        pauseKey = KeyEvent.VK_SHIFT;
    }
}
