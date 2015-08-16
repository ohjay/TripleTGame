package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The menu for selecting between cutscenes to view.
 * There will be three cutscenes total: one will play at the beginning of each of the two worlds,
 * and the last will play at the game's conclusion.
 * @author Owen Jow
 */
public class CutsceneMenuPanel extends EscapableMenuPanel {
    private static final Image CUTSCENE_MENU_SHEET = Images.get("cutsceneMenu");
    private int sheetOffset;
    
    /**
     * Paints the part of the sheet that should currently be in effect.
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(CUTSCENE_MENU_SHEET, 0, 0, TripleTWindow.SCR_WIDTH, TripleTWindow.SCR_HEIGHT, 
                sheetOffset, 0, sheetOffset + TripleTWindow.SCR_WIDTH, TripleTWindow.SCR_HEIGHT, null);
    }
}
