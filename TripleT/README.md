# TripleT
The source code (`.java` files) for TripleTGame.

---

[Animator](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Animator.java)<br>
A class that would have served as a controller for sprite animation... until I ceased development on Triple T. If you wanted to animate Kirby, for example, you would have done it through the methods in this class.

[CongratulatoryPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/CongratulatoryPanel.java)<br>
The panel that is displayed after you win, but before everyone goes home.

[ControllableSprite](https://github.com/ohjay/TripleTGame/blob/master/TripleT/ControllableSprite.java)<br>
A sprite that can be controlled by the player (via the keyboard).

[ControlMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/ControlMenuPanel.java)<br>
The panel for the controls menu, in which the user will be able to customize controls.

[CreditsPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/CreditsPanel.java)<br>
The credits panel.

[CutsceneMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/CutsceneMenuPanel.java)<br>
The menu for selecting between cutscenes to view. There will be three cutscenes total: one will play at the beginning of each of the two worlds, and the last will play at the game's conclusion.

[DemoGhost](https://github.com/ohjay/TripleTGame/blob/master/TripleT/DemoGhost.java)<br>
A ghost for use in the Dodge! pre-game panel demos.

[DemoStarKirby](https://github.com/ohjay/TripleTGame/blob/master/TripleT/DemoStarKirby.java)<br>
A demo Star Kirby to be animated in the Dodge! pre-game screen.

[DodgePanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/DodgePanel.java)<br>
A panel containing all of the topmost logic for Dodge!.

[DodgePausePanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/DodgePausePanel.java)<br>
A pause panel for the Dodge! minigame. 

[DodgePostGPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/DodgePostGPanel.java)<br>
The post-game screen for Dodge!.

[DodgePreGPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/DodgePreGPanel.java)<br>
The panel to display before playing the Dodge! minigame. Includes an objective statement, controls info, and movement demonstrations.

[Door](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Door.java)<br>
A POJO containing door-related information.

[EscapableMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/EscapableMenuPanel.java)<br>
A menu that can be escaped from (i.e. a menu with a "back" option).

[Foreground](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Foreground.java)<br>
The foreground; that is, a collection of all the objects that exist one layer above the background.

[GameOverPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/GameOverPanel.java)<br>
The panel that losers are shown.

[GameState](https://github.com/ohjay/TripleTGame/blob/master/TripleT/GameState.java)<br>
A class containing any data required by the overall game. While theoretically many game states could exist, for every game ("game": imagine a physical handheld game that could be purchased from a store) there will only be a single GameState which will be used by any panels or components that require it.

[Images](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Images.java)<br>
A utility class for storing and manipulating images. Contains all of the game's image data and permits access to any desired image via that image's unique idenitifier.

[KPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/KPanel.java)<br>
A JPanel that contains logic common to all panels with key binding inputs.

[Kirby](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Kirby.java)<br>
This is story-mode Kirby in all his glory. As a controllable sprite, Kirby can be directed with the keyboard and will display movement animations based on his spritesheet.

[Level1](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Level1.java)<br>
The first level of the game.

[Level2](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Level2.java)<br>
The second level of the game (world 1, level 2).

[Level2Kirby](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Level2Kirby.java)<br>
Kirby in his Level 2 form, which is different than most other forms in that he's being pulled into the air and therefore requires less controls + less animations.

[LevelPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/LevelPanel.java)<br>
An abstract blueprint for a level panel, which includes activation and painting methods (in addition to a drawForeground method that should be overridden by subclasses).

[LoadingScreen](https://github.com/ohjay/TripleTGame/blob/master/TripleT/LoadingScreen.java)<br>
A placeholder screen to tide the users over while resources load.

[MainMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/MainMenuPanel.java)<br>
The main menu panel for the game.

[MenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/MenuPanel.java)<br>
An abstract representation of a menu panel. Includes methods for key bindings, along with the ability to switch between options (which will tend to be images).

[MinigameGhost](https://github.com/ohjay/TripleTGame/blob/master/TripleT/MinigameGhost.java)<br>
A Tedhaun, serving as a generic ghost in the Dodge! minigame. Includes positional state values, alongside image, collision detection, and movement behavior.

[MinigameMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/MinigameMenuPanel.java)<br>
A menu that serves as a gateway to different minigames. There are two such minigames; one is a survival-style evasion game (Dodge!) and the other is a "Smash Bros"-inspired knockout game, in which the winner is the last man standing among the player and three CPUs.

[OptionMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/OptionMenuPanel.java)<br>
A menu for toggling and choosing between game options. The player should be able to change Kirby's in-game color and adjust the music volume.

[PersistentInfo](https://github.com/ohjay/TripleTGame/blob/master/TripleT/PersistentInfo.java)<br>
A collection of data that should persist through different play sessions. In other words, it represents the data that will be saved. This will also serve as a container for individual save file data.

[PosterMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/PosterMenuPanel.java)<br>
A "poster" menu panel, named because the user won't be interacting with any of its elements. Instead, the user will read or look at the contents of the panel, and then push ANY key to exit.

[SaveFileInfo](https://github.com/ohjay/TripleTGame/blob/master/TripleT/SaveFileInfo.java)<br>
A class containing the data for an individual game save file. The game should contain three such save files.

[SmashPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/SmashPanel.java)<br>
The panel for the kirbySMASH minigame. That will never be made. :(

[Sprite](https://github.com/ohjay/TripleTGame/blob/master/TripleT/Sprite.java)<br>
An abstract representation of a sprite that will be extended by all sprites. (Google's definition of a sprite: a computer graphic that may be moved on-screen and otherwise manipulated as a single entity.)

[StarKirby](https://github.com/ohjay/TripleTGame/blob/master/TripleT/StarKirby.java)<br>
A Kirby to serve as the controllable model for Dodge!. Visually, StarKirby will be represented by Kirby on a star, surfing over sky or space.

[StoryMenuPanel](https://github.com/ohjay/TripleTGame/blob/master/TripleT/StoryMenuPanel.java)<br>
The menu for the story mode, which includes a choice between each of the three save files. It also allows for the option of deleting save files. Incidentally, within the story menu we will use the inherited "imgIndex" variable to represent the save file that is currently selected.

[TreasureChest](https://github.com/ohjay/TripleTGame/blob/master/TripleT/TreasureChest.java)<br>
A treasure chest, to be used as an objective in the Dodge! minigame.

[TripleT](https://github.com/ohjay/TripleTGame/blob/master/TripleT/TripleT.java)<br>
[The main class for] Triple T, aka Kirby: The Tedhaun Treaty.

[TripleTWindow](https://github.com/ohjay/TripleTGame/blob/master/TripleT/TripleTWindow.java)<br>
A representation of the on-screen window for the game.
