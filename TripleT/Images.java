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
     * Adds the image specified by IMAGE_PATH to the internal data structure 
     * under the name LABEL.
     */
    private static void addToImages(String label, String imagePath) {
        images.put(label, new ImageIcon(Images.class.getResource(imagePath)).getImage());
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
        addToImages("storyH", "/images/menu1.png");
        addToImages("minigamesH", "/images/menu2.png");
        addToImages("cutscenesH", "/images/menu3.png");
        addToImages("controlsH", "/images/menu4.png");
        addToImages("optionsH", "/images/menu5.png");
        addToImages("creditsH", "/images/menu6.png");
        addToImages("titleScr", "/images/tripleT_titlesc.png");
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
        addToImages("dodgeH", "/images/minimen1.png");
        addToImages("kirbySMASHH", "/images/minimen2.png");
        addToImages("controlsMenu", "/images/controlsMenu.png");
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
        addToImages("dodgeBackground", "/images/dodgeBG.png");
        addToImages("treasure", "/images/treasure1.png");
        addToImages("lifeCount", "/images/lifeCount.png");
        addToImages("zero", "/images/zero.png");
        addToImages("one", "/images/one.png");
        addToImages("two", "/images/two.png");
        addToImages("three", "/images/three.png");
        addToImages("four", "/images/four.png");
        addToImages("five", "/images/five.png");
        addToImages("six", "/images/six.png");
        addToImages("seven", "/images/seven.png");
        addToImages("eight", "/images/eight.png");
        addToImages("nine", "/images/nine.png");
        addToImages("dodgePostGInitial", "/images/dodgePostGInitial.png");
        addToImages("dodgePostGRestart", "/images/dodgePostGRestart.png");
        addToImages("dodgePostGRagequit", "/images/dodgePostGRagequit.png");
        addToImages("dodgePostGCRestart", "/images/dodgePostGCRestart.png");
        addToImages("dodgePostGCRagequit", "/images/dodgePostGCRagequit.png");
        addToImages("dodgePreG", "/images/dodgePreG.png");
    }
    
    /**
     * Initializes Tedhaun images.
     * This collection includes:
     * - rightTedhaun: a Tedhaun facing right
     * - leftTedhaun: a Tedhaun facing left
     */
    private static void initializeTedhaunImages() {
        addToImages("rightTedhaun", "/images/ghost1.png");
        addToImages("leftTedhaun", "/images/ghost-1.png");
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
        addToImages("rightStarKirby", "/images/kirbyStar1.png");
        addToImages("leftStarKirby", "/images/kirbyStar-1.png");
        addToImages("rightStarTled", "/images/kirbyStarHit.png");
        addToImages("leftStarTled", "/images/kirbyStarHit2.png");
        addToImages("orangeStarLeft", "/images/kirbyOrangeStar-1.png");
        addToImages("orangeStarRight", "/images/kirbyOrangeStar1.png");
        addToImages("redStarLeft", "/images/kirbyRedStar-1.png");
        addToImages("redStarRight", "/images/kirbyRedStar1.png");
    }
    
    /**
     * Initializes Dodge pause menu images.
     * - dodgePause1: has the "continue" option selected
     * - dodgePause2: has the "ragequit" option selected
     */
    private static void initializeDodgePauseImages() {
        addToImages("dodgePause1", "/images/dodgePause1.png");
        addToImages("dodgePause2", "/images/dodgePause2.png");
    }
    
    /**
     * Initializes images for the credits menu.
     * - credits: the credits screen background
     * - owenjow: the credits entry for Owen Jow
     * - williamjow: the credits entry for William Jow
     */
    private static void initializeCreditsImages() {
        addToImages("creditsBackground", "/images/credits.png");
        addToImages("owenjow", "/images/owenjow.png");
        addToImages("williamjow", "/images/williamjow.png");
    }
    
    /**
     * Initializes sheets for in-game characters and objects.
     * - kirbySS: the sheet for a basic Kirby (although let's be honest, Kirby is never basic)
     * - kirbySS-1: the sheet for left-facing Kirby
     * - storyMenuSS: the "sheet" (as in 2 images) for the story menu
     */
    private static void initializeSheets() {
        addToImages("kirbySS", "/images/kirby_spritesheet.png");
        addToImages("kirbySS-1", "/images/kirby_spritesheet-1.png");
        addToImages("storyMenuSS", "/images/storyMenu.png");
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
        addToImages("demoForeground", "/images/demoForeground.png");
        addToImages("level1Background", "/images/level1Background.png");
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
