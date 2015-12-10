package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.KeyStroke;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.AbstractAction;
import java.util.LinkedList;

/**
 * An abstract blueprint for a level panel, which includes activation and painting methods
 * in addition to a drawForeground method that should be overridden by subclasses.
 * @author Owen Jow
 */
abstract class LevelPanel extends KPanel implements ActionListener {
    protected Timer timer;
    protected Image backgroundImg, fgImage; // fg = foreground
    protected boolean isPaused, isSlideOff, isLeft; 
    // ^ isSlideOff: indicates whether or not Kirby just slid off a ledge
    // ^ isLeft: indicates whether or not this level is the level on the "LEFT" side of a door
    protected int pauseIndex, counter;
    protected double backgroundX, bgDXMultiplier = 1.1, fgDXMultiplier = 1.3;
    protected Foreground foreground;
    protected LinkedList<Door> doors = new LinkedList<Door>();
    protected Door activeDoor;
    protected SaveFileInfo file;
    Kirby kirby;
    
    // Action names (for key bindings)
    protected static final String PAUSELECT = "pauselect", RIGHT_PRESSED = "rightpr", LEFT_PRESSED = "leftpr", 
            DOWN_PRESSED = "downpr", UP_PRESSED = "uppr", GBA_A_PRESSED = "gbaapr", RIGHT_RELEASED = "rightre", 
            LEFT_RELEASED = "leftre", DOWN_RELEASED = "downre", UP_RELEASED = "upre", ENTER_PRESSED = "enterpre";
    
    /**
     * Reset values for the panel. Override this for individualized reset behavior.
     */
    protected void reset() {
        /* By default this method will do very little */
        isPaused = false;
        pauseIndex = 0;
        counter = 0;
        activeDoor = null;
        backgroundX = 0;
    }
    
    protected void activate(SaveFileInfo file) {
        reset();
        
        // Start the timer that will continually request focus for this panel
        timer = new Timer(5, this);
        timer.start();
        requestFocus();
        this.file = file;
    }
    
    public void deactivate() {
        timer.stop();
    }
    
    /**
     * Initializes doors for the level. This method is necessary because door setup
     * must occur after all of the levels have been constructed.
     * 
     * Since LevelPanel is an abstract class, this method must be overridden
     * by any panels that include doors.
     */
    public void initializeDoors() {
        /* Default behavior: nothing. No doors as of present. */
    }
    
    /**
     * Returns true if the foreground should be moved.
     * @param dx Kirby's intended horizontal delta value
     */
    private boolean shouldMoveForeground(int dx) {
        if (dx == 0 || foreground.intersects(Math.min(22, kirby.spriteWidth), kirby.spriteHeight, 
                kirby.getX() + dx, kirby.getY())) {
            return false;   
        } else if (dx > 0) {
            return kirby.getX() + kirby.spriteWidth >= TripleTWindow.SCR_WIDTH / 2
                    && foreground.leftOffset - dx * fgDXMultiplier
                    >= -foreground.length + TripleTWindow.SCR_WIDTH;
        } else {
            return kirby.getX() <= TripleTWindow.SCR_WIDTH / 2 
                    && foreground.leftOffset - dx * fgDXMultiplier <= 0;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!isPaused) {
            counter++; // update this counter every time the timer fires an ActionEvent
            if (kirby.updateFrame()) {
                // Kirby's current frame has changed, so we'll need to repaint
                repaint();
            }
            
            if (kirby.animationEquals(Kirby.Animation.ENTERING)) {
                // We're getting out of here, man. Don't do anything else!
                if (kirby.hasEntered()) {
                    kirby.toggleEntered();
                    deactivate();
                    GameState.layout.show(GameState.contentPanel, activeDoor.getDesc(!isLeft));
                    activeDoor.getLevel(!isLeft).activate(file);
                    
                    file.incrementLevel();
                    file.updatePercentage();
                    TripleT.savePersistentInfo(GameState.pInfo);
                }
            } else {
                // Life proceeds as normal
                // Set Kirby's aerial status (i.e. check if Kirby is at ground level)
                if (kirby.isInAir()) {
                    if (kirby.getDY() > 0 && foreground.intersects(Math.min(22, kirby.spriteWidth), 
                            kirby.spriteHeight, kirby.getX(), kirby.getY() + 1)) {
                        // Looks like Kirby's made his landing!
                        kirby.setDY(0);
                        kirby.toggleInAir(); 
                    
                        if (Math.abs(kirby.getDX()) == 2 && !isSlideOff) {
                            kirby.setAnimation(Kirby.Animation.RUNNING);
                        }
                    }
                } else if (!foreground.intersects(Math.min(22, kirby.spriteWidth), 
                        kirby.prevHeight + 3, kirby.getX(), kirby.getY() + 2)) { 
                    // Kirby is actually in the air (after falling/walking off something)
                    isSlideOff = (kirby.animationEquals(Kirby.Animation.SLIDING)) ? true : false;
                    kirby.setDY(1);
                    kirby.setAnimation(Kirby.Animation.FALLING);
                    kirby.setCurrentFrame(0);
                    kirby.toggleInAir();
                }
        
                if (counter % 2 == 0) {
                    // Modulo two because we don't want to move TWO fast!
                    int dx = kirby.getDX();
                    if (shouldMoveForeground(dx)) {
                        // Move the background/foreground alongside Kirby
                        backgroundX -= bgDXMultiplier * dx;
                        foreground.horizontalShift(fgDXMultiplier * (-dx));
                        kirby.moveVertically(0, TripleTWindow.SCR_HEIGHT, foreground);
                    } else {
                        kirby.moveWithinBoundaries(0, TripleTWindow.SCR_WIDTH, 0, 
                                TripleTWindow.SCR_HEIGHT, foreground);
                    }
                
                    repaint();
                }
            }
        }
    }
    
