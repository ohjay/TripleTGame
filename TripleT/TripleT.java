package TripleT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JApplet;

/**
 * Triple T, aka Kirby: The Tedhaun Treaty.
 * Sprites for the game obtained from The Spriter's Resource (www.spriters-resource.com).
 * It should also be noted that the Kirby franchise is owned by Nintendo and HAL Laboratory,
 * and that this game is of purely recreational value (aka not for profit).
 * @author Owen Jow
 */
public class TripleT extends JApplet {
    private static final String TRIPLE_T_FILEPATH = ".triplet.ser"; // filepath for serialized data
    
    /** 
     * Launches Kirby: The Tedhaun Treaty.
     * Sets up the game based on saved date, and then initializes the GUI 
     * so that the game can be played.
     * @param args command-line args that, for the purposes of this game, need not exist
     */
    public static void main(String[] args) {
        GameState state = loadGameState();
        TripleTWindow.initializeGUI(state);
    }
    
    /**
     * Loads and returns a state object from a serialized file.
     * If no such serialized file exists, returns a fresh new state object.
     */
    private static GameState loadGameState() {
        try {
            File tripleTFile = new File(System.getProperty("user.home"), TRIPLE_T_FILEPATH);
            if (!tripleTFile.exists()) {
                return new GameState();
            } else {
                FileInputStream fileIn = new FileInputStream(tripleTFile);
                ObjectInputStream pInfoIn = new ObjectInputStream(fileIn);
                GameState state = new GameState();
                state.pInfo = (PersistentInfo) pInfoIn.readObject();
                fileIn.close();
                pInfoIn.close();
                
                return state;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        // Something went wrong, so we'll just return a new state object
        return new GameState();
    }
    
    /** 
     * Serializes a state object for use by posterity.
     * Saves the state object in a file specified by the filepath TRIPLE_T_FILEPATH.
     * Like actual Kirby games, the game state should be saved whenever the player
     * completes a level or makes any kind of development in the story or game.
     * @param state the game state to be serialized
     */
    static void savePersistentInfo(PersistentInfo pInfo) {
        try {
            File tripleTFile = new File(System.getProperty("user.home"), TRIPLE_T_FILEPATH);
            if (!tripleTFile.exists()) {
                tripleTFile.createNewFile();
            }
            
            FileOutputStream fileOut = new FileOutputStream(tripleTFile, false);
            ObjectOutputStream pInfoOut = new ObjectOutputStream(fileOut);
            pInfoOut.writeObject(pInfo);
            fileOut.close();
            pInfoOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
