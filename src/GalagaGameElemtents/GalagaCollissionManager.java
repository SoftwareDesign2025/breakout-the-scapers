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
    // For Galaga balls: applies damage and removes ball immediately on hit
    public static int handleBallBricks(List<GalagaBall> balls, List<EnemyBase> enemies) {

 
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
