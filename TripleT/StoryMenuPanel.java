package TripleT;

import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/** 
 * The menu for the story mode, which includes a choice between each of the three save files.
 * It also allows for the option of deleting save files. Also, within the story menu we will use
 * the inherited "imgIndex" variable to represent the save file that is currently selected.
 * @author Owen Jow
 */
public class StoryMenuPanel extends MenuPanel {
    private static final Image STORY_MENU_SHEET = Images.get("storyMenuSS");
    // Sheet image offset values (SCR = "screen")
    private static final int SCR_WIDTH = 512, SCR_HEIGHT = 412;
    // Save file data coordinates (SF = "save file")
    private static final int SF_X = 65, SF_STR_BASE_Y = 131, SF_LVL_Y_OFFSET = 23, 
            SF_Y_OFFSET = 82, PERCENT_X = 375, PERCENT_BASE_Y = 149;
    // Confirmation tab coordinates
    private static final int L1_X = 166, L1_Y = 174, L2_X_OFFSET = -1, L3_X_OFFSET = -42,
            L23_Y_OFFSET = 25, YES_X = 200, NO_X = 284, YESNO_Y = 267;
    private int sheetOffset; // the offset for the story menu "sprite"sheet
    private static final SaveFileInfo[] saveFiles = new SaveFileInfo[] { GameState.pInfo.saveFile1,
            GameState.pInfo.saveFile2, GameState.pInfo.saveFile3 };
    private boolean yesHighlighted;
    private static final Font regFont = new Font("Optima", Font.BOLD, 19);
    private static final Font percentFont = new Font("Palatino", Font.BOLD, 45);
    
    /**
     * Paints the part of the sheet that should currently be in effect.
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(STORY_MENU_SHEET, 0, 0, SCR_WIDTH, SCR_HEIGHT, sheetOffset, 0, 
                sheetOffset + SCR_WIDTH, SCR_HEIGHT, null);
        
        // Add save file information
        for (int i = 0; i < saveFiles.length; i++) {
            g.setFont(regFont);
            g.setColor((sheetOffset == 0 && imgIndex == i) ? Color.WHITE : Color.BLACK);
            SaveFileInfo sf = saveFiles[i];
            g2.drawString(sf.worldStr, SF_X, SF_STR_BASE_Y + i * SF_Y_OFFSET);
            g2.drawString("Level " + sf.world + "-" + sf.level, SF_X, 
                    SF_STR_BASE_Y + SF_LVL_Y_OFFSET + i * SF_Y_OFFSET);
            g.setFont(percentFont);
            g2.drawString("" + sf.percentage + "%", PERCENT_X, PERCENT_BASE_Y + i * SF_Y_OFFSET);
        }
        
        // If the confirmation popup is open, include text inside of the box
        if (sheetOffset == SCR_WIDTH) {
            g2.setColor(Color.WHITE);
            g.setFont(regFont);
            g2.drawString("Are you sure that you", L1_X, L1_Y);
            g2.drawString("want to delete file #" + (imgIndex + 1) + "?", 
                    L1_X + L2_X_OFFSET, L1_Y + L23_Y_OFFSET);
            g2.drawString("[This action cannot be undone!]", L1_X + L3_X_OFFSET,
                    L1_Y + 2 * L23_Y_OFFSET);
            
            g2.setColor((yesHighlighted) ? Color.WHITE : Color.GRAY);
            g2.drawString("Yes", YES_X, YESNO_Y);
            g2.setColor((yesHighlighted) ? Color.GRAY : Color.WHITE);
            g2.drawString("No", NO_X, YESNO_Y);
        }
    }
    
    public void activate() {
        activate(new KeyListener());
    }
    
    /**
     * The KeyListener for the story menu.
     * Controls registered: UP, DOWN, LEFT, RIGHT, ENTER, DELETE, and ESC.
     */
    public class KeyListener extends MenuPanel.KeyListener {
        @Override
        public void keyPressed(KeyEvent evt) {
            int keyCode = evt.getKeyCode();
            
            if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT) {
                if (sheetOffset == 0) {
                    // The confirmation popup is not currently being displayed
                    imgIndex = (imgIndex == 0) ? 2 : imgIndex - 1;
                } else {
                    yesHighlighted = !yesHighlighted;
                }
            } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_RIGHT) {
                if (sheetOffset == 0) {
                    imgIndex = (imgIndex == 2) ? 0 : imgIndex + 1;
                } else {
                    yesHighlighted = !yesHighlighted;
                }
            } else if (keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_BACK_SPACE) {
                sheetOffset = SCR_WIDTH;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                if (sheetOffset == SCR_WIDTH) {
                    // The confirmation popup is open
                    sheetOffset = 0;
                    if (yesHighlighted) {
                        // the selected save file should be cleared
                        saveFiles[imgIndex + 1].clear();
                    }
                } else {
                    switch (imgIndex) {
                        case 0:
                            // Save file #1
                            // Go to #1's game
                        case 1:
                            // Save file #2
                            // Go to #2's game
                        case 2:
                            // Save file #3
                            // Go to #3's game
                    }
                }
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                // Return to the main menu
                deactivate();
                GameState.layout.show(GameState.contentPanel, "mainMenu");
                GameState.menuPanel.activate();
            }
            
            repaint();
        }
    }
}
