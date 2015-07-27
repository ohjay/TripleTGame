package TripleT;

import java.io.Serializable;

/** 
 * A class containing the data for an individual game save file.
 * The game should contain three such save files.
 * @author Owen Jow
 */
public class SaveFileInfo implements Serializable {
    int world, level, percentage;
    String worldStr = "Start new game!"; // for example, "Honey Haunt" or "Syrup Stronghold"
    
    /**
     * Updates the save file's percentage (this should be called after a new level is reached).
     * If the percentage is already up-to-date, this method will do nothing.
     */
    void updatePercentage() {
        // There are 15 stages total (3 worlds, each with 4 levels and 1 boss)
        percentage = world * level / 15 * 100;
    }
    
    /**
     * Restores all data to initial values, as if it were a brand-new save file.
     */
    void clear() {
        world = 0;
        level = 0;
        percentage = 0;
        worldStr = "Start new game!";
    }
}
