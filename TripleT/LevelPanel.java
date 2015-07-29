package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

abstract class LevelPanel extends JPanel implements ActionListener {
    protected Timer timer;
    protected KeyAdapter kl;
    protected Image backgroundImg;
    
    protected void activate() {
        addKeyListener(kl);
        
        // Start the timer that will continually request focus for this panel
        timer = new Timer(20, this);
        timer.start();
    }
    
    public void deactivate() {
        removeKeyListener(kl);
        timer.stop();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
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
    }
    
    protected void drawForeground(Graphics2D g2) {
        /* Does nothing for now, but this should be overridden by subclasses 
           that want foregrounds and sprites to be drawn! */
    }
}
