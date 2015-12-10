package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.KeyStroke;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * The second level of the game (world 1, level 2).
 *
 * In light of my decision to cease development on Triple T, 
 * this is now going to be the vacuum stage that I'd planned to save for later in the game.
 * @author Owen Jow
 */
public class Level2 extends LevelPanel implements ActionListener {
    private int yTraveled;
    private LinkedList<Block> blocks = new LinkedList<Block>();
    
    // Constants
    private static final int NUM_BLOCKS = 145, XRANGE = 400, Y_DIST = 105, 
            INITIAL_IMG_OFFSET = -14500, KIRBY_INITIAL_Y = 142;
    
    public Level2() {
        backgroundImg = Images.get("level2Backdrop");
    }
    
    /**
     * Reset everything to its initial state.
     * After this method is executed, Kirby will be at the top of the level
     * and the vacuum will be 0% clogged.
     */
    protected void reset() {
        isPaused = false;
        pauseIndex = 0;
        yTraveled = 0;
        kirby = new Level2Kirby(42, KIRBY_INITIAL_Y, Kirby.Animation.SOMERSAULTING);
        
        // Initialize our collection of blocks
        for (int i = 0; i < NUM_BLOCKS; i++) {
            blocks.add(new Block((int) (Math.random() * XRANGE), i * Y_DIST));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!isPaused) {
            yTraveled += 2;
            kirby.move();
            
            if (kirby.getFrame() == kirby.getAnimation().getLength()) {
                kirby.setCurrentFrame(0);
            } else if (kirby.getFrame() > 0) {
                kirby.updateFrame();
            }
            
            Iterator<Block> blockIter = blocks.iterator();
            while (blockIter.hasNext()) {
               Block b = blockIter.next();
               if (b.getRectangle().intersects(kirby.getRectangle())) {
                   b.accelerated = true;
                   kirby.setCurrentFrame(1);
               }
               
               b.yPos -= (b.accelerated) ? 3 : 1;
           
               if (b.yPos < -Block.BIG_BLOCKSIZE) {
                   blockIter.remove();
               }
            }
            
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImg, 0, INITIAL_IMG_OFFSET + yTraveled, null);
        
        for (Block b : blocks) {
            g2.drawImage(b.img, b.xPos, b.yPos, null);
            if (b.yPos > TripleTWindow.SCR_HEIGHT) {
                break;
            }
        }
        
        kirby.drawImage(g2);
        
        if (isPaused) {
            g2.drawImage(Images.get("pauseOverlay" + pauseIndex), 0, 0, null);
        }
    }
    
    /**
     * Set them key bindings.
     * The controls involved in Level 2 are RIGHT, LEFT, and PAUSE/SELECT.
     */
    void setKeyBindings() {
        // Input map bindings [pressed]
        InputMap iMap = getInputMap();
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0), RIGHT_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0), LEFT_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.downKey, 0), DOWN_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.upKey, 0), UP_PRESSED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.pauseKey, 0), PAUSELECT);
        iMap.put(KeyStroke.getKeyStroke("ENTER"), ENTER_PRESSED);
        // [Released]
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0, true), RIGHT_RELEASED);
        addToInputMap(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0, true), LEFT_RELEASED);
        
        // Action map bindings [pressed]
        ActionMap aMap = getActionMap();
        aMap.put(RIGHT_PRESSED, new RightPressedAction());
        aMap.put(LEFT_PRESSED, new LeftPressedAction());
        aMap.put(DOWN_PRESSED, new DownPressedAction());
        aMap.put(UP_PRESSED, new UpPressedAction());
        aMap.put(PAUSELECT, new PauselectAction());
        aMap.put(ENTER_PRESSED, new EnterPressedAction());
        // [Released]
        aMap.put(RIGHT_RELEASED, new RightReleasedAction());
        aMap.put(LEFT_RELEASED, new LeftReleasedAction());
    }
    
    //================================================================================
    // Overridden action methods
    //================================================================================
    
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
        }
        
        repaint();
    }
    
    protected void upPressed() {
        if (isPaused) { 
            pauseIndex = 1 - pauseIndex; 
        }
        
        repaint();
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
        kirby.rightReleased();
        repaint();
    }
    
    protected void leftReleased() {
        kirby.leftReleased();
        repaint();
    }
    
    //================================================================================
    // Inner classes
    //================================================================================
    
    /**
     * A block, meant for Kirby to collide with.
     * Used exclusively in Level 2 as an interactive object.
     */
    private class Block {
        int xPos, yPos;
        Image img;
        private boolean isBig;
        boolean accelerated;
        static final int BIG_BLOCKSIZE = 32, SMALL_BLOCKSIZE = 16;
        
        /**
         * Constructor. Sets x- and y- coordinates for the block.
         * Determines randomly whether or not it is a big block or a small block.
         * @param xPos the x-coordinate
         * @param yPos the y-coordinate
         */
        Block(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            
            if (Math.random() >= 0.5) {
                img = Images.get("bigBlock");
            } else {
                img = Images.get("smallBlock");
            }
        }
        
        /**
         * Gets the rectangle associated with the block, to be used for collision detection.
         */
        Rectangle getRectangle() {
            if (isBig) {
                return new Rectangle(xPos, yPos, BIG_BLOCKSIZE, BIG_BLOCKSIZE);
            } else {
                return new Rectangle(xPos, yPos, SMALL_BLOCKSIZE, SMALL_BLOCKSIZE);
            }
        }
    }
}
