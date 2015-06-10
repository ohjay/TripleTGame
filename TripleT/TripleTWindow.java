package TripleT;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A representation of the on-screen window for the game.
 * @author Owen Jow
 */
public class TripleTWindow {
    
    /** 
     * Initializes the GUI for the game.
     */
    public static void initializeGUI() {
        JFrame window = new JFrame("Kirby: The Tedhaun Treaty");
        window.setSize(512, 412);
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
        MainMenuPanel menuPanel = new MainMenuPanel();
        GameState.menuPanel = menuPanel;
        contentPanel.add(menuPanel, "mainMenu");
        // Story menu panel
        StoryMenuPanel storyMPanel = new StoryMenuPanel();
        contentPanel.add(storyMPanel, "storyMenu");
        // Minigame menu panel
        MinigameMenuPanel minigameMPanel = new MinigameMenuPanel();
        GameState.minigameMPanel = minigameMPanel;
        contentPanel.add(minigameMPanel, "minigameMenu");
        // Cutscene menu panel
        CutsceneMenuPanel cutsceneMPanel = new CutsceneMenuPanel();
        contentPanel.add(cutsceneMPanel, "cutsceneMenu");
        // Control menu panel
        ControlMenuPanel controlMPanel = new ControlMenuPanel();
        contentPanel.add(controlMPanel, "controlMenu");
        // Option menu panel
        OptionMenuPanel optionMPanel = new OptionMenuPanel();
        contentPanel.add(optionMPanel, "optionMenu");
        // Credits panel
        CreditsPanel creditsPanel = new CreditsPanel();
        GameState.creditsPanel = creditsPanel;
        contentPanel.add(creditsPanel, "credits");
        
        // MINIGAME PANELS:
        // Dodge! panel
        DodgePanel dodgePanel = new DodgePanel();
        GameState.dodgePanel = dodgePanel;
        contentPanel.add(dodgePanel, "dodge");
        // Dodge! pause panel
        DodgePausePanel dodgePausePanel = new DodgePausePanel();
        GameState.dodgePausePanel = dodgePausePanel;
        contentPanel.add(dodgePausePanel, "dodgePause");
        // Dodge! post-game panel
        DodgePostGPanel dodgePostGPanel = new DodgePostGPanel();
        GameState.dodgePostGPanel = dodgePostGPanel;
        contentPanel.add(dodgePostGPanel, "dodgePostG");
        // Dodge! pre-game panel
        DodgePreGPanel dodgePreGPanel = new DodgePreGPanel();
        GameState.dodgePreGPanel = dodgePreGPanel;
        contentPanel.add(dodgePreGPanel, "dodgePreG");
        // kirbySMASH panel
        SmashPanel smashPanel = new SmashPanel();
        contentPanel.add(smashPanel, "kirbySMASH");
    }
}
