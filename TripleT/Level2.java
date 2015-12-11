package TripleT;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.InputMap;
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
    private int yTraveled, timeRemaining, blockDamage, postGameTimer;
    private LinkedList<Block> blocks = new LinkedList<Block>();
    
    // Constants
    private static final int NUM_BLOCKS = 145, XRANGE = 400, Y_DIST = 105, 
            INITIAL_IMG_OFFSET = 14500, KIRBY_INITIAL_Y = 142, DAMAGE_GOAL = 180,
            POST_GAME_HANGTIME = 350, FONT_SIZE_SM = 14, FONT_SIZE_BIG = 21,
            BASE_POSTG_Y = 195;
    
    // Constants that are also text/meter coordinates. 
    // (There are actually additional coordinates in paintComponent.)
    private static final int CLOGMETER_X = 318, CLOGMETER_Y = 25;
    
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
        blockDamage = 0;
        postGameTimer = 0;
        timeRemaining = INITIAL_IMG_OFFSET;
        
        // Clear and initialize our collection of blocks
        blocks.clear();
        for (int i = 0; i < NUM_BLOCKS; i++) {
            blocks.add(new Block((int) (Math.random() * XRANGE), i * Y_DIST));
        }
    }

    /**
     * Specifies whether all action should have ceased.
     * Like the development of this game.
     */
    private boolean isGameOver() {
        return (blockDamage >= DAMAGE_GOAL) || (timeRemaining <= 0);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!isPaused) {
            if (blockDamage >= DAMAGE_GOAL) {
                // You win! ...the whole game, now that I've ceased development!
                postGameTimer += 1;
                if (postGameTimer >= POST_GAME_HANGTIME) {
                    // Transition to the congratulatory screen
                    deactivate();
                    GameState.layout.show(GameState.contentPanel, "congratulations");
                    GameState.congratulatoryPanel.activate();
                    
                    // Save the player into the hall of fame (100% hooray)
                    file.incrementLevel();
                    file.updatePercentage();
                    TripleT.savePersistentInfo(GameState.pInfo);
                }
            } else if (timeRemaining <= 0) {
                // You lose! Dang, you suck even more than that vacuum. ;)
                kirby.flyUpward();
                if (kirby.getY() < -kirby.spriteHeight) {
                    postGameTimer += 1;
                    if (postGameTimer >= POST_GAME_HANGTIME) {
                        // Transition to the game over screen
                        deactivate();
                        GameState.layout.show(GameState.contentPanel, "gameOver");
                        GameState.gameOverPanel.activate(file);
                    }
                }
            } else {
                yTraveled += 2;
                timeRemaining -= 2;
                kirby.move();
            
                if (kirby.getFrame() == kirby.getAnimation().getLength()) {
                    kirby.setCurrentFrame(0);
                } else if (kirby.getFrame() > 0) {
                    kirby.updateFrame();
                }
                
                // Iterate through the blocks
                Iterator<Block> blockIter = blocks.iterator();
                while (blockIter.hasNext()) {
                   Block b = blockIter.next();
                   if (b.getRectangle().intersects(kirby.getRectangle())) {
                       b.accelerated = true;
                       kirby.setCurrentFrame(1);
                   }
           
                   if (b.accelerated) {
                       b.yPos -= 3;
                       if (b.yPos < -Block.BIG_BLOCKSIZE) {
                           // This one was hit by Kirby, so it does block damage
                           blockDamage += (b.isBig) ? 5 : 2;
                       }
                   } else {
                       b.yPos -= 1;
                   }
       
                   if (b.yPos < -Block.BIG_BLOCKSIZE) {
                       blockIter.remove();
                   }
                }
            }
            
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImg, 0, -INITIAL_IMG_OFFSET + yTraveled, null);
        
        for (Block b : blocks) {
            g2.drawImage(b.img, b.xPos, b.yPos, null);
            if (b.yPos > TripleTWindow.SCR_HEIGHT) {
                break;
            }
        }
        
        kirby.drawImage(g2);
        
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        
        // Draw the objective + "clog meter" text
        g.setFont(new Font("Verdana", Font.BOLD, FONT_SIZE_SM));
        g.setColor(Color.WHITE);
        g2.drawString("Objective:", 5, 21); // (x, y) coordinates
        g2.drawString("Hit blocks upward", 5, 40);
        g2.drawString("and clog the vacuum!", 5, 54);
        g2.drawString("ClogMeter: ", CLOGMETER_X + 1, 21);
        g2.drawString("Time Remaining: " + timeRemaining, CLOGMETER_X + 1, 56);
        g.setColor(Color.BLACK);
        g2.drawString("Objective: ", 4, 20);
        g2.drawString("Hit blocks upward", 4, 39);
        g2.drawString("and clog the vacuum!", 4, 53);
        g2.drawString("ClogMeter: ", CLOGMETER_X, 20);
        g2.drawString("Time Remaining: " + timeRemaining, CLOGMETER_X, 55);
        
        // The clog meter itself
        g.setColor(Color.WHITE);
        g2.fill3DRect(CLOGMETER_X, CLOGMETER_Y, DAMAGE_GOAL + 2, 15, true);
        g.setColor(Color.BLACK);
        g2.fill3DRect(CLOGMETER_X + 1, CLOGMETER_Y + 1, DAMAGE_GOAL, 13, true);
        g.setColor(Color.GREEN);
        
        if (blockDamage < DAMAGE_GOAL) {
            g2.fill3DRect(CLOGMETER_X + 1, CLOGMETER_Y + 1, blockDamage, 13, true);
        } else {
            g2.fill3DRect(CLOGMETER_X + 1, CLOGMETER_Y + 1, DAMAGE_GOAL, 13, true);
        }
        
        // Post-game text display
        if (postGameTimer > 0) {
            if (blockDamage >= DAMAGE_GOAL) {
                g.setFont(new Font("Verdana", Font.BOLD, FONT_SIZE_BIG));
                g.setColor(Color.WHITE);
                g2.drawString("You did it!", 151, BASE_POSTG_Y + 1);
                g2.drawString("That vacuum isn't getting YOU...", 51, BASE_POSTG_Y + 25);
                g.setColor(Color.BLACK);
                g2.drawString("You did it!", 150, BASE_POSTG_Y);
                g2.drawString("That vacuum isn't getting YOU...", 50, BASE_POSTG_Y + 24);
            } else {
                g.setFont(new Font("Verdana", Font.BOLD, 21));
                g.setColor(Color.WHITE);
                g2.drawString("The vacuum got you...", 129, BASE_POSTG_Y + 1);
                g.setColor(Color.BLACK);
                g2.drawString("The vacuum got you...", 128, BASE_POSTG_Y);
            }
        }
        
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
    
    @Override
    void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, boolean shouldRemove) {
        InputMap iMap = getInputMap();
        if (RIGHT_PRESSED.equals(iMap.get(oldKey))) {
            updateReleasedKeyBindings(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0, true), 
                    RIGHT_RELEASED);
        } else if (LEFT_PRESSED.equals(iMap.get(oldKey))) {
            updateReleasedKeyBindings(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0, true), 
                    LEFT_RELEASED);
        }
        
        iMap.put(newKey, iMap.get(oldKey));
        if (shouldRemove) { iMap.remove(oldKey); }
    }
    
    @Override
    void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, String oldAction, boolean shouldRemove) {
        InputMap iMap = getInputMap();
        if (RIGHT_PRESSED.equals(oldAction)) {
            updateReleasedKeyBindings(iMap, KeyStroke.getKeyStroke(GameState.pInfo.rightKey, 0, true), 
                    RIGHT_RELEASED);
        } else if (LEFT_PRESSED.equals(oldAction)) {
            updateReleasedKeyBindings(iMap, KeyStroke.getKeyStroke(GameState.pInfo.leftKey, 0, true), 
                    LEFT_RELEASED);
        }
        
        iMap.put(newKey, oldAction);
        if (shouldRemove) { iMap.remove(oldKey); }
    }
    
    //================================================================================
    // Overridden action methods
    //================================================================================
    
    protected void rightPressed() {
        if (!isPaused && !isGameOver()) {
            kirby.rightPressed();
            repaint();
        }
    }
    
    protected void leftPressed() {
        if (!isPaused && !isGameOver()) {
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
        if (!isGameOver()) {
            kirby.rightReleased();
            repaint();
        }
    }
    
    protected void leftReleased() {
        if (!isGameOver()) {
            kirby.leftReleased();
            repaint();
        }
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
        boolean isBig;
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
                isBig = true;
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
