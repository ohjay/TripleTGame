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
     * Takes in a game state, which is passed along to panels that require it.
     * @param state the current game state
     */
    public static void initializeGUI(GameState state) {
        JFrame window = new JFrame("Kirby: The Tedhaun Treaty");
        window.setSize(512, 412);
        window.setLocation(121, 121);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        
        // Create a base JPanel with a card layout
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        state.contentPanel = contentPanel;
        state.layout = (CardLayout) contentPanel.getLayout();
        
        // Add game panels to the card layout structure and the game state object
        initializeGamePanels(state, contentPanel);
        
        state.menuPanel.activate(); // activate the initial panel
        window.setContentPane(contentPanel);
        window.setVisible(true);
    }
    
    /**
     * Intializes the game panels that the game will use.
     * @param state the GameState, to be filled with panel data
     * @param contentPanel the main, base JPanel to which the others will be added
     */
    private static void initializeGamePanels(GameState state, JPanel contentPanel) {
        // MENU PANELS:
        // Main menu panel
        MainMenuPanel menuPanel = new MainMenuPanel(state);
        state.menuPanel = menuPanel;
        contentPanel.add(menuPanel, "mainMenu");
        // Story menu panel
        StoryMenuPanel storyMPanel = new StoryMenuPanel(state);
        contentPanel.add(storyMPanel, "storyMenu");
        // Minigame menu panel
        MinigameMenuPanel minigameMPanel = new MinigameMenuPanel(state);
        state.minigameMPanel = minigameMPanel;
        contentPanel.add(minigameMPanel, "minigameMenu");
        // Cutscene menu panel
        CutsceneMenuPanel cutsceneMPanel = new CutsceneMenuPanel(state);
        contentPanel.add(cutsceneMPanel, "cutsceneMenu");
        // Control menu panel
        ControlMenuPanel controlMPanel = new ControlMenuPanel(state);
        contentPanel.add(controlMPanel, "controlMenu");
        // Option menu panel
        OptionMenuPanel optionMPanel = new OptionMenuPanel(state);
        contentPanel.add(optionMPanel, "optionMenu");
        // Credits panel
        CreditsPanel creditsPanel = new CreditsPanel(state);
        state.creditsPanel = creditsPanel;
        contentPanel.add(creditsPanel, "credits");
        
        // MINIGAME PANELS:
        // Dodge! panel
        DodgePanel dodgePanel = new DodgePanel(state);
        state.dodgePanel = dodgePanel;
        contentPanel.add(dodgePanel, "dodge");
        // Dodge! pause panel
        DodgePausePanel dodgePausePanel = new DodgePausePanel(state);
        state.dodgePausePanel = dodgePausePanel;
        contentPanel.add(dodgePausePanel, "dodgePause");
        // Dodge! post-game panel
        DodgePostGPanel dodgePostGPanel = new DodgePostGPanel(state);
        state.dodgePostGPanel = dodgePostGPanel;
        contentPanel.add(dodgePostGPanel, "dodgePostG");
        // Dodge! pre-game panel
        DodgePreGPanel dodgePreGPanel = new DodgePreGPanel(state);
        state.dodgePreGPanel = dodgePreGPanel;
        contentPanel.add(dodgePreGPanel, "dodgePreG");
        // kirbySMASH panel
        SmashPanel smashPanel = new SmashPanel(state);
        contentPanel.add(smashPanel, "kirbySMASH");
    }
}
