package TripleT;

import java.io.Serializable;

/** 
 * A class containing the data for an individual game save file.
 * The game should contain three such save files.
 * @author Owen Jow
 */
public class SaveFileInfo implements Serializable {
    int world = 1, level = 1, percentage;
    String worldStr = "Start new game!"; // for example, "Honey Haunt" or "Syrup Stronghold"
    
    /**
     * Updates the save file's percentage (this should be called after a new level is reached).
     * If the percentage is already up-to-date, this method will do nothing.
     */
    void updatePercentage() {
        // There are 2 stages total (1 world, with 2 levels)
        percentage = (int) (((double) (world + level - 2) * 100) / 2);
    }
    
    /**
     * Sets the most recently unlocked level to LEVEL + 1.
     * Also updates the file's world description.
     */
    void incrementLevel() {
        level += 1;
        if (level == 2) {
            worldStr = "Vacuum Vista";
        } else if (level == 3) {
            worldStr = "Congratulatory message";
        }
    }
    
    /**
     * Restores all data to initial values, as if it were a brand-new save file.
     */
    void clear() {
        world = 1;
        level = 1;
        percentage = 0;
        worldStr = "Start new game!";
    }
}
