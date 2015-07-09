package Dodge;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * A class containing any data required by the overall game.
 * While theoretically many game states could exist, for every game
 * ("game": imagine a physical handheld game that could be purchased from a store)
 * there will only be a single GameState which will be used by any panels or components 
 * that require it.
 * @author Owen Jow
 */
public class GameState {
    static PersistentInfo pInfo;
    static JPanel contentPanel;
    static CardLayout layout;
    static MainMenuPanel mainMenuPanel;
    static CreditsPanel creditsPanel;
    static ControlMenuPanel controlMPanel;
    static DodgePanel dodgePanel;
    static DodgePausePanel dodgePausePanel;
    static DodgePostGPanel dodgePostGPanel;
    static DodgePreGPanel dodgePreGPanel;
}
