package TripleT;

/** 
 * A demo Star Kirby to be animated in the Dodge! pre-game screen.
 * @author Owen Jow
 */
public class DemoStarKirby extends StarKirby {
    private int xMin, xMax;
    
    /**
     * Constructs a demo Star Kirby with a starting position of (X_MIN, Y_POS).
     * It will also be bounded horizontally by X_MIN and X_MAX.
     * @param xMin the starting x-position for the demo Kirby
     * @param xMax the ending x-position for the demo Kirby
     * @param yPos the y-position for the demo Kirby
     */
    public DemoStarKirby(int xMin, int xMax, int yPos) {
        xPos = xMin;
        this.yPos = yPos;
        this.xMin = xMin;
        this.xMax = xMax;
    }
    
    /** 
     * Moves like a normal Kirby, but only within the given horizontal range.
     */
    @Override 
    public void move() {
        if (xPos + 2 > xMax) xPos = xMin;
        else xPos += (isPoweredUp()) ? 5 : 2;
    }
    
    /**
     * Powers up Kirby manually. 
     * After this method is executed, Kirby will be in a powered-up state.
     */
    public void powerUp() {
        state = State.POWERED_UP;
    }
}
