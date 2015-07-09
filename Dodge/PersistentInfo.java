package Dodge;

import java.io.Serializable;

/** 
 * A collection of data that should persist through different play sessions.
 * In other words, it represents the data that will be saved.
 * This will also serve as a container for individual save file data.
 * @author Owen Jow
 */
public class PersistentInfo implements Serializable {
    private static final long serialVersionUID = 7042866136132959358L; // version identifier
    int dodgeHighScore; // the game's high score
}
