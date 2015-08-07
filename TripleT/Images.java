package TripleT;

import java.util.HashMap;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * A utility class for storing and manipulating images. 
 * Contains all of the game's image data and permits access to any desired image 
 * via that image's unique idenitifier.
 * @author Owen Jow
 */
public class Images {
    private static HashMap<String, Image> images = new HashMap<String, Image>();
    private static boolean initialized;
    static final Image[] numbers = new Image[] { get("zero"), get("one"), get("two"), get("three"),
            get("four"), get("five"), get("six"), get("seven"), get("eight"), get("nine") };
    
    /** 
     * Initializes (adds to the internal data structure) all of the images.
     */
    private static void initialize() {
        // Menu images
        initializeMenuImages();
        initializeSubmenuImages();
        initializeCreditsImages();
        
        // Dodge! minigame images
        initializeDodgeImages();
        initializeTedhaunImages();
        initializeStarKirbyImages();
        initializeDodgePauseImages();
        
        // Sheet and background/foreground images
        initializeSheets();
        initializeGrounds();
        
        // All images have been initalized now!
        initialized = true;
    }
    
    /** 
     * Initializes (adds to the internal data structure) images pertaining to the main menu.
     * The images share the same background, with the only difference
     * being the menu option that is currently highlighted:
     * - storyH: has the "story" option highlighted
     * - minigamesH: has the "minigames" option highlighted
     * - cutscenesH: has the "cutscenes" option highlighted
     * - controlsH: has the "controls" option highlighted
     * - optionsH: has the "options" option highlighted
     * - creditsH: has the "credits" option highlighted
     * - titleScr: the title screen image
     */
    private static void initializeMenuImages() {
        images.put("storyH", 
                new ImageIcon(Images.class.getResource("/images/menu1.png")).getImage());
        images.put("minigamesH",
                new ImageIcon(Images.class.getResource("/images/menu2.png")).getImage());
        images.put("cutscenesH",
                new ImageIcon(Images.class.getResource("/images/menu3.png")).getImage());
        images.put("controlsH",
                new ImageIcon(Images.class.getResource("/images/menu4.png")).getImage());
        images.put("optionsH",
                new ImageIcon(Images.class.getResource("/images/menu5.png")).getImage());
        images.put("creditsH",
                new ImageIcon(Images.class.getResource("/images/menu6.png")).getImage()); 
        images.put("titleScr",
                new ImageIcon(Images.class.getResource("/images/tripleT_titlesc.png")).getImage());
    }
    
    /** 
     * Initializes (adds to the internal data structure) submenu images.
     * Like the images for the main menu, multiple images for the same menu 
     * differ only in which option is highlighted:
     * - dodgeH: has the Dodge! minigame highlighted [minigame menu]
     * - kirbySMASHH: has the Kirby SMASH minigame highlighted [minigame menu]
     * - controlsMenu: the background for the controls menu
     */
    private static void initializeSubmenuImages() {
        images.put("dodgeH",
                new ImageIcon(Images.class.getResource("/images/minimen1.png")).getImage());
        images.put("kirbySMASHH",
                new ImageIcon(Images.class.getResource("/images/minimen2.png")).getImage());
        images.put("controlsMenu",
                new ImageIcon(Images.class.getResource("/images/controlsMenu.png")).getImage());
    }
    
    /**
     * Initializes images related to the Dodge! minigame.
     * This collection includes:
     * - dodgeBackground: the background for the Dodge! minigame
     * - treasure: the graphic for the in-game treasure chest
     * - lifeCount: a Kirby face intended to be used as a life count indicator
     * - zero through nine: the numbers zero through nine
     * - dodgePostGInitial: the initial post-game screen
     * - dodgePostGRestart: post-game screen with "restart" highlighted
     * - dodgePostGRagequit: post-game screen with "ragequit" highlighted
     * - dodgePostGCRestart: post-game congratulatory screen with "restart" highlighted
     * - dodgePostGCRagequit: post-game congratulatory screen with "ragequit" highlighted
     * - dodgePreG: pre-game screen
     */
    private static void initializeDodgeImages() {
        images.put("dodgeBackground", 
                new ImageIcon(Images.class.getResource("/images/dodgeBG.png")).getImage());
        images.put("treasure",
                new ImageIcon(Images.class.getResource("/images/treasure1.png")).getImage());
        images.put("lifeCount",
                new ImageIcon(Images.class.getResource("/images/lifeCount.png")).getImage());
        images.put("zero", 
                new ImageIcon(Images.class.getResource("/images/zero.png")).getImage());
        images.put("one",
                new ImageIcon(Images.class.getResource("/images/one.png")).getImage());
        images.put("two",
                new ImageIcon(Images.class.getResource("/images/two.png")).getImage());
        images.put("three",
                new ImageIcon(Images.class.getResource("/images/three.png")).getImage());
        images.put("four",
                new ImageIcon(Images.class.getResource("/images/four.png")).getImage());
        images.put("five",
                new ImageIcon(Images.class.getResource("/images/five.png")).getImage());
        images.put("six",
                new ImageIcon(Images.class.getResource("/images/six.png")).getImage());
        images.put("seven",
                new ImageIcon(Images.class.getResource("/images/seven.png")).getImage());
        images.put("eight",
                new ImageIcon(Images.class.getResource("/images/eight.png")).getImage());
        images.put("nine",
                new ImageIcon(Images.class.getResource("/images/nine.png")).getImage());
        images.put("dodgePostGInitial",
                new ImageIcon(Images.class.getResource("/images/dodgePostGInitial.png")).getImage());
        images.put("dodgePostGRestart",
                new ImageIcon(Images.class.getResource("/images/dodgePostGRestart.png")).getImage());
        images.put("dodgePostGRagequit",
                new ImageIcon(Images.class.getResource("/images/dodgePostGRagequit.png")).getImage());
        images.put("dodgePostGCRestart",
                new ImageIcon(Images.class.getResource("/images/dodgePostGCRestart.png")).getImage());
        images.put("dodgePostGCRagequit",
                new ImageIcon(Images.class.getResource("/images/dodgePostGCRagequit.png")).getImage());
        images.put("dodgePreG",
                new ImageIcon(Images.class.getResource("/images/dodgePreG.png")).getImage());
    }
    
