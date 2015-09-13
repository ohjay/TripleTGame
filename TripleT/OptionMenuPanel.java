package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A menu for toggling and choosing between game options.
 * The player should be able to change Kirby's in-game color, and adjust the music volume.
 * @author Owen Jow
 */
public class OptionMenuPanel extends EscapableMenuPanel {
    private static final Image OPTIONS_MENU_BG = Images.get("optionsMenu");
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(OPTIONS_MENU_BG, 0, 0, null);
    }
}
