package TripleT;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A representation of the on-screen window for the game.
 * @author Owen Jow
 */
public class TripleTWindow {
    static final int SCREEN_WIDTH = 512, SCREEN_HEIGHT = 412;
    
    /** 
     * Initializes the GUI for the game.
     */
    public static void initializeGUI() {
        JFrame window = new JFrame("Kirby: The Tedhaun Treaty");
        window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        window.setLocation(121, 121);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        
        // Create a base JPanel with a card layout
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        GameState.contentPanel = contentPanel;
        GameState.layout = (CardLayout) contentPanel.getLayout();
        
        // Add game panels to the card layout structure and the game state
        initializeGamePanels(contentPanel);
        
        GameState.menuPanel.activate(); // activate the initial panel
        window.setContentPane(contentPanel);
        window.setVisible(true);
    }
    
    /**
     * Intializes the game panels that the game will use.
     * @param contentPanel the main, base JPanel to which the others will be added
     */
    private static void initializeGamePanels(JPanel contentPanel) {
        // MENU PANELS:
        // Main menu panel
        GameState.menuPanel = new MainMenuPanel();
        contentPanel.add(GameState.menuPanel, "mainMenu");
        // Story menu panel
        GameState.storyMPanel = new StoryMenuPanel();
        contentPanel.add(GameState.storyMPanel, "storyMenu");
        // Minigame menu panel
        GameState.minigameMPanel = new MinigameMenuPanel();
        contentPanel.add(GameState.minigameMPanel, "minigameMenu");
        // Cutscene menu panel
        GameState.cutsceneMPanel = new CutsceneMenuPanel();
        contentPanel.add(GameState.cutsceneMPanel, "cutsceneMenu");
        // Control menu panel
        GameState.controlMPanel = new ControlMenuPanel();
        contentPanel.add(GameState.controlMPanel, "controlMenu");
        // Option menu panel
        GameState.optionMPanel = new OptionMenuPanel();
        contentPanel.add(GameState.optionMPanel, "optionMenu");
        // Credits panel
        GameState.creditsPanel = new CreditsPanel();
        contentPanel.add(GameState.creditsPanel, "credits");
        
        // MINIGAME PANELS:
        // Dodge! panel
        GameState.dodgePanel = new DodgePanel();
        contentPanel.add(GameState.dodgePanel, "dodge");
        // Dodge! pause panel
        GameState.dodgePausePanel = new DodgePausePanel();
        contentPanel.add(GameState.dodgePausePanel, "dodgePause");
        // Dodge! post-game panel
        GameState.dodgePostGPanel = new DodgePostGPanel();
        contentPanel.add(GameState.dodgePostGPanel, "dodgePostG");
        // Dodge! pre-game panel
        GameState.dodgePreGPanel = new DodgePreGPanel();
        contentPanel.add(GameState.dodgePreGPanel, "dodgePreG");
        // kirbySMASH panel
        GameState.smashPanel = new SmashPanel();
        contentPanel.add(GameState.smashPanel, "kirbySMASH");
        
        // Level panels
        GameState.level1 = new Level1();
        contentPanel.add(GameState.level1, "level1");
    }
}
