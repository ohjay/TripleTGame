package Dodge;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A representation of the on-screen window for the game.
 * @author Owen Jow
 */
public class DodgeWindow {
    
    /** 
     * Initializes the GUI for the game.
     */
    public static void initializeGUI() {
        JFrame window = new JFrame(":::  D O D G E  :::");
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
        
        GameState.mainMenuPanel.activate(); // activate the initial panel
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
        GameState.mainMenuPanel = new MainMenuPanel();
        contentPanel.add(GameState.mainMenuPanel, "mainMenu");
        // Control menu panel
        GameState.controlMPanel = new ControlMenuPanel();
        contentPanel.add(GameState.controlMPanel, "controlMenu");
        // Credits panel=
        GameState.creditsPanel = new CreditsPanel();
        contentPanel.add(GameState.creditsPanel, "credits");
        
        // GAME PANELS:
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
    }
}
