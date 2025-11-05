package GalagaGameElemtents;

import java.util.Iterator;
import java.util.List;

import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;
import GameUtils.CollisionManager;

public class GalagaCollissionManager extends CollisionManager {
	
	public static void handleBallPaddle(List<GalagaBall> balls, List<GalagaPaddle> paddles) {
        
    }

    // returns the total points scored
    public static int handleBallBricks(List<Ball> balls, List<Brick> bricks) {
        Iterator<Brick> it = bricks.iterator(); //safe way to remove while iterating
        int score = 0;
        while (it.hasNext()) {
            for (Ball ball : balls) {
            }
        }
        return score;
    }
    
    public static void handleBallBall(List<GalagaBall> balls) {
       
    }
}
