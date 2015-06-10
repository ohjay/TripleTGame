package TripleT;

import java.io.Serializable;

/** 
 * A collection of data that should persist through different play sessions.
 * In other words, it represents the data that will be saved.
 * This will also serve as a container for individual save file data.
 * @author Owen Jow
 */
public class PersistentInfo implements Serializable {
    int dodgeHighScore;
}
