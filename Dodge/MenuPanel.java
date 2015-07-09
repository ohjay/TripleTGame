package Dodge;

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
    protected int imgIndex;
    protected Timer timer;
    protected KeyAdapter kl;
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
    }
    
    /** 
     * Activates the menu panel. 
     * Starts a timer and adds a key listener so that the panel can respond to things.
     */
    protected void activate() {
        imgIndex = 0; // default the menu choice to the first option
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
}
