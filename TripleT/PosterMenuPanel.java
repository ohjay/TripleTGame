package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JPanel;

/**
 * A "poster" menu panel, named because the user won't be interacting 
 * with any of its elements. Instead, the user will read or look at the 
 * contents of the panel, and then push ANY key to exit.
 * 
 * All subclasses should need to do is assign some value to BACKGROUND_IMG.
 * @author Owen Jow
 */
public class PosterMenuPanel extends JPanel {
    private KeyListener kl;
    protected Image backgroundImg;
    
    public void activate() {
        kl = new KeyListener();
        addKeyListener(kl);
        requestFocus();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImg, 0, 0, null);
    }
    
    public class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent evt) {
            // Note that this represents ANY key press!
            removeKeyListener(kl);
            GameState.layout.show(GameState.contentPanel, "mainMenu");
            GameState.menuPanel.requestFocus();
        }
    }
}
