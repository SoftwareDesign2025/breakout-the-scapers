package GameUtils;

import java.util.Iterator;
import java.util.List;

import GameElemtents.Ball;
import GameElemtents.Brick;
import GameElemtents.Paddle;

//keeps the physics and scoring logic separate from the controller
public class CollisionManager { 
	
	//checks if the ball hits the paddle, and makes it bounce upward proportionally on how much
	// to the right is with respect to the paddle Warning, there is a lot of math on this
	public static void handleBallPaddle(List<Ball> balls, List<Paddle> paddles) {
        for (Ball ball : balls) {
            for (Paddle paddle : paddles) {
                paddle.collideWithBall(ball);
            }
        }
    }

	//checks collisions between the ball and all bricks
    //removes bricks when destroyed, and adds points
    public static void handleBallBricks(List<Ball> balls, List<Brick> bricks, BreakoutController controller) {
        Iterator<Brick> it = bricks.iterator(); //safe way to remove while iterating
        
        while (it.hasNext()) {
            for (Ball ball : balls) {
                Brick brick = it.next();
                if (brick.collideWithBall(ball)) {
                    controller.addScore(brick.getPoints());
                }
                if (brick.deadBrick()) {
                    it.remove();
                }
            }
            
        }
    }
    
    public static void handleBallBall(List<Ball> balls) {
       for (int i = 0; i < balls.size(); i++) {
           Ball ballA = balls.get(i);
           // for ball A collide with every other ball B
           for (int j = i + 1; j < balls.size(); j++) {
               Ball ballB = balls.get(j);
               ballA.collideWithBall(ballB);
           }
       }
    }

}
