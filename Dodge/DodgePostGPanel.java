package Dodge;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.Timer;

/** 
 * The post-game screen for Dodge!.
 * @author Owen Jow
 */
public class DodgePostGPanel extends MenuPanel {
    private int score, scoreCounter;
    private boolean listenerActivated;
    private static final int SCORE_X = 165, SCORE_Y = 124, HSCORE_X = 234, HSCORE_Y = 157, 
            NUM_WIDTH = 18;
    private LinkedList<Integer> numStack = new LinkedList<Integer>();
    
    public DodgePostGPanel() {
        // Initialize the post-game background images
        menuImages = new Image[] { Images.get("dodgePostGInitial"), 
                Images.get("dodgePostGRestart"), Images.get("dodgePostGRagequit"),
                Images.get("dodgePostGCRestart"), Images.get("dodgePostGCRagequit") };
        this.kl = new KeyListener();
    }
    
    /**
     * Resets the panel and also updates the score variable with whatever score was just achieved.
     * @param the newest score
     */
    public void resetWithScore(int newScore) {
        score = newScore; 
        scoreCounter = 0; // reset the score counter to 0
        imgIndex = 0;
        listenerActivated = false;
    }
    
    /**
     * Override the activation method because we don't want to add a key listener upon activation.
     * Instead, we need to wait for the score to finish incrementing.
     */
    @Override
    public void activate() {
        timer = new Timer(1, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
        
        if (scoreCounter < score) {
            if (score <= 5000 || scoreCounter > score - 15) scoreCounter += 1;
            else if (score > 25000) scoreCounter += 15;
            else if (score > 10000) scoreCounter += 10;
            else scoreCounter += 5;
            repaint();
        } else if (!listenerActivated) {
            if (score > GameState.pInfo.dodgeHighScore) {
                GameState.pInfo.dodgeHighScore = score;
                Dodge.savePersistentInfo(GameState.pInfo);
                imgIndex = 3;
            } else {
                imgIndex = 1;
            }
            
            // The score display has finished updating, so the screen should be responsive to input
            this.kl = new KeyListener();
            addKeyListener(kl);
            listenerActivated = true;
            repaint();
        }
    }
    
    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw high score
        drawNumber((Graphics2D) g, GameState.pInfo.dodgeHighScore, HSCORE_X, HSCORE_Y);
        // Draw score number
        drawNumber((Graphics2D) g, scoreCounter, SCORE_X, SCORE_Y);
    }
    
    /**
     * Draws a number at a position given by the specified coordinates.
     * @param g2 a Graphics2D object to be used for painting
     * @param num the number to be drawn
     * @param x the x-coordinate at which to draw the number
     * @param y the y-coordinate at which to draw the number
     */
    private void drawNumber(Graphics2D g2, int num, int x, int y) {
        // Make sure something is drawn if score is 0
        if (num == 0) {
            g2.drawImage(Images.numbers[0], x, y, null);
        }
        
        while (num > 0) {
            numStack.push(num % 10); // add the digits to a stack data structure
            num /= 10;
        }
        
        // Draw the number's digits
        for (int i = 0; !numStack.isEmpty(); i++) {
            g2.drawImage(Images.numbers[numStack.pop()], x + i * NUM_WIDTH, y, null);
        }
    }
    
    /**
     * The KeyListener for the main menu.
     * Controls registered: UP, DOWN, LEFT, RIGHT, and ENTER.
     */
    public class KeyListener extends KeyAdapter {
        
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            
            if (keyCode == GameState.pInfo.upKey || keyCode == GameState.pInfo.downKey) {
                imgIndex = (imgIndex % 2 == 0) ? imgIndex - 1 : imgIndex + 1;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                deactivate();
                if (imgIndex % 2 == 0) {
                    GameState.layout.show(GameState.contentPanel, "mainMenu");
                    GameState.mainMenuPanel.activate();
                } else {
                    GameState.layout.show(GameState.contentPanel, "dodge");
                    GameState.dodgePanel.reset();
                    GameState.dodgePanel.activate();
                }
            }
            
            repaint();
        }
    }
}