    /**
     * Initializes Tedhaun images.
     * This collection includes:
     * - rightTedhaun: a Tedhaun facing right
     * - leftTedhaun: a Tedhaun facing left
     */
    private static void initializeTedhaunImages() {
        images.put("rightTedhaun",
                new ImageIcon(Images.class.getResource("/images/ghost1.png")).getImage());
        images.put("leftTedhaun", 
                new ImageIcon(Images.class.getResource("/images/ghost-1.png")).getImage());
    }
    
    /** 
     * Initializes Star Kirby images (Star Kirby being the protagonist of the Dodge! minigame).
     * This collection includes:
     * - rightStarKirby: Kirby on a star, facing right
     * - leftStarKirby: Kirby on a star, facing left
     * - rightStarTled: Kirby on a star, facing right, startled
     * - leftStarTled: Kirby on a star, facing left, startled
     * - orangeStarLeft: Kirby on a star, facing left, orange
     * - orangeStarRight: Kirby on a star, facing right, orange
     * - redStarLeft: Kirby on a star, facing left, red
     * - redStarRight: Kirby on a star, facing right, red
     */
    private static void initializeStarKirbyImages() {
        images.put("rightStarKirby",
                new ImageIcon(Images.class.getResource("/images/kirbyStar1.png")).getImage());
        images.put("leftStarKirby",
                new ImageIcon(Images.class.getResource("/images/kirbyStar-1.png")).getImage());
        images.put("rightStarTled",
                new ImageIcon(Images.class.getResource("/images/kirbyStarHit.png")).getImage());
        images.put("leftStarTled",
                new ImageIcon(Images.class.getResource("/images/kirbyStarHit2.png")).getImage());
        images.put("orangeStarLeft",
                new ImageIcon(Images.class.getResource("/images/kirbyOrangeStar-1.png")).getImage());
        images.put("orangeStarRight",
                new ImageIcon(Images.class.getResource("/images/kirbyOrangeStar1.png")).getImage());
        images.put("redStarLeft",
                new ImageIcon(Images.class.getResource("/images/kirbyRedStar-1.png")).getImage());
        images.put("redStarRight",
                new ImageIcon(Images.class.getResource("/images/kirbyRedStar1.png")).getImage());
    }
    
    /**
     * Initializes Dodge pause menu images.
     * - dodgePause1: has the "continue" option selected
     * - dodgePause2: has the "ragequit" option selected
     */
    private static void initializeDodgePauseImages() {
        images.put("dodgePause1",
                new ImageIcon(Images.class.getResource("/images/dodgePause1.png")).getImage());
        images.put("dodgePause2",
                new ImageIcon(Images.class.getResource("/images/dodgePause2.png")).getImage());
    }
    
    /**
     * Initializes images for the credits menu.
     * - credits: the credits screen background
     * - owenjow: the credits entry for Owen Jow
     * - williamjow: the credits entry for William Jow
     */
    private static void initializeCreditsImages() {
        images.put("creditsBackground",
                new ImageIcon(Images.class.getResource("/images/credits.png")).getImage());
        images.put("owenjow",
                new ImageIcon(Images.class.getResource("/images/owenjow.png")).getImage());
        images.put("williamjow",
                new ImageIcon(Images.class.getResource("/images/williamjow.png")).getImage());
    }
    
    /**
     * Initializes sheets for in-game characters and objects.
     * - kirbySS: the sheet for a basic Kirby (although let's be honest, Kirby is never basic)
     * - kirbySS-1: the sheet for left-facing Kirby
     * - storyMenuSS: the "sheet" (as in 2 images) for the story menu
     */
    private static void initializeSheets() {
        images.put("kirbySS",
                new ImageIcon(Images.class.getResource("/images/kirby_spritesheet.png")).getImage());
        images.put("kirbySS-1",
                new ImageIcon(Images.class.getResource("/images/kirby_spritesheet-1.png")).getImage());
        images.put("storyMenuSS",
                new ImageIcon(Images.class.getResource("/images/storyMenu.png")).getImage());
    }
    
    /**
     * Initializes images for backgrounds and foregrounds.
     * Listing:
     * Foregrounds:
     * - demoForeground
     * Backgrounds:
     * - level1Background
     */
    private static void initializeGrounds() {
        images.put("demoForeground",
                new ImageIcon(Images.class.getResource("/images/demoForeground.png")).getImage());
        images.put("level1Background",
                new ImageIcon(Images.class.getResource("/images/level1Background.png")).getImage());
    }
    
    /**
     * Returns the image mapped to by IMG_ID.
     * @param imgID an identifier for the requested image
     * @return an Image represented by the given identifier
     */
    public static Image get(String imgID) {
        if (!initialized) {
            initialize();
        }
        
        return images.get(imgID);
    }
}