    /**
     * Paints the level background (and the foreground through the drawForeground method).
     * @param g a Graphics object used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImg, (int) backgroundX, 0, null);
        drawForeground(g2);
        
        if (isPaused) {
            g2.drawImage(Images.get("pauseOverlay" + pauseIndex), 0, 0, null);
        }
    }
    
    /**
     * Draws the foreground (which includes sprites). Does nothing for now, but this should be
     * overridden by subclasses that want foregrounds and/or sprites to be drawn!
     * @param g2 a Graphics2D object used for [pick one: drawing, painting, what's the difference?]
     */
    protected void drawForeground(Graphics2D g2) {
        /* Extend me, levels with foregrounds! */
    }
    
    //================================================================================
    // Key binding logic
    //================================================================================
    
    void setKeyBindings() {
        // Input map bindings [pressed]
        InputMap iMap = getInputMap();
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0), RIGHT_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0), LEFT_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.downKey, 0), DOWN_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.upKey, 0), UP_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.pauseKey, 0), PAUSELECT);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.jumpKey, 0), GBA_A_PRESSED);
        iMap.put(KeyStroke.getKeyStroke("ENTER"), ENTER_PRESSED);
        // [Released]
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0, true), RIGHT_RELEASED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0, true), LEFT_RELEASED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.downKey, 0, true), DOWN_RELEASED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.upKey, 0, true), UP_RELEASED);
        
        // Action map bindings [pressed]
        ActionMap aMap = getActionMap();
        aMap.put(RIGHT_PRESSED, new RightPressedAction());
        aMap.put(LEFT_PRESSED, new LeftPressedAction());
        aMap.put(DOWN_PRESSED, new DownPressedAction());
        aMap.put(UP_PRESSED, new UpPressedAction());
        aMap.put(PAUSELECT, new PauselectAction());
        aMap.put(GBA_A_PRESSED, new AAction());
        aMap.put(ENTER_PRESSED, new EnterPressedAction());
        // [Released]
        aMap.put(RIGHT_RELEASED, new RightReleasedAction());
        aMap.put(LEFT_RELEASED, new LeftReleasedAction());
        aMap.put(DOWN_RELEASED, new DownReleasedAction());
        aMap.put(UP_RELEASED, new UpReleasedAction());
    }
    
    //================================================================================
    // Action methods (to be overridden for special behavior/controls)
    //================================================================================
    
    /**
     * Pauses the game, or selects an option if the game is already paused.
     */
    protected void pauselect() {
        if (isPaused) {
            if (pauseIndex == 0) {
                isPaused = false;
            } else {
                deactivate();
                GameState.layout.show(GameState.contentPanel, "mainMenu");
                GameState.menuPanel.requestFocus();
            }
        } else if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            isPaused = true;
            repaint();
        }
    }
    
    protected void aPressed() {
        if (!isPaused && !kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.aPressed();
            repaint();
        }
    }
    
    protected void rightPressed() {
        if (!isPaused && !kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.rightPressed();
            repaint();
        }
    }
    
    protected void leftPressed() {
        if (!isPaused && !kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.leftPressed();
            repaint();
        }
    }
    
    protected void downPressed() {
        if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            if (isPaused) {
                pauseIndex = 1 - pauseIndex;
            } else {
                kirby.downPressed();
            }
        
            repaint();
        }
    }
    
    protected void upPressed() {
        if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            if (isPaused) { 
                pauseIndex = 1 - pauseIndex; 
            } else {
                if (!kirby.isInAir()) {
                    for (Door d : doors) {
                        if (Math.abs(d.getX(isLeft) + foreground.leftOffset - kirby.getX()) <= 3) {
                            kirby.enterDoor();
                            activeDoor = d;
                            return;
                        }
                    }
                }
            
                kirby.upPressed();
            }
        
            repaint();
        }
    }
    
    protected void enterPressed() {
        if (isPaused) {
            if (pauseIndex == 0) {
                isPaused = false;
            } else {
                deactivate();
                GameState.layout.show(GameState.contentPanel, "mainMenu");
                GameState.menuPanel.requestFocus();
            }
        }
    }
    
    protected void rightReleased() {
        if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.rightReleased();
            repaint();
        }
    }
    
    protected void leftReleased() {
        if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.leftReleased();
            repaint();
        }
    }
    
    protected void downReleased() {
        if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.downReleased();
            repaint();
        }
    }
    
    protected void upReleased() {
        if (!kirby.animationEquals(Kirby.Animation.ENTERING)) {
            kirby.upReleased();
            repaint();
        }
    }
    
    //================================================================================
    // Action classes (to be inherited)
    //================================================================================
    
    protected class PauselectAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            pauselect();
        }
    }
    
    /**
     * The action associated with a press of the "jump/attack" button.
     * On a GBA Kirby game, this is the A button.
     */
    protected class AAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            aPressed();
        }
    }
    
    protected class RightPressedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            rightPressed();
        }
    }
    
    protected class LeftPressedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            leftPressed();
        }
    }
    
    protected class DownPressedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            downPressed();
        }
    }
    
    protected class UpPressedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            upPressed();
        }
    }
    
    protected class EnterPressedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            enterPressed();
        }
    }
    
    protected class RightReleasedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            rightReleased();
        }
    }
    
    protected class LeftReleasedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            leftReleased();
        }
    }
    
    protected class DownReleasedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            downReleased();
        }
    }
    
    protected class UpReleasedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            upReleased();
        }
    }
}
