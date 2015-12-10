package TripleT;

/**
 * The menu for selecting between cutscenes to view.
 * There will be three cutscenes total: one will play at the beginning of each of the two worlds,
 * and the last will play at the game's conclusion.
 * @author Owen Jow
 */
public class CutsceneMenuPanel extends PosterMenuPanel {
    /**
     * Just your standard image-assigning constructor.
     */
    public CutsceneMenuPanel() {
        backgroundImg = Images.get("cutsceneMenu");
    }
}
