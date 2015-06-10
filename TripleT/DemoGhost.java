package TripleT; 

/** 
 * A ghost for use in the Dodge! pre-game panel demos.
 * @author Owen Jow
 */
public class DemoGhost extends MinigameGhost {
    
    /** 
     * Creates a demo ghost at the specified coordinates that always faces left.
     * @param xPos the ghost's x-position
     * @param yPos the ghost's y-position
     */
    public DemoGhost(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        isFacingRight = false;
    }
}
