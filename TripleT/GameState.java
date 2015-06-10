package TripleT;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * A class containing any data required by the overall game.
 * While theoretically many game states could exist, for every game
 * (game: imagine a physical handheld game that could be purchased from a store)
 * there will only be one GameState instance which will be passed around
 * to any panels or components that require it.
 * @author Owen Jow
 */
public class GameState {
    PersistentInfo pInfo;
    JPanel contentPanel;
    CardLayout layout;
    MainMenuPanel menuPanel;
    MinigameMenuPanel minigameMPanel;
    DodgePanel dodgePanel;
    DodgePausePanel dodgePausePanel;
    DodgePostGPanel dodgePostGPanel;
    DodgePreGPanel dodgePreGPanel;
    CreditsPanel creditsPanel;
    
    /**
     * Creates a GameState object and initializes a new PersistentInfo.
     */
    public GameState() {
        pInfo = new PersistentInfo();
    }
}
