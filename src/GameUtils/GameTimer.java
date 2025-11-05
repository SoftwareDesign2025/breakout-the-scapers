package GameUtils;

import GameElemtents.Paddle;
import GameElemtents.PowerUps;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

// this is for making a global timer methods to be used and called for other game functions
public class GameTimer {
    private static final int CONVERT_TIME_SEC = 1000; // milliseconds in a second
    private final long startTime;
	
	// when program runs make a start time to note when the game has started
	public GameTimer() {
		this.startTime = System.currentTimeMillis();
	}
	

    // Returns true if the given time limit (in seconds) has passed since start of the game
    public boolean timerExpired(int timeLimitSeconds) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        boolean timeSinceStart = elapsedTime >= timeLimitSeconds * CONVERT_TIME_SEC;
        return timeSinceStart;
        
    }
	
	// get the time the game has been running for
    public long getElapsedTimeSeconds() {
        return (System.currentTimeMillis() - startTime) /  CONVERT_TIME_SEC;
    }
    
    public void runForDuration(Runnable action, Runnable onExpire, double durationSeconds) {
        action.run();

        // Schedule the onExpire task (e.g., reset paddle size)
        PauseTransition timer = new PauseTransition(Duration.seconds(durationSeconds));
        timer.setOnFinished(event -> onExpire.run());
        timer.play();
    }
    
    //GameTimer timer = new GameTimer();
//    timer.runForDuration(
//    	    () -> paddle.expand(),       // what to do now
//    	    () -> paddle.resetSize(),    // what to do when time ends
//    	    10                           // duration in seconds
//    	);
    
    
   
	
}
