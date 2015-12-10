package TripleT;

import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.io.IOException;

/** 
 * The menu for the story mode, which includes a choice between each of the three save files.
 * It also allows for the option of deleting save files. Incidentally, within the story menu we will use
 * the inherited "imgIndex" variable to represent the save file that is currently selected.
 * @author Owen Jow
 */
public class StoryMenuPanel extends EscapableMenuPanel {
    private static final Image STORY_MENU_SHEET = Images.get("storyMenuSS");
    // Save file data coordinates (SF = "save file")
    private static final int SF_X = 65, SF_STR_BASE_Y = 131, SF_LVL_Y_OFFSET = 23, 
            SF_Y_OFFSET = 82, PERCENT_X = 370, PERCENT_BASE_Y = 149;
    // Confirmation tab coordinates
    private static final int L1_X = 166, L1_Y = 174, L2_X_OFFSET = -1, L3_X_OFFSET = -42,
            L23_Y_OFFSET = 25, YES_X = 200, NO_X = 284, YESNO_Y = 267;
    private int sheetOffset; // the offset for the story menu "sprite"sheet
    private static SaveFileInfo[] saveFiles;
    private boolean yesHighlighted;
    private static Font regFont;
    private static Font percentFont;
    
    // Action names
    private static final String DELETE = "delete";
    
    /**
     * Performs setup that would otherwise delay screen loading.
     */
    public void initialize() {
        saveFiles = new SaveFileInfo[] { GameState.pInfo.saveFile1,
                GameState.pInfo.saveFile2, GameState.pInfo.saveFile3 };
        regFont = new Font("Optima", Font.BOLD, 19);
        percentFont = new Font("Palatino", Font.BOLD, 45);
    }
    
    /**
     * Paints the part of the sheet that should currently be in effect.
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(STORY_MENU_SHEET, 0, 0, TripleTWindow.SCR_WIDTH, TripleTWindow.SCR_HEIGHT, 
                sheetOffset, 0, sheetOffset + TripleTWindow.SCR_WIDTH, TripleTWindow.SCR_HEIGHT, null);
        
        // Make sure that the text is antialiased
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        
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
        if (sheetOffset == TripleTWindow.SCR_WIDTH) {
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
    
    @Override
    void setKeyBindings() {
        super.setKeyBindings();
        getInputMap().put(KeyStroke.getKeyStroke("DELETE"), DELETE);
        getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), DELETE);
        getActionMap().put(DELETE, new DeleteAction());
    }
    
    @Override
    protected void switchOptions(int direction) {
        if (direction == -1) {
            if (sheetOffset == 0) {
                // The confirmation popup is not currently being displayed
                imgIndex = (imgIndex == 0) ? 2 : imgIndex - 1;
            } else {
                yesHighlighted = !yesHighlighted;
            }
        } else {
            if (sheetOffset == 0) {
                imgIndex = (imgIndex == 2) ? 0 : imgIndex + 1;
            } else {
                yesHighlighted = !yesHighlighted;
            }
        }
        
        repaint();
    }
    
    /**
     * Loads the game at the save file's latest unlocked level.
     */
    private void loadAtCheckpoint(SaveFileInfo file) {
        switch (file.world) {
            case 1:
                switch (file.level) {
                    case 1:
                        GameState.layout.show(GameState.contentPanel, "level1");
                        GameState.level1.activate(file);
                        break;
                    case 2:
                        GameState.layout.show(GameState.contentPanel, "level2");
                        GameState.level2.activate(file);
                        break;
                    case 3:
                        GameState.layout.show(GameState.contentPanel, "congratulations");
                        GameState.congratulatoryPanel.activate();
                        break;
                }
                
                break;
        }
    }
    
    @Override
    protected void confirm() {
        if (sheetOffset == TripleTWindow.SCR_WIDTH) {
            // The confirmation popup is open
            sheetOffset = 0;
            if (yesHighlighted) {
                // the selected save file should be cleared
                saveFiles[imgIndex].clear();
                TripleT.savePersistentInfo(GameState.pInfo);
            }
        } else {
            switch (imgIndex) {
                case 0:
                    // Save file #1
                    // Go to #1's game (this should take the player directly to #1's progress map)
                    loadAtCheckpoint(saveFiles[0]);
                    break;
                case 1:
                    // Save file #2
                    // Go to #2's game (this should take the player directly to #2's progress map)
                    loadAtCheckpoint(saveFiles[1]);
                    break;
                case 2:
                    // Save file #3
                    // Go to #3's game (this should take the player directly to #3's progress map)
                    loadAtCheckpoint(saveFiles[2]);
                    break;
            }
        }
        
        repaint();
    }
    
    private void delete() {
        sheetOffset = TripleTWindow.SCR_WIDTH;
        repaint();
    }
    
    private class DeleteAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent evt) {
            delete();
        }
    }
}
