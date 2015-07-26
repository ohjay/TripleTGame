package TripleT;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/** 
 * The menu for the story mode, which includes a choice between each of the three save files.
 * It also allows for the option of deleting save files.
 * @author Owen Jow
 */
public class StoryMenuPanel extends MenuPanel {
    private static final Image STORY_MENU_SHEET = Images.get("storyMenuSS");
    private static final int SCR_WIDTH = 512, SCR_HEIGHT = 412;
    private int sheet_offset;
    
    /**
     * Paints the part of the sheet that should currently be in effect.
     * @param g the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(STORY_MENU_SHEET, 0, 0, SCR_WIDTH, SCR_HEIGHT, sheet_offset, 0, 
                sheet_offset + SCR_WIDTH, SCR_HEIGHT, null);
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
            
            if (keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_BACK_SPACE) {
                sheet_offset = SCR_WIDTH;
            } else if (keyCode == KeyEvent.VK_ENTER) {
                sheet_offset = 0;
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                // Return to the main menu
                deactivate();
                GameState.layout.show(GameState.contentPanel, "mainMenu");
                GameState.menuPanel.activate();
            }
            
            repaint();
            /* To do: finish keyPressed method */
        }
    }
}
