package TripleT;

/**
 * A POJO containing door-related information.
 *
 * In the Kirby universe (and most other universes, too!) 
 * doors represent entry points to some other place. 
 * They can often be entered from two sides.
 * 
 * Accordingly, our Door class possesses information
 * about the place on the "left" side of the door, the place
 * on the "right" side of the door, and the position of the door on both sides.
 * (The information will be null or invalid if there is no door on one side.)
 * 
 * Note that the concept of a "left" side or a "right" side is somewhat arbitrary.
 * For the moment, "left" is determined here as the side that appears first chronologically.
 *
 * @author Owen Jow
 */
public class Door {
    private int xPosL = -1, xPosR = -1; // initial door x-positions
    private LevelPanel levelL, levelR; // the levels associated with each side of the door
    private String descL, descR; // the identifiers for each level
    
    /**
     * The constructor for doors with an entry point on only one side.
     * We pass in the attributes for one side, and determine which side that is
     * by the IS_LEFT parameter.
     * @param xPos the left x-coordinate of the door within the level
     * @param level the panel representation of the level on ** THE OTHER SIDE **
     * @param desc the above level's String identifier
     * @param isLeft a boolean specifying whether the door is in the LEFT or the RIGHT level
     */
    public Door(int xPos, LevelPanel level, String desc, boolean isLeft) {
        if (isLeft) {
            xPosL = xPos;
            levelR = level; // remember: this is the DESTINATION door
            descR = desc; // (and this is the ID of the destination door)
        } else {
            xPosR = xPos;
            levelL = level;
            descL = desc;
        }
    }
    
    /**
     * Constructs a door with levels on both sides.
     */
    public Door(int xPosL, int xPosR, LevelPanel levelL, LevelPanel levelR, 
            String descL, String descR) {
        this.xPosL = xPosL;
        this.xPosR = xPosR;
        this.levelL = levelL;
        this.levelR = levelR;
        this.descL = descL;
        this.descR = descR;
    }
    
    /**
     * Gets either the left x-coordinate of the LEFT door, or the RIGHT door.
     * @param isLeft specifies which x-coordinate to supply
     * @return either xPosL or xPosR
     */
    public int getX(boolean isLeft) {
        return (isLeft) ? xPosL : xPosR;
    }
    
    /**
     * Same as the above, except it retrieves the level instead.
     * @return the level on one side of the door
     */
    public LevelPanel getLevel(boolean isLeft) {
        return (isLeft) ? levelL : levelR;
    }
    
    /**
     * Same as the above two methods, but it returns a string ID.
     * @return string ID for the level on either side of the door
     */
    public String getDesc(boolean isLeft) {
        return (isLeft) ? descL : descR;
    }
}