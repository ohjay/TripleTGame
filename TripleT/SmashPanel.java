package TripleT;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/** 
 * The panel for the kirbySMASH minigame. That will never be made. :(
 * @author Owen Jow
 */
public class SmashPanel extends EscapableMenuPanel {
    private static final Image SMASH_BACKGROUND = Images.get("smashMessage");
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(SMASH_BACKGROUND, 0, 0, null);
    }
}
