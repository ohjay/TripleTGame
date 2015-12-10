package TripleT;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.util.ArrayList;

/**
 * A representation of the on-screen window for the game.
 * @author Owen Jow
 */
public class TripleTWindow {
    static final int SCR_WIDTH = 512, SCR_HEIGHT = 412; // (SCR = "screen")
    
    /** 
     * Initializes the GUI for the game.
     */
    public static void initializeGUI() {
        JFrame window = new JFrame("Kirby: The Tedhaun Treaty");
        window.setSize(SCR_WIDTH, SCR_HEIGHT);
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
        setKeyBindings(); // allow the game to respond to keyboard input
        
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
        GameState.level2 = new Level2();
        contentPanel.add(GameState.level2, "level2");
        
        // Aaand doors for level panels
        GameState.level1.initializeDoors();
    }
    
    /**
     * Sets the key bindings for each panel. Each individual panel 
     * should have its own setKeyBindings method, which will be called here.
     */
    private static void setKeyBindings() {
        // Menu panels
        GameState.menuPanel.setKeyBindings();
        GameState.storyMPanel.setKeyBindings();
        GameState.minigameMPanel.setKeyBindings();
        GameState.creditsPanel.setKeyBindings();
        GameState.dodgePausePanel.setKeyBindings();
        
        // Level panels
        GameState.level1.setKeyBindings();
        GameState.level2.setKeyBindings();
    }
    
    /**
     * Updates panel key bindings for the OLD_KEY, replacing them with NEW_KEY.
     * This change will take place in all panels that used to have a binding for OLD_KEY.
     */
    static void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, boolean shouldRemove) {
        // Menu panels
        GameState.menuPanel.updateKeyBindings(oldKey, newKey, shouldRemove);
        GameState.storyMPanel.updateKeyBindings(oldKey, newKey, shouldRemove);
        GameState.minigameMPanel.updateKeyBindings(oldKey, newKey, shouldRemove);
        GameState.creditsPanel.updateKeyBindings(oldKey, newKey, shouldRemove);
        GameState.dodgePausePanel.updateKeyBindings(oldKey, newKey, shouldRemove);
        
        // Level panels
        GameState.level1.updateKeyBindings(oldKey, newKey, shouldRemove);
        GameState.level2.updateKeyBindings(oldKey, newKey, shouldRemove);
    }
    
    /**
     * The same as the above, except with explicit old actions.
     */
    static void updateKeyBindings(KeyStroke oldKey, KeyStroke newKey, ArrayList<String> oldActions, 
            boolean shouldRemove) {
        // Menu panels
        GameState.menuPanel.updateKeyBindings(oldKey, newKey, oldActions.get(0), shouldRemove);
        GameState.storyMPanel.updateKeyBindings(oldKey, newKey, oldActions.get(1), shouldRemove);
        GameState.minigameMPanel.updateKeyBindings(oldKey, newKey, oldActions.get(2), shouldRemove);
        GameState.creditsPanel.updateKeyBindings(oldKey, newKey, oldActions.get(3), shouldRemove);
        GameState.dodgePausePanel.updateKeyBindings(oldKey, newKey, oldActions.get(4), shouldRemove);
        
        // Level panels
        GameState.level1.updateKeyBindings(oldKey, newKey, oldActions.get(5), shouldRemove);
        GameState.level2.updateKeyBindings(oldKey, newKey, oldActions.get(6), shouldRemove);
    }
    
    /**
     * This method will return a String ArrayList containing the action bindings 
     * for every panel with a key binding for KEY. The order of actions within 
     * the ArrayList is important, because it determines which bindings will end up
     * associated with each panel! (aka don't mess with the order man)
     */
    static ArrayList<String> getActionsForKey(KeyStroke key) {
        ArrayList<String> actions = new ArrayList<String>();
        
        // Add the corresponding action for each panel
        // Menu panels
        actions.add((String) GameState.menuPanel.getInputMap().get(key));
        actions.add((String) GameState.storyMPanel.getInputMap().get(key));
        actions.add((String) GameState.minigameMPanel.getInputMap().get(key));
        actions.add((String) GameState.creditsPanel.getInputMap().get(key));
        actions.add((String) GameState.dodgePausePanel.getInputMap().get(key));
        
        // Level panels
        actions.add((String) GameState.level1.getInputMap().get(key));
        actions.add((String) GameState.level2.getInputMap().get(key));
        
        return actions;
    }
}
