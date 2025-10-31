package GameUtils;


import java.io.*;
import java.sql.Time;
// this is for making a global timer methods to be used and called for other game functions
public class GameTimer {
    private static final int CONVERT_TIME_SEC = 1000; // milliseconds in a second
    private final long startTime;
	
	// when program runs make a start time to note when the game has started
	public GameTimer() {
		this.startTime = System.currentTimeMillis();
	}
	

    // Returns true if the given time limit (in seconds) has passed since start
    public boolean timerExpired(int timeLimitSeconds) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime >= timeLimitSeconds * CONVERT_TIME_SEC;
    }
	
	
    public double getElapsedTimeSeconds() {
        return (System.currentTimeMillis() - startTime) / (double) CONVERT_TIME_SEC;
    }
	
}
