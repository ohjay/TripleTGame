package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/** 
 * An abstract representation of a menu panel.
 * Includes methods for activation and deactivation, along with the ability 
 * to switch between options (which will tend to be images).
 * @author Owen Jow
 */
public abstract class MenuPanel extends JPanel implements ActionListener {
    protected Image[] menuImages;
    protected int imgIndex = 0;
    protected Timer timer;
    protected KeyListener kl;
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
    }
    
    /** 
     * Activates the menu panel.
     * Enables a key listener so that the menu can respond to keyboard inputs.
     * Since every panel will have a different key listener, this method 
     * should be called by each panel with that panel's individual key listener.
     * [Note: PKL is supposed to stand for "panel key listener"]
     * @param kl the key listener with which to activate this panel
     */
    protected <PKL extends KeyListener> void activate(PKL kl) {
        this.kl = kl;
        addKeyListener(kl);
        
        // Start the timer that will continually request focus for this panel
        timer = new Timer(1, this);
        timer.start();
    }
    
    /**
     * Deactivates the menu panel.
     * The key listener will be removed from the registry, and the timer will cease to run.
     */
    public void deactivate() {
        removeKeyListener(kl);
        timer.stop();
    }
    
    /**
     * Paints the menu's background image.
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuImages[imgIndex], 0, 0, null);
    }
    
    /** 
     * Increments the images index.
     * After this operation, the next image displayed will be the one 
     * following the current image in the internal images array. 
     */
    protected void incrementIndex() {
        if (imgIndex < menuImages.length - 1) {
            imgIndex++;
        } else {
            imgIndex = 0;
        }
    }
    
    /** 
     * Decrements the images index.
     * After this operation, the next image displayed will be the one
     * preceding the current image in the internal images array.
     */
    protected void decrementIndex() {
        if (imgIndex > 0) {
            imgIndex--;
        } else {
            imgIndex = menuImages.length - 1;
        }
    }
    
    /**
     * An empty class that should always be extended if keyboard controllers are desired.
     */
    public class KeyListener extends KeyAdapter {
        /* Doesn't respond to anything right now */
    }
}
