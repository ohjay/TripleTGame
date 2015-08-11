package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * An abstract blueprint for a level panel, which includes activation and painting methods
 * in addition to a drawForeground method that should be overridden by subclasses.
 * @author Owen Jow
 */
abstract class LevelPanel extends JPanel implements ActionListener {
    protected Timer timer;
    protected KeyAdapter kl;
    protected Image backgroundImg;
    protected boolean isPaused;
    protected int pauseIndex;
    
    /**
     * Reset values for the panel. Override this for individualized reset behavior.
     */
    protected void reset() {
        /* By default this method will do very little */
        isPaused = false;
        pauseIndex = 0;
    }
    
    protected void activate() {
        reset();
        addKeyListener(kl);
        
        // Start the timer that will continually request focus for this panel
        timer = new Timer(5, this);
        timer.start();
        requestFocus();
    }
    
    public void deactivate() {
        removeKeyListener(kl);
        timer.stop();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        /* Nothing to see here: override this for timer behavior! */
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
}
