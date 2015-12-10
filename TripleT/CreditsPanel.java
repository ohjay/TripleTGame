package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.InputMap;

/** 
 * The credits panel.
 * @author Owen Jow
 */
public class CreditsPanel extends JPanel implements ActionListener {
    private Timer timer;
    private static final Image CREDITS_BACKGROUND = Images.get("creditsBackground");
    private static final Image[] CREDITS = new Image[] { Images.get("owenjow"),
            Images.get("williamjow"), Images.get("pirra") }; // (printed in this order)
    // Positional coordinates for the actual credits [note: CR = credits]
    private static final int ODD_FINAL_X = -1, INIT_X = 512, CR_INIT_Y = 130, CR_Y_OFFSET = 66,
            SPEEDUP_DIST = 130;
    private static final int EVEN_FINAL_X = ODD_FINAL_X + 20, HALF_XDIST = INIT_X / 2;
    private int[] xPositions = new int[CREDITS.length];
    private int crYPos;
    private boolean finished;
    private static final String GO_BACK = "go back";
    
    /** 
     * Activates the credits panel.
     * In doing so, resets critical values so that the credits will always begin the same way.
     */
    public void activate() {
        // Make sure credit animations start at the beginning (by initializing x-positions)
        finished = false;
        for (int i = 0; i < CREDITS.length; i++) {
            xPositions[i] = (i % 2 == 0) ? INIT_X : -INIT_X;
        }
        
        timer = new Timer(4, this);
        timer.start();
    }
    
    /**
     * With every tick of the timer, requests focus and animates the credits 
     * if they're not all on the screen already.
     * @param evt an ActionEvent; not used
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
        
        if (!finished) {
            // Update the x-positions in the most convoluted-looking way possible
            for (int i = 0; i < CREDITS.length; i++) {
                if (i % 2 == 0) {
                    if (xPositions[i] > EVEN_FINAL_X 
                            && (i == 0 || overHalfway(xPositions[i - 1], false))) {
                        xPositions[i] -= (shouldSpeedUp(xPositions[i], true)) ? 2 : 1;
                    } else if (i == CREDITS.length && xPositions[i] == EVEN_FINAL_X) {
                        finished = true;
                    }
                } else if (xPositions[i] < ODD_FINAL_X && overHalfway(xPositions[i - 1], true)) {
                    xPositions[i] += (shouldSpeedUp(xPositions[i], false)) ? 2 : 1;
                } else if (i == CREDITS.length && xPositions[i] == ODD_FINAL_X) {
                    finished = true;
                }
            }
            
            repaint();
        }
    }
    
    /**
     * Returns true if the given x-coordinate is more than halfway to its final position.
     * @param x the x-coordinate in question
     * @param isEven specifies whether or not x relates to one of the even-indexed credits
     * @return whether or not x is over halfway to its final position
     */
    private boolean overHalfway(int x, boolean isEven) {
        return (isEven) ? (x < EVEN_FINAL_X + HALF_XDIST) : (x > ODD_FINAL_X - HALF_XDIST);
    }
    
    /**
     * Returns true if the speed for the current animation should increase.
     * @param x the x-coordinate at which the block is currently being drawn
     * @param isEven specifies whether or not x relates to one of the even-indexed credits
     * @return whether or not the animation should speed up
     */
    private boolean shouldSpeedUp(int x, boolean isEven) {
        return (isEven) ? (x < INIT_X - SPEEDUP_DIST) : (x > -INIT_X + SPEEDUP_DIST);
    }
    
    /**
     * Paint the credits background, and then the actual credits over that.
     * @param g the graphics component used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(CREDITS_BACKGROUND, 0, 0, null);
        
        for (int i = 0; i < CREDITS.length; i++) {
            crYPos = CR_INIT_Y + i * CR_Y_OFFSET;
            g2.drawImage(CREDITS[i], xPositions[i], crYPos, null);
        }
    }
    
    void setKeyBindings() {
        // Input maps
        InputMap iMap = getInputMap();
        iMap.put(KeyStroke.getKeyStroke("ENTER"), GO_BACK);
        iMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), GO_BACK);
        iMap.put(KeyStroke.getKeyStroke("DELETE"), GO_BACK);
        iMap.put(KeyStroke.getKeyStroke("ESCAPE"), GO_BACK);
    
        // Action maps
        getActionMap().put(GO_BACK, new EscapeAction());
    }
    
    void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, boolean shouldRemove) {
        InputMap iMap = getInputMap();
        iMap.put(newKey, iMap.get(oldKey));
        if (shouldRemove) { iMap.remove(oldKey); }
    }
    
    void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, String oldAction, 
            boolean shouldRemove) {
        getInputMap().put(newKey, oldAction);
        if (shouldRemove) { getInputMap().remove(oldKey); }
    }
    
    /**
     * Returns to the main menu.
     */
    protected class EscapeAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            timer.stop();
            GameState.layout.show(GameState.contentPanel, "mainMenu");
            GameState.menuPanel.requestFocus();
        }
    }
}
