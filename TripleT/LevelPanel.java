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

/**
 * An abstract blueprint for a level panel, which includes activation and painting methods
 * in addition to a drawForeground method that should be overridden by subclasses.
 * @author Owen Jow
 */
abstract class LevelPanel extends KPanel implements ActionListener {
    protected Timer timer;
    protected Image backgroundImg, fgImage; // fg = foreground
    protected boolean isPaused;
    protected int pauseIndex, counter;
    protected Foreground foreground;
    Kirby kirby;
    
    // Action names (for key bindings)
    protected static final String PAUSELECT = "pauselect", RIGHT_PRESSED = "rightpr", LEFT_PRESSED = "leftpr", 
            DOWN_PRESSED = "downpr", UP_PRESSED = "uppr", GBA_A_PRESSED = "gbaapr", RIGHT_RELEASED = "rightre", 
            LEFT_RELEASED = "leftre", DOWN_RELEASED = "downre", UP_RELEASED = "upre";
    
    /**
     * Reset values for the panel. Override this for individualized reset behavior.
     */
    protected void reset() {
        /* By default this method will do very little */
        isPaused = false;
        pauseIndex = 0;
        counter = 0;
    }
    
    protected void activate() {
        reset();
        
        // Start the timer that will continually request focus for this panel
        timer = new Timer(5, this);
        timer.start();
        requestFocus();
    }
    
    public void deactivate() {
        timer.stop();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!isPaused) {
            counter++; // update this counter every time the timer fires an ActionEvent
            if (kirby.updateFrame()) {
                // Kirby's current frame has changed, so we'll need to repaint
                repaint();
            }
        
            // Set Kirby's aerial status (i.e. check if Kirby is at ground level)
            if (kirby.isInAir()) {
                if (kirby.getY() >= foreground.groundLevel) {
                    kirby.setDY(0);
                    kirby.toggleInAir(); 
                }
            } else {
                if (kirby.getY() < foreground.groundLevel) { kirby.toggleInAir(); }
            }
        
            if (counter % 2 == 0) {
                // Because we don't want to move TOO fast!
                kirby.moveWithinBoundaries(0, TripleTWindow.SCR_WIDTH, 0, 
                        TripleTWindow.SCR_HEIGHT, foreground);
                repaint();
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
        g2.drawImage(backgroundImg, 0, 0, null);
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
    abstract void drawForeground(Graphics2D g2);
    
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
        } else {
            isPaused = true;
            repaint();
        }
    }
    
    protected void aPressed() {
        if (!isPaused) {
            kirby.aPressed();
            repaint();
        }
    }
    
    protected void rightPressed() {
        if (!isPaused) {
            kirby.rightPressed();
            repaint();
        }
    }
    
    protected void leftPressed() {
        if (!isPaused) {
            kirby.leftPressed();
            repaint();
        }
    }
    
    protected void downPressed() {
        if (isPaused) {
            pauseIndex = 1 - pauseIndex;
        } else {
            kirby.downPressed();
        }
        
        repaint();
    }
    
    protected void upPressed() {
        if (isPaused) { 
            pauseIndex = 1 - pauseIndex; 
        } else {
            kirby.upPressed();
        }
        
        repaint();
    }
    
    protected void rightReleased() {
        kirby.rightReleased();
        repaint();
    }
    
    protected void leftReleased() {
        kirby.leftReleased();
        repaint();
    }
    
    protected void downReleased() {
        kirby.downReleased();
        repaint();
    }
    
    protected void upReleased() {
        kirby.upReleased();
        repaint();
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
