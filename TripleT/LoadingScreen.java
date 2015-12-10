package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * A placeholder screen to tide the users over while resources load.
 */
public class LoadingScreen extends JPanel {
    private static final Image BACKGROUND_IMG = Images.get("loadingScr");
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(BACKGROUND_IMG, 0, 0, null);
    }
}
