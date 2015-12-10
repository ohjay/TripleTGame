package TripleT;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * The panel that is displayed after you win, but before everyone goes home.
 * @author Owen Jow
 */
public class CongratulatoryPanel extends PosterMenuPanel implements ActionListener {
    private Timer timer;
    private int counter;
    private static final int NOINPUT_TIME = 150;
    
    /**
     * Constructor. Should be obvious.
     */
    public CongratulatoryPanel() {
        backgroundImg = Images.get("congratulations");
    }
    
    /**
     * We won't want to activate key input until the user's had a chance to look at the message.
     * Otherwise, it's easy to skip the "congratulations" screen by accident.
     */
    @Override
    public void activate() {
        timer = new Timer(15, this);
        timer.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        counter++;
        if (counter >= NOINPUT_TIME) {
            timer.stop();
            super.activate();
        }
    }
}
