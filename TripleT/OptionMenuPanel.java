package TripleT;

/**
 * A menu for toggling and choosing between game options.
 * The player should be able to change Kirby's in-game color and adjust the music volume.
 * 
 * ^ Or, at least, he/she SHOULD have been able to, before I decided to cease development.
 * @author Owen Jow
 */
public class OptionMenuPanel extends PosterMenuPanel {
    /**
     * Constructor.
     */
    public OptionMenuPanel() {
        backgroundImg = Images.get("optionsMenu");
    }
}
