package GalagaGameElemtents;

import java.util.Iterator;
import java.util.List;

import GameElemtents.Brick;

public class GalagaCollissionManager {
	
	public static void handleBallPaddle(List<GalagaBall> balls, List<GalagaPaddle> paddles) {
        
    }

    // returns the total points scored
    // For Galaga balls: applies damage and removes ball immediately on hit
    public static int handleBallBricks(List<GalagaBall> balls, List<Brick> bricks) {
        int score = 0;
        Iterator<GalagaBall> ballIterator = balls.iterator();
        while (ballIterator.hasNext()) {
            GalagaBall galagaBall = ballIterator.next();
            for (Brick brick : bricks) {
                // Check collision
                if (brick.getView().getBoundsInParent().intersects(galagaBall.getView().getBoundsInParent())) {
                    // Apply damage from the ball
                    int damage = galagaBall.getProjectileDamage();
                    boolean destroyed = brick.onHit(damage);
                    if (destroyed) {
                        score += brick.getPoints();
                    }
                    // Remove ball immediately from list and scene (ball removes itself)
                    galagaBall.eraseFromScreen();
                    ballIterator.remove();
                    break; // Only one brick hit per ball
                }
            }
        }
        return score;
    }
    
    public static void handleBallBall(List<GalagaBall> balls) {
       
    }
}
